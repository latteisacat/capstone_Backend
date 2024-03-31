package com.example.capstone_backend;

import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CapstoneBackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CapstoneBackendApplication.class, args);
	}

}
