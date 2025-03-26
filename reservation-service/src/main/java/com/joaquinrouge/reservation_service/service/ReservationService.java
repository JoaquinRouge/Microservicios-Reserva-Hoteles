package com.joaquinrouge.reservation_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquinrouge.reservation_service.client.IRoomClient;
import com.joaquinrouge.reservation_service.client.IUserClient;
import com.joaquinrouge.reservation_service.dto.RoomDto;
import com.joaquinrouge.reservation_service.model.Reservation;
import com.joaquinrouge.reservation_service.model.repository.IReservationRepository;

import feign.FeignException;

@Service
public class ReservationService implements IReservationService{

	@Autowired
	private IReservationRepository reservationRepo;
	
	@Autowired
	private IRoomClient roomClient;
	
	@Autowired
	private IUserClient userClient;
	
	@Override
	public List<Reservation> getAllReservations() {
		return reservationRepo.findAll();
	}

	@Override
	public Reservation getReservation(Long id) {
		return reservationRepo.findById(id)
				.orElseThrow(()-> new IllegalArgumentException(
						"Reservation with id " + id + " not found"));
	}

	@Override
	public Reservation getReservationByUserId(Long id) {
		return reservationRepo.findByUserId(id)
				.orElseThrow(()-> new IllegalArgumentException(
						"Reservation for user with id " + id + " not found"));
	}

	@Override
	public Reservation getReservationByRoomId(Long id) {
		return reservationRepo.findByRoomId(id)
				.orElseThrow(()-> new IllegalArgumentException(
						"Reservation for room with id " + id + " not found"));
	}

	@Override
	public Reservation createReservation(Reservation reservation) {
		
		//CALL TO ROOM MICROSERVICE TO CHECK ROOM EXISTANCE
		
		try {
			
			roomClient.getRoom(reservation.getRoomId());	
			
		}catch (FeignException.NotFound e) {
			
			throw new IllegalArgumentException(
					"Room with id " + reservation.getRoomId() + " not found.");
	        
	    }catch (FeignException e) {
	    	
	    	throw new RuntimeException(
	    			"Error while communicating with Room service: " + e.getMessage());
	        
	    }
		
		//CALL TO USER MICROSERVICE TO CHECK ROOM EXISTANCE
		
		try {
			
			userClient.getUser(reservation.getUserId());
			
		}catch (FeignException.NotFound e) {
			
			throw new IllegalArgumentException(
					"User with id " + reservation.getUserId() + " not found.");
	        
	    }catch (FeignException e) {
	    	
	    	throw new RuntimeException(
	    			"Error while communicating with User service: " + e.getMessage());
	        
	    }
		
		if(reservationRepo.existsByRoomId(reservation.getRoomId())) {
			throw new IllegalArgumentException(
					"The Room with id" + reservation.getRoomId() + " is unavailable");
		}
		
		roomClient.markRoomAsUnavailable(reservation.getRoomId());
		return reservationRepo.save(reservation);
	}

	@Override
	public void deleteReservation(Long id) {
		if(!reservationRepo.existsById(id)) {
			throw new IllegalArgumentException("Reservation with id " + id + " not found");
		}
		
		reservationRepo.deleteById(id);
		
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		
		Reservation reservationFromDb = this.getReservation(reservation.getId());
		
		if(reservationRepo.existsByRoomId(reservation.getRoomId())) {
			throw new IllegalArgumentException(
					"The Room with id" + reservation.getRoomId() + " is unavailable");
		}
		
		reservationFromDb.setRoomId(reservation.getRoomId());
		reservationFromDb.setUserId(reservation.getUserId());
		reservationFromDb.setStatus(reservation.getStatus());
		
		return reservationRepo.save(reservationFromDb);
	}

}
