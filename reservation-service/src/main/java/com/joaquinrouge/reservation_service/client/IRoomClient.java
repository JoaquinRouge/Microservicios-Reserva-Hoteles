package com.joaquinrouge.reservation_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.joaquinrouge.reservation_service.dto.RoomDto;

@FeignClient(name="ROOM-SERVICE")
public interface IRoomClient {
	
	@GetMapping("/room/id/{findId}")
	RoomDto getRoom(@PathVariable("findId") Long id);
	
	@PostMapping("/room/update/available/{markId}")
	void markRoomAsUnavailable(@PathVariable("markId") Long markId);
	
}
