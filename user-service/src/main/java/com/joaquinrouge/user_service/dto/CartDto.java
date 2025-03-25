package com.joaquinrouge.user_service.dto;

public class CartDto {
	private Long userId;
	
	public CartDto() {
		
	}

	public CartDto(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
