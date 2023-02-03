package com.wedotech.pawsitivelywell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class PawsitivelywellApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawsitivelywellApplication.class, args);
	}

}
