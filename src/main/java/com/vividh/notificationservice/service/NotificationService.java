package com.vividh.notificationservice.service;

import com.vividh.notificationservice.channel.NotificationChannel;
import com.vividh.notificationservice.exception.UserNotFoundException;
import com.vividh.notificationservice.kafka.NotificationProducer;
import com.vividh.notificationservice.model.NotificationEvent;
import com.vividh.notificationservice.model.NotificationLog;
import com.vividh.notificationservice.model.NotificationRequest;
import com.vividh.notificationservice.model.User;
import com.vividh.notificationservice.repository.NotificationLogRepository;
import com.vividh.notificationservice.repository.UserRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final Map<String, NotificationChannel> channelMap;
    private final NotificationProducer producer;

    public NotificationService(UserRepository userRepository, NotificationLogRepository notificationLogRepository , List<NotificationChannel> channels, NotificationProducer producer) {
        this.userRepository = userRepository;
        this.notificationLogRepository = notificationLogRepository;
        this.channelMap = channels.stream()
                .collect(Collectors.toMap(
                        NotificationChannel::getChannelType,
                        Function.identity()
                ));
        this.producer = producer;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public String sendNotification(NotificationRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        List<String> channelsToUse;
        if (request.getChannelType() != null && !request.getChannelType().isBlank()) {
            if (!channelMap.containsKey(request.getChannelType())) {
                throw new IllegalArgumentException(
                        "Invalid channel type " + request.getChannelType()
                );
            }
            channelsToUse = List.of(request.getChannelType());
        } else {
            channelsToUse = user.getChannelTypes();
        }
        for (String channelType : channelsToUse) {
            NotificationEvent event = new NotificationEvent(
                    user.getId(),
                    user.getContact(),
                    request.getMessage(),
                    channelType
            );
            producer.sendNotificationEvent(event);
        }
        return "Notification event published successfully!";
    }

    public List<NotificationLog> getLogsByUser(Long userId) {
        return notificationLogRepository.findByUserId(userId);
    }

    public List<NotificationLog> getAllLogs() {
        return notificationLogRepository.findAll();
    }
}
