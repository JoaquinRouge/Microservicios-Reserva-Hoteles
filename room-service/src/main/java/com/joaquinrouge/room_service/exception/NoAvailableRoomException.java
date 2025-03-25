package com.joaquinrouge.room_service.exception;

public class NoAvailableRoomException extends RuntimeException{
    public NoAvailableRoomException(String message) {
        super(message);
    }
}
