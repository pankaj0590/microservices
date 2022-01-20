package com.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.api.users.entity.UserEntity;
import com.photoapp.api.users.inter.service.call.AlbumServiceFeign;
import com.photoapp.api.users.repository.UsersRepository;
import com.photoapp.api.users.shared.UserDto;
import com.photoapp.api.users.ui.model.AlbumResponseModel;

@Service
public class UsersServiceImpl implements UsersService {

	  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UsersRepository repository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	AlbumServiceFeign albumServiceFeign;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		ModelMapper mapper =new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity= mapper.map(userDto, UserEntity.class);
		
		userEntity=repository.save(userEntity);
		UserDto retrunValue= mapper.map(userEntity, UserDto.class);
		return retrunValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity entity= repository.findByEmail(username);
		if(entity==null) throw new UsernameNotFoundException(username);
		return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity entity= repository.findByEmail(email);
		if(entity==null) throw new UsernameNotFoundException(email);
		return new ModelMapper().map(entity, UserDto.class);
	}

	@Override
	public UserDto getUserById(int id) {
		Optional<UserEntity> userEntity =repository.findById((long) id);
		if(!userEntity.isPresent()) throw new UsernameNotFoundException("User not found");
		UserDto dto  = new ModelMapper().map(userEntity.get(), UserDto.class);
		logger.info("Before calling Albums Microservice: ");
		List<AlbumResponseModel> albumsList =albumServiceFeign.getAlbumsById(id);
		logger.info("After calling Albums Microservice: ");
		dto.setAlbums(albumsList);
		return dto;
	}
	
	

}
