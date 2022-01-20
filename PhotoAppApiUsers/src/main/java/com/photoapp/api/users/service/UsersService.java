package com.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photoapp.api.users.shared.UserDto;


public interface UsersService extends UserDetailsService {
	UserDto createUser(UserDto userDto);
	UserDto getUserDetailsByEmail(String email);
	UserDto getUserById(int id);
}
