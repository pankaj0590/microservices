package com.photoapp.api.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {
	
	@Autowired
	private Environment env;
	@GetMapping("/status/check")
	public String getStatus() {
		return "Users Service is working.." + "token" + env.getProperty("token.expiration_time");
	}

}
