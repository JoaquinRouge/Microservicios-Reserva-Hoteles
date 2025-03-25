package com.joaquinrouge.room_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaquinrouge.room_service.model.Room;

public interface IRoomRepository extends JpaRepository<Room, Long>{
	List<Room> findByAvailableTrue();
	List<Room> findByCapacity(int capacity);
}
