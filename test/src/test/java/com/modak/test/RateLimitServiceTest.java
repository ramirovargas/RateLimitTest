package com.modak.test;

import com.modak.test.configuration.RateLimitConfig;
import com.modak.test.exception.RateLimitExceededException;
import com.modak.test.service.NotificationService;
import com.modak.test.service.impl.RateLimitService;
import com.modak.test.service.impl.RateLimitingNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class RateLimitServiceTest {
        private RateLimitConfig rateLimitConfig;
        private RateLimitService rateLimitService;
        private NotificationService NotificationService;
        private NotificationService rateLimitedService;

        @BeforeEach
        public void setUp() {
                rateLimitConfig = new RateLimitConfig();
                rateLimitConfig.addRule("Status", 2, Duration.ofMinutes(1));
                rateLimitConfig.addRule("News", 1, Duration.ofDays(1));
                rateLimitConfig.addRule("Marketing", 3, Duration.ofHours(1));

                rateLimitService = new RateLimitService(rateLimitConfig);
                NotificationService = mock(NotificationService.class);
                rateLimitedService = new RateLimitingNotificationService(NotificationService, rateLimitService);
        }

        @Test
        public void testStatusRateLimit() throws InterruptedException {
                String type = "Status";
                String userId = "user";
                String message = "update";

                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
                assertThrows(RateLimitExceededException.class, () -> rateLimitedService.send(type, userId, message));
                Thread.sleep(60000);
                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
        }

        @Test
        public void testNewsRateLimit() {
                String type = "News";
                String userId = "user";
                String message = "news update";

                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
                assertThrows(RateLimitExceededException.class, () -> rateLimitedService.send(type, userId, message));
        }

        @Test
        public void testMarketingRateLimit() {
                String type = "Marketing";
                String userId = "user";
                String message = "marketing update";

                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
                assertDoesNotThrow(() -> rateLimitedService.send(type, userId, message));
                assertThrows(RateLimitExceededException.class, () -> rateLimitedService.send(type, userId, message));
        }

}
