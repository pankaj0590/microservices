package com.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	 private Environment environment;
	 
	 Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment=environment;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authorrozationHeader =request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorrozationHeader == null || !authorrozationHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		 UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		 
	     SecurityContextHolder.getContext().setAuthentication(authentication);
	     chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String authorrozationHeader =request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorrozationHeader == null) {
			return null;
		}
		String token = authorrozationHeader.replace("Bearer","");
		
		logger.info("Authorization header flow from API Getway to downstream microservoce : "+ token );
		
		String id =Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
		
		logger.info("Extracted id from jwt token : "+ id );

		if(id == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(id, null, new ArrayList<>());
	}
	
	

}
