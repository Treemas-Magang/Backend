package com.treemaswebapi.treemaswebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.treemaswebapi.treemaswebapi.entity.UserEntity")
public class TreemasWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreemasWebApiApplication.class, args);
	}

}
