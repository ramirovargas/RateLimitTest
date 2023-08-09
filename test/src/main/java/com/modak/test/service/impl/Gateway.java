package com.modak.test.service.impl;

public class Gateway {
    public void send(String userId, String message) {
        System.out.println("sending message to user " + userId + " message: "+ message);
    }
}
