package com.joaquinrouge.reservation_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joaquinrouge.reservation_service.dto.UserDto;

@FeignClient(name = "USER-SERVICE")
public interface IUserClient {
	
	@GetMapping("/user/id/{id}")
	UserDto getUser(@PathVariable("id") Long id);
	
}
