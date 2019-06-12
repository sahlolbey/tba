package com.tba.tbaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@SpringBootApplication
public class TbaWebApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication();
		//springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
		springApplication.run(TbaWebApplication.class, args);


	}


}
