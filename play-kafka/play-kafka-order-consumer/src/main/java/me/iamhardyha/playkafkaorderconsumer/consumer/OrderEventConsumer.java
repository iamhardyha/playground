package me.iamhardyha.playkafkaorderconsumer.consumer;

import me.iamhardyha.playkafkaorderconsumer.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "order.events")
    public void consume(OrderEvent event, Acknowledgment ack) throws InterruptedException {

        System.out.println("수신 이벤트: " + event);

        // 일부러 처리 시간 지연
        Thread.sleep(10000);

        ack.acknowledge();

        System.out.println("처리 완료 및 offset commit");
    }

}
