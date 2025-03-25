package com.joaquinrouge.room_service.service;

import java.util.List;

import com.joaquinrouge.room_service.model.Room;

public interface IRoomService {
	List<Room> getAllRooms();
	List<Room> getAvailableRooms();
	List<Room> getRoomsByCapacity(int capacity);
	Room getRoom(Long id);
	Room createRoom(Room room);
	void deleteRoom(Long id);
	Room updateRoom(Room room);
}
