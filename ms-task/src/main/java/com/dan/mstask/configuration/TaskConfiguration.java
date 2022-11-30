package com.dan.mstask.configuration;

import com.dan.mstask.service.task.HousekeepTaskService;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TaskConfiguration {

    private final HousekeepTaskService housekeepTaskService;

    @EventListener(ApplicationReadyEvent.class)
    public void doHousekeepTaskOnInit(){
        log.info("doHousekeepAuditOnInit - [start]");
        housekeepTaskService.execute(new BaseRequest());
        log.info("doHousekeepAuditOnInit - [end]");
    }

}
