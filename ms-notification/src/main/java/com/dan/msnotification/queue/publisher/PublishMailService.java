package com.dan.msnotification.queue.publisher;

import com.alibaba.fastjson2.JSON;
import com.dan.msnotification.model.request.SendBasicMailRequest;
import com.dan.msnotification.utility.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublishMailService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void execute(SendBasicMailRequest sendBasicMailRequest){
        log.info("PublishMailService - convert and send");
        kafkaTemplate.send(Constants.KAFKA_TOPIC_NOTIFICATION_EMAIL, UUID.randomUUID().toString() , JSON.toJSONString(sendBasicMailRequest));
    }
}
