package com.tp.udde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class UddeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UddeApplication.class, args);
	}

}
