package com.modak.test;

import com.modak.test.service.impl.Gateway;
import com.modak.test.configuration.RateLimitConfig;
import com.modak.test.service.NotificationService;
import com.modak.test.service.impl.NotificationServiceImpl;
import com.modak.test.service.impl.RateLimitService;
import com.modak.test.service.impl.RateLimitingNotificationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
		RateLimitConfig rateLimitConfig = new RateLimitConfig();
		rateLimitConfig.addRule("News", 1, Duration.ofDays(1));
		rateLimitConfig.addRule("Status", 2, Duration.ofMinutes(1));
		rateLimitConfig.addRule("Marketing", 3, Duration.ofHours(1));

		RateLimitService rateLimitService = new RateLimitService(rateLimitConfig);
		Gateway gateway = new Gateway();

		NotificationService baseService = new NotificationServiceImpl(gateway);
		NotificationService rateLimitedService = new RateLimitingNotificationService(baseService, rateLimitService);

		rateLimitedService.send("Marketing", "user", "update 1");
		rateLimitedService.send("Marketing", "user", "update 2");
		rateLimitedService.send("Marketing", "user", "update 3");
	}

}
