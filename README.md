# Notification Service

A Spring Boot REST API that sends notifications to users via multiple channels (Email, SMS, Push). Built to demonstrate composition, dependency injection, and layered architecture in Java.

## Features
- Register users with one or more notification channels
- Send notifications via all registered channels or a specific channel
- Persist notification logs with timestamps
- Clean error responses with global exception handling
- Input validation on all endpoints

## Tech Stack
- Java 17, Spring Boot 3.2
- PostgreSQL
- Spring Data JPA
- Maven

## Design Decisions
**Composition over inheritance** — NotificationChannel is an interface implemented by EmailChannel, SMSChannel, and PushChannel. Adding a new channel requires zero changes to existing code.

**Strategy pattern** — Channels are resolved at runtime via a Map<String, NotificationChannel> injected by Spring. The service never needs to know which channels exist.

**Layered architecture** — Controller handles HTTP, Service handles business logic, Repository handles persistence. Each layer has one responsibility.

## API Endpoints

### Register a user
POST /api/notifications/register
```json
{
    "name": "Vividh",
    "contact": "vividh@example.com",
    "channelTypes": ["EMAIL", "SMS"]
}
```

### Send a notification
POST /api/notifications/send
```json
{
    "userId": 1,
    "message": "Your order has been placed!",
    "channelType": "EMAIL" 
}
```
channelType is optional — omit it to send via all registered channels.

### Get logs for a user
GET /api/notifications/logs/{userId}

### Get all logs
GET /api/notifications/logs

## Setup
1. Clone the repo
2. Create a PostgreSQL database called `notificationdb`
3. Copy `application.properties.example` to `application.properties` and fill in your credentials
4. Run `mvn spring-boot:run`

## Coming soon
- Kafka integration for async notification processing
- Retry logic for failed notifications
