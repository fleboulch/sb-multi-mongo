package com.example.demo;

import com.example.demo.containers.Containers;
import org.springframework.boot.SpringApplication;

public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoApplication::main)
				.with(Containers.class)
				.run(args);
	}

}
