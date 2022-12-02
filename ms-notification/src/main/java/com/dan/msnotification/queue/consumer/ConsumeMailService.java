package com.dan.msnotification.queue.consumer;

import com.alibaba.fastjson2.JSON;
import com.dan.msnotification.model.request.SendBasicMailRequest;
import com.dan.msnotification.service.SendBasicMailService;
import com.dan.msnotification.utility.Constants;
import com.dan.shared.model.response.ValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumeMailService {

    private final SendBasicMailService sendBasicMailService;

    @KafkaListener(topics = Constants.KAFKA_TOPIC_NOTIFICATION_EMAIL, groupId = "${spring.kafka.consumer.group-id}")
    public ValidationResponse execute(String kafkaMessage){
        log.info("ConsumeMailService - Called");
        sendBasicMailService.execute(JSON.parseObject(kafkaMessage, SendBasicMailRequest.class));
        return ValidationResponse.builder().result(true).build();
    }

}
