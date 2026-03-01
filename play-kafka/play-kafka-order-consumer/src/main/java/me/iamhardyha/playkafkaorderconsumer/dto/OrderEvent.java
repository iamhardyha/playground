package me.iamhardyha.playkafkaorderconsumer.dto;


import java.time.Instant;

public record OrderEvent(
        String eventId,
        String orderId,
        String type,
        Instant occurredAt
) { }
