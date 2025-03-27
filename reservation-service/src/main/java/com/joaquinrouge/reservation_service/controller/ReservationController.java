package com.joaquinrouge.reservation_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.reservation_service.model.Reservation;
import com.joaquinrouge.reservation_service.service.IReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
	@Autowired
	private IReservationService reservationService;
	
	@GetMapping()
	public ResponseEntity<Object> getAllReservations() {
		return ResponseEntity.status(HttpStatus.OK).body(reservationService.getAllReservations());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Object> getReservation(@PathVariable("id") Long id){
		try {
			Reservation reservation = reservationService.getReservation(id);
			return ResponseEntity.status(HttpStatus.OK).body(reservation);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/user/id/{userId}")
	public ResponseEntity<Object> getReservationByUserId(@PathVariable("userId") Long userId){
		try {
			Reservation reservation = reservationService.getReservationByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).body(reservation);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/room/id/{roomId}")
	public ResponseEntity<Object> getReservationByRoomId(@PathVariable("roomId") Long roomId){
		try {
			Reservation reservation = reservationService.getReservationByRoomId(roomId);
			return ResponseEntity.status(HttpStatus.OK).body(reservation);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation){
		try {
			Reservation createReservation = reservationService.createReservation(reservation);
			return ResponseEntity.status(HttpStatus.CREATED).body(createReservation);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{deleteId}")
	public ResponseEntity<Object> deleteObject(@PathVariable("deleteId") Long deleteId){
		try {
			reservationService.deleteReservation(deleteId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> deleteReservation(@RequestBody Reservation reservation){
		try {
			Reservation updateReservation = reservationService.updateReservation(reservation);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateReservation);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
