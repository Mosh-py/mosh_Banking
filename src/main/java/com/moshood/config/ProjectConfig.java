package com.moshood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {"com.moshood.observer"})
public class ProjectConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
