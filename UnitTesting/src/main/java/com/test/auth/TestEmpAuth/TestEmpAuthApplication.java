package com.test.auth.TestEmpAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TestEmpAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestEmpAuthApplication.class, args);
	}

}
