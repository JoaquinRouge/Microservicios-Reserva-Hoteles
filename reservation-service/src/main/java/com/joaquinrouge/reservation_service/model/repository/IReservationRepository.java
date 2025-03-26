package com.joaquinrouge.reservation_service.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.reservation_service.model.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long>{
	Optional<Reservation> findByUserId(Long id);
	Optional<Reservation> findByRoomId(Long id);
	boolean existsByRoomId(Long id);
}
