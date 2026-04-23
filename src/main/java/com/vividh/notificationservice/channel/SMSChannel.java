package com.vividh.notificationservice.channel;

import org.springframework.stereotype.Component;

@Component
public class SMSChannel implements NotificationChannel {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }

    @Override
    public String getChannelType() {
        return "SMS";
    }
}
