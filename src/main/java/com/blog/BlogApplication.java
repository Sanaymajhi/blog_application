package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // this acts as configuration class
public class
BlogApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean //bean annotation add on method when  external liberty class add.and helps to create object
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}

	//springBootApplication config the external class to springIOC.then springIoc has information that class and create bean for this class
}
