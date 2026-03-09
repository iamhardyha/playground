package me.iamhardyha.playkafkaorderproducer.api;

import lombok.RequiredArgsConstructor;
import me.iamhardyha.playkafkaorderproducer.api.request.CreateOrderRequest;
import me.iamhardyha.playkafkaorderproducer.application.OrderEventProducer;
import me.iamhardyha.playkafkaorderproducer.dto.OrderEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderEventProducer producer;

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createOrder(@RequestBody CreateOrderRequest request) {

        String orderId = (request.orderId() == null || request.orderId().isBlank()) ? UUID.randomUUID().toString() : request.orderId();
        String type = (request.type() == null || request.type().isBlank()) ? UUID.randomUUID().toString() : request.type();

        OrderEvent event = new OrderEvent(
                UUID.randomUUID().toString(),
                orderId,
                type,
                Instant.now()
        );

        return producer.publish(orderId, event)
                .thenApply(result -> {
                    int partition = result.getRecordMetadata().partition();
                    long offset = result.getRecordMetadata().offset();

                    return ResponseEntity.ok(Map.of(
                        "orderId", orderId,
                        "sendTopic", result.getRecordMetadata().topic(),
                        "partition", partition,
                        "offset", offset
                    ));
                });

    }
}
