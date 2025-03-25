package com.joaquinrouge.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joaquinrouge.user_service.dto.ProductDto;

@FeignClient(name="product-service")
public interface IProductClient {	
	@GetMapping("/product/id/{id}")
	ProductDto getProduct(@PathVariable("id") Long id);
}
