package com.treemaswebapi.treemaswebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan(basePackages = "com.treemaswebapi.treemaswebapi.entity")
public class TreemasWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreemasWebApiApplication.class, args);
	}

}
