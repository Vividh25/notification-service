package com.vividh.notificationservice.channel;

import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel {

    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending EMAIL to " + recipient + ": " + message);
    }

    @Override
    public String getChannelType() {
        return "EMAIL";
    }
}
