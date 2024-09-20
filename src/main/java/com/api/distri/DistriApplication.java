package com.api.distri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
public class DistriApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistriApplication.class, args);
	}

}
