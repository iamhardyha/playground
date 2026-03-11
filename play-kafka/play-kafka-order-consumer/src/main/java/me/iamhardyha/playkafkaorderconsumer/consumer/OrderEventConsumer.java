package me.iamhardyha.playkafkaorderconsumer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.iamhardyha.playkafkaorderconsumer.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    @KafkaListener(topics = "order.events", groupId = "order-group")
    public void consume(OrderEvent event, Acknowledgment ack) throws InterruptedException {

        log.info("수신 이벤트: {}", event);

        try {
            process(event);
            ack.acknowledge();
            log.info("처리 완료 및 offset commit");
        } catch (Exception e) {
            log.error("처리 실패 - commit 하지 않음. event={}", event, e);
        }

    }

    private void process(OrderEvent event) {
        if ("order-fail".equals(event.orderId())) {
            throw new RuntimeException("테스트용 강제 실패");
        }
    }

}
