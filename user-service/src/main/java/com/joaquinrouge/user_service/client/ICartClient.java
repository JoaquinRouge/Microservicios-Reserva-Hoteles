package com.joaquinrouge.user_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.joaquinrouge.user_service.dto.AddToCartDto;
import com.joaquinrouge.user_service.dto.CartDto;

@FeignClient(name = "CART-SERVICE")
public interface ICartClient {
	@PostMapping("/cart/create")
	CartDto createCart(@RequestBody CartDto cart);
	
	@PostMapping("/cart/add")
	AddToCartDto addToCart(@RequestBody AddToCartDto dto);
}
