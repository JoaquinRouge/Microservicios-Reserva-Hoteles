package com.joaquinrouge.room_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.joaquinrouge.room_service.exception.NoAvailableRoomException;
import com.joaquinrouge.room_service.model.Room;
import com.joaquinrouge.room_service.repository.IRoomRepository;

public class RoomService implements IRoomService {

	@Autowired
	private IRoomRepository roomRepo;
	
	@Override
	public List<Room> getAllRooms() {
		return roomRepo.findAll();
	}

	@Override
	public List<Room> getAvailableRooms() {
		
		List<Room> list = roomRepo.findByAvailableTrue();
		
		if(list.isEmpty()) {
			throw new NoAvailableRoomException("There are no available rooms");
		}
		
		return list;
	}

	@Override
	public List<Room> getRoomsByCapacity(int capacity) {
		
		List<Room> list = roomRepo.findByCapacity(capacity);
		
		if(list.isEmpty()) {
			throw new IllegalArgumentException("There are no room with capacity: " + capacity);
		}
		
		return list;
		
	}

	@Override
	public Room getRoom(Long id) {
		return roomRepo.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("No room with id " + id));
	}

	@Override
	public Room createRoom(Room room) {
		return roomRepo.save(room);
	}

	@Override
	public void deleteRoom(Long id) {
		if(!roomRepo.existsById(id)) {
			throw new IllegalArgumentException("No room with id " + id);
		}
		
		roomRepo.deleteById(id);
		
	}

	@Override
	public Room updateRoom(Room room) {
		
		Room roomFromDb = this.getRoom(room.getId());
		
		roomFromDb.setAvailable(room.isAvailable());
		roomFromDb.setCapacity(room.getCapacity());
		roomFromDb.setType(room.getType());
		
		return roomRepo.save(roomFromDb);
	}

}
