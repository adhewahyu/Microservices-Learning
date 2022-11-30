package com.dan.msmasterdata.configuration;

import com.dan.msmasterdata.service.province.RefreshProvinceService;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class MasterdataConfiguration {

    private final RefreshProvinceService refreshProvinceService;

    @EventListener(ApplicationReadyEvent.class)
    public void doRefreshProvince(){
        log.info("doRefreshProvince - start");
        refreshProvinceService.execute(new BaseRequest());
        log.info("doRefreshProvince - end");
    }

}
