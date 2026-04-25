package com.vividh.notificationservice.kafka;

import com.vividh.notificationservice.channel.NotificationChannel;
import com.vividh.notificationservice.model.NotificationEvent;
import com.vividh.notificationservice.model.NotificationLog;
import com.vividh.notificationservice.repository.NotificationLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationConsumer {

    private final Map<String, NotificationChannel> channelMap;
    private final NotificationLogRepository logRepository;

    public NotificationConsumer(List<NotificationChannel> channels, NotificationLogRepository logRepository) {
        this.channelMap = channels.stream()
                .collect(Collectors.toMap(
                        NotificationChannel::getChannelType,
                        Function.identity()
                ));
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "notification-events", groupId = "notification-group")
    public void consume(NotificationEvent event) {
        System.out.println("Consumes event from Kafka: " + event);

        NotificationChannel channel = channelMap.get(event.getChannelType());
        if (channel != null) {
            channel.send(event.getContact(), event.getMessage());
            saveLog(event);
        }
    }

    private void saveLog(NotificationEvent event) {
        NotificationLog log = new NotificationLog();
        log.setUserId(event.getUserId());
        log.setContact(event.getContact());
        log.setChannelType(event.getChannelType());
        log.setMessage(event.getMessage());
        log.setSentAt(LocalDateTime.now());
        logRepository.save(log);
    }
}
