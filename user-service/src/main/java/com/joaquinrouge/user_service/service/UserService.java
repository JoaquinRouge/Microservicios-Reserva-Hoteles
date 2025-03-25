package com.joaquinrouge.user_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquinrouge.user_service.client.ICartClient;
import com.joaquinrouge.user_service.dto.AddToCartDto;
import com.joaquinrouge.user_service.dto.CartDto;
import com.joaquinrouge.user_service.dto.ProductDto;
import com.joaquinrouge.user_service.model.User;
import com.joaquinrouge.user_service.repository.IUserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private ICartClient cartClient;
	
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUser(Long id) {
		return userRepo.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("User with id " + id + " not found"));
	}

	@Override
	public User createUser(User user) {
		
		if(userRepo.existsByName(user.getName())) {
			throw new IllegalArgumentException("User already exists");
		}
		
		User createUser = userRepo.save(user);
		
		cartClient.createCart(new CartDto(createUser.getId()));
		
		return createUser;
	}

	@Override
	public void deleteUser(Long id) {

		if(!userRepo.existsById(id)) {
			throw new IllegalArgumentException("User not found");
		}
		
		userRepo.deleteById(id);
		
	}

	@Override
	public User updateUser(User user) {
		
		User userFromDb = this.getUser(user.getId());
		
		userFromDb.setName(user.getName());
		
		return userRepo.save(userFromDb);
	}

	@Override
	public AddToCartDto addToCart(AddToCartDto dto) {
		return cartClient.addToCart(dto);
	}

}
