package com.dan.msnotification.configuration;

import com.dan.msnotification.service.RefreshMailTemplateService;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotificationConfiguration {

    private final RefreshMailTemplateService refreshMailTemplateService;

    @EventListener(ApplicationReadyEvent.class)
    public void doRefreshEmailTemplate(){
        log.info("doRefreshEmailTemplate - start");
        refreshMailTemplateService.execute(new BaseRequest());
        log.info("doRefreshEmailTemplate - end");
    }

}
