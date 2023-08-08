package com.modak.test.model;

import java.time.Duration;

public class RateLimitRule {
    private int maxRequests;
    private Duration interval;

    public RateLimitRule(int maxRequests, Duration interval) {
        this.maxRequests = maxRequests;
        this.interval = interval;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public Duration getInterval() {
        return interval;
    }
}
