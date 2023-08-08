package com.modak.test.service;

import com.modak.test.configuration.RateLimitConfig;
import com.modak.test.exception.RateLimitExceededException;
import com.modak.test.model.RateLimitRule;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimitService {
    private Map<String, Map<String, List<Instant>>> requestHistory = new HashMap<>();
    private RateLimitConfig rateLimitConfig;

    public RateLimitService(RateLimitConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    public boolean allowRequest(String recipient, String notificationType) throws RateLimitExceededException {
        RateLimitRule rule = rateLimitConfig.getRule(notificationType);
        if (rule == null) {
            return true;
        }

        Instant now = Instant.now();

        Map<String, List<Instant>> recipientHistory = requestHistory.get(recipient);
        if (recipientHistory == null) {
            recipientHistory = new HashMap<>();
            requestHistory.put(recipient, recipientHistory);
        }

        List<Instant> typeHistory = recipientHistory.get(notificationType);
        if (typeHistory == null) {
            typeHistory = new ArrayList<>();
            recipientHistory.put(notificationType, typeHistory);
        }

        typeHistory.removeIf(instant -> instant.isBefore(now.minus(rule.getInterval())));
        if (typeHistory.size() >= rule.getMaxRequests()) {
            throw new RateLimitExceededException("Rate limit exceeded for type: " + notificationType);
        }

        typeHistory.add(now);
        return true;
    }
}
