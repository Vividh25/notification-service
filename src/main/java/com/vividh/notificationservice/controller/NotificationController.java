package com.vividh.notificationservice.controller;

import com.vividh.notificationservice.model.NotificationLog;
import com.vividh.notificationservice.model.NotificationRequest;
import com.vividh.notificationservice.model.User;
import com.vividh.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(notificationService.registerUser(user));
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @GetMapping("/logs/{userId}")
    public ResponseEntity<List<NotificationLog>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getLogsByUser(userId));
    }

    @GetMapping("/logs")
    public ResponseEntity<List<NotificationLog>> getAllLogs() {
        return ResponseEntity.ok(notificationService.getAllLogs());
    }
}
