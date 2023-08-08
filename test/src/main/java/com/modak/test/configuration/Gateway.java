package com.modak.test.configuration;

public class Gateway {
    public void send(String userId, String message) {
        System.out.println("sending message to user " + userId + " message: "+ message);
    }
}
