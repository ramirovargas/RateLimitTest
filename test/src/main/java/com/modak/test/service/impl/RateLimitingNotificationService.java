package com.modak.test.service.impl;

import com.modak.test.exception.RateLimitExceededException;
import com.modak.test.service.NotificationService;

public class RateLimitingNotificationService implements NotificationService {
    private NotificationService wrappedService;
    private RateLimitService rateLimitService;

    public RateLimitingNotificationService(NotificationService wrappedService, RateLimitService rateLimitService) {
        this.wrappedService = wrappedService;
        this.rateLimitService = rateLimitService;
    }

    @Override
    public void send(String type, String userId, String message) {
        if (rateLimitService.allowRequest(userId, type)) {
            wrappedService.send(type, userId, message);
        } else {
            throw new RateLimitExceededException("Rate limit exceeded for type: " + type);
        }
    }
}
