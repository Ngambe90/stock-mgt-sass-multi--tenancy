package com.ngambe.sass_stock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngambe.sass_stock.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByUsername(String username);

}
