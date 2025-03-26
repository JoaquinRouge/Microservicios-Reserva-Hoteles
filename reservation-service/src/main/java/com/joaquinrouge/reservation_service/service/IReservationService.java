package com.joaquinrouge.reservation_service.service;

import java.util.List;

import com.joaquinrouge.reservation_service.model.Reservation;

public interface IReservationService {
	
	List<Reservation> getAllReservations();
	Reservation getReservation(Long id);
	Reservation getReservationByUserId(Long id);
	Reservation getReservationByRoomId(Long id);
	Reservation createReservation(Reservation reservation);
	void deleteReservation(Long id);
	Reservation updateReservation(Reservation reservation);
}
