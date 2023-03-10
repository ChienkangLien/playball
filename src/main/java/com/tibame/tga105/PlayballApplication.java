package com.tibame.tga105;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
//@ComponentScan(basePackages = {"com.tibame.tga105"})
public class PlayballApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayballApplication.class, args);
	}

}
