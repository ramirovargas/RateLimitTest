package com.modak.test.configuration;

import com.modak.test.model.RateLimitRule;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RateLimitConfig {
    private Map<String, RateLimitRule> rules = new HashMap<>();

    public void addRule(String notificationType, int maxRequests, Duration interval) {
        rules.put(notificationType, new RateLimitRule(maxRequests, interval));
    }

    public RateLimitRule getRule(String notificationType) {
        return rules.get(notificationType);
    }

}
