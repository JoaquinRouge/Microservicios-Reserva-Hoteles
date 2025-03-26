package com.joaquinrouge.reservation_service.dto;

public class RoomDto {
	
	private Long id;
	private String type;
	private int capacity;
	private boolean available;
	
	public RoomDto() {
		
	}

	public RoomDto(Long id, String type, int capacity, boolean available) {
		super();
		this.id = id;
		this.type = type;
		this.capacity = capacity;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	
}
