package com.photoapp.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	
	@Autowired
	private Environment environment;
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}
	
	public static class Config{
		//Put Configuration properites here
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange,chain) ->{
			ServerHttpRequest httpRequest =exchange.getRequest();
			if(!httpRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onErrors(exchange,"No authorization header", HttpStatus.UNAUTHORIZED);
			}
			String authorizationHeader= httpRequest.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt=authorizationHeader.replace("Bearer", "");
			if(!isJwtValid(jwt)) {
				return onErrors(exchange,"Jwt token is not valid", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onErrors(ServerWebExchange exchange, String string, HttpStatus unauthorized) {
	     ServerHttpResponse response=exchange.getResponse();
	     response.setStatusCode(unauthorized);
		return response.setComplete();
	}
	private boolean isJwtValid(String jwtToken) {
		boolean returnValue=true;
		String subject=null;
		try {
		subject=Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
		.parseClaimsJws(jwtToken)
		.getBody()
		.getSubject();
		}catch (Exception e) {
			returnValue=false;
		}
		if(subject == null || subject.isEmpty()) {
			returnValue=false;
		}
		return returnValue;
	}

}
