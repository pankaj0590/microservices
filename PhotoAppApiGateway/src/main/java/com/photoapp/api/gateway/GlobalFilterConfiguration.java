package com.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {

	
	final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);
	
	@Bean
	public GlobalFilter globalPrePostFilter() {
		return (exchange,chain) ->{
			logger.info("Global  Pre-Filter is executed ....");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				logger.info("Global  Post-Filter is executed ....");
			}));
		};
	}
}
