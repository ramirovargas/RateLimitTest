package com.modak.test.service.impl;

import com.modak.test.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    private Gateway gateway;

    public NotificationServiceImpl(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void send(String type, String userId, String message) {
        gateway.send(userId, message);
    }
}
