package com.dan.msaudit.configuration;

import com.dan.msaudit.service.HousekeepAuditService;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AuditConfiguration {

    private final HousekeepAuditService housekeepAuditService;

    @EventListener(ApplicationReadyEvent.class)
    public void doHousekeepAuditOnInit(){
        log.info("doHousekeepAuditOnInit - [start]");
        housekeepAuditService.execute(new BaseRequest());
        log.info("doHousekeepAuditOnInit - [end]");
    }

}
