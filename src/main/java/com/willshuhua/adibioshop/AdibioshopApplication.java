package com.willshuhua.adibioshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.willshuhua.adibioshop"})
@MapperScan(basePackages = {"com.willshuhua.adibioshop.dao"})
@EnableTransactionManagement
public class AdibioshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdibioshopApplication.class, args);
	}
}
