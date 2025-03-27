package com.joaquinrouge.room_service.controller;

import java.util.List;

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

import com.joaquinrouge.room_service.exception.NoAvailableRoomException;
import com.joaquinrouge.room_service.model.Room;
import com.joaquinrouge.room_service.service.IRoomService;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private IRoomService roomService;
	
	@GetMapping()
	public ResponseEntity<Object> getAllRooms(){
		return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms());
	}
	
	@GetMapping("/available")
	public ResponseEntity<Object> getAvailableRooms(){
		try { 
			List<Room> roomsAvailable = roomService.getAvailableRooms();
			return ResponseEntity.status(HttpStatus.OK).body(roomsAvailable);
		}catch(NoAvailableRoomException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/capacity/{capacity}")
	public ResponseEntity<Object> getRoomsByCapacity(@PathVariable("capacity") int capacity){
		try {
			List<Room> roomsByCapacity = roomService.getRoomsByCapacity(capacity);
			return ResponseEntity.status(HttpStatus.OK).body(roomsByCapacity);
		}catch(NoAvailableRoomException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/id/{findId}")
	public ResponseEntity<Object> getRoom(@PathVariable("findId") Long findId){
		try {
			Room room = roomService.getRoom(findId);
			return ResponseEntity.status(HttpStatus.OK).body(room);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createRoom(@RequestBody Room room){
		try {
			Room createRoom = roomService.createRoom(room);
			return ResponseEntity.status(HttpStatus.CREATED).body(createRoom);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/update/available/{markId}/{bool}")
	public ResponseEntity<Object> changeRoomAvailiability(@PathVariable("markId") Long markId,
			@PathVariable("bool") boolean bool){
		roomService.changeRoomAvailability(markId,bool);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/delete/{deleteId}")
	public ResponseEntity<Object> deleteRoom(@PathVariable("deleteId") Long deleteId){
		try {
			roomService.deleteRoom(deleteId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateRoom(@RequestBody Room room){
		try {
			Room updateRoom = roomService.updateRoom(room);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateRoom);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
