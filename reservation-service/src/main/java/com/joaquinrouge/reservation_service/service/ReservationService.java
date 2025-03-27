package com.joaquinrouge.reservation_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquinrouge.reservation_service.client.IRoomClient;
import com.joaquinrouge.reservation_service.client.IUserClient;
import com.joaquinrouge.reservation_service.dto.RoomDto;
import com.joaquinrouge.reservation_service.model.Reservation;
import com.joaquinrouge.reservation_service.model.ReservationStatus;
import com.joaquinrouge.reservation_service.model.repository.IReservationRepository;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

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
	@CircuitBreaker(name="ROOM-SERVICE",fallbackMethod="createReservationFallBack")
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
		
		roomClient.changeRoomAvailability(reservation.getRoomId(),false);
		return reservationRepo.save(reservation);
	}

	public Reservation createReservationFallBack(Throwable t) {
		
		System.out.println(
				"Fallback method createReservation executed. Error: " + t.getMessage());
		
		return new Reservation(-1L,-1L,-1L,ReservationStatus.CANCELLED);
	}
	
	@Override
	@CircuitBreaker(name="ROOM-SERVICE", fallbackMethod="deleteReservationFallBack")
	public void deleteReservation(Long id) {
		if(!reservationRepo.existsById(id)) {
			throw new IllegalArgumentException("Reservation with id " + id + " not found");
		}
		
		Reservation reservation = this.getReservation(id);
		
		reservationRepo.deleteById(id);
		
		roomClient.changeRoomAvailability(reservation.getRoomId(), true);
		
	}

	public void deleteReservationFallBack(Long id,Throwable t) {
		System.out.println(
				"Fallback method deleteReservation for id " + id +
				"executed. Error: " + t.getMessage());
		
	    throw new RuntimeException("Unable to delete reservation with id " + id + 
                " due to a problem in ROOM-SERVICE.");
	}
	
	@Override
	public Reservation updateReservation(Reservation reservation) {
		
		Reservation reservationFromDb = this.getReservation(reservation.getId());
		
		if(reservationRepo.existsByRoomId(reservation.getRoomId())) {
			throw new IllegalArgumentException(
					"The Room with id " + reservation.getRoomId() + " is unavailable");
		}
		
		reservationFromDb.setRoomId(reservation.getRoomId());
		reservationFromDb.setUserId(reservation.getUserId());
		reservationFromDb.setStatus(reservation.getStatus());
		
		return reservationRepo.save(reservationFromDb);
	}

}
