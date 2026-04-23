package com.ngambe.sass_stock.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.repositories.UserRepository;
import com.ngambe.sass_stock.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return this.repository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("no user was found with "+username));
	}

}
