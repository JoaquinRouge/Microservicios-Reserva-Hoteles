package com.joaquinrouge.user_service.service;

import java.util.List;

import com.joaquinrouge.user_service.model.User;

public interface IUserService {
	List<User> getAllUsers();
	User getUser(Long id);
	User createUser(User user);
	void deleteUser(Long id);
	User updateUser(User user);
}
