package com.dan.msmasterdata.queue.publisher.province;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublishProvinceService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void execute(BaseRequest baseRequest){
        log.info("PublishProvinceService - convert and send");
        kafkaTemplate.send(Constants.KAFKA_TOPIC_MASTERDATA_PROVINSI, UUID.randomUUID().toString() , JSON.toJSONString(baseRequest));
    }
}
