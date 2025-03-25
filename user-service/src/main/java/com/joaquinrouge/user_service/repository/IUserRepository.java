package com.joaquinrouge.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.user_service.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	boolean existsByName(String name);
}
