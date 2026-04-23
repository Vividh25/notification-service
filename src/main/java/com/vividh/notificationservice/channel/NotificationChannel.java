package com.vividh.notificationservice.channel;

public interface NotificationChannel {

    void send(String recipient, String message);
    String getChannelType();
}
