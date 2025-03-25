package com.joaquinrouge.user_service.dto;

import java.util.List;

public class AddToCartDto {
	private Long userId;
	private List<Long> productsId;
	
	public AddToCartDto() {
		
	}

	public AddToCartDto(Long userId, List<Long> productsId) {
		super();
		this.userId = userId;
		this.productsId = productsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getProductsId() {
		return productsId;
	}

	public void setProductsId(List<Long> productsId) {
		this.productsId = productsId;
	}
	
	
	
}
