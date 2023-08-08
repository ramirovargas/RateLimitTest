package com.modak.test.service;

import com.modak.test.configuration.Gateway;

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
