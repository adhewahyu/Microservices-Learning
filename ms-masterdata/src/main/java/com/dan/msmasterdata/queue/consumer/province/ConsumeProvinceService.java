package com.dan.msmasterdata.queue.consumer.province;

import com.dan.msmasterdata.service.province.RefreshProvinceService;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumeProvinceService {

    private final RefreshProvinceService refreshProvinceService;

    @KafkaListener(topics = Constants.KAFKA_TOPIC_MASTERDATA_PROVINSI, groupId = "${spring.kafka.consumer.group-id}")
    public ValidationResponse execute(String kafkaMessage){
        log.info("ConsumeProvinsiService - Called");
        refreshProvinceService.execute(new BaseRequest());
        return ValidationResponse.builder().result(true).build();
    }

}
