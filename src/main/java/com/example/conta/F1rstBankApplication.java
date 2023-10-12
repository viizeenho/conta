package com.example.conta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class F1rstBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1rstBankApplication.class, args);
	}

}
