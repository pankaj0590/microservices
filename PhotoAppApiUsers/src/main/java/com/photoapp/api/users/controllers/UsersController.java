package com.photoapp.api.users.controllers;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.api.users.entity.UserEntity;
import com.photoapp.api.users.service.UsersService;
import com.photoapp.api.users.shared.UserDto;
import com.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.photoapp.api.users.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UsersService usersService;

	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto dto = mapper.map(userDetails, UserDto.class);
		UserDto returnValue = usersService.createUser(dto);
		CreateUserResponseModel response = mapper.map(returnValue, CreateUserResponseModel.class);
		return new ResponseEntity<CreateUserResponseModel>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable int id){
		UserDto dto = usersService.getUserById(id);
		UserResponseModel retrunValue =new ModelMapper().map(dto, UserResponseModel.class);
		return new ResponseEntity<UserResponseModel>(retrunValue, HttpStatus.OK);
		
	}

}
