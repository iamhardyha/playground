package me.iamhardyha.playkafkaorderproducer.application;

import lombok.RequiredArgsConstructor;
import me.iamhardyha.playkafkaorderproducer.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public CompletableFuture<SendResult<String, OrderEvent>> publish(String orderId, OrderEvent event) {
        return kafkaTemplate.send(topic, orderId, event); // 핵심은 파티션 키값이 orderId
    }


}
