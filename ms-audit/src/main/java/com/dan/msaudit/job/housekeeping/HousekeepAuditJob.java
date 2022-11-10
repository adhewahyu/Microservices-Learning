package com.dan.msaudit.job.housekeeping;

import com.dan.msaudit.service.HousekeepAuditService;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HousekeepAuditJob {

    private final HousekeepAuditService housekeepAuditService;

    @Scheduled(cron = "${config.scheduler.audit.doHousekeepAudit}")
    @SchedulerLock(name = "doHousekeepAudit", lockAtLeastFor = "15S", lockAtMostFor = "20S")
    public void execute(){
        log.info(">> housekeepAuditService [start]");
        housekeepAuditService.execute(new BaseRequest());
        log.info(">> housekeepAuditService [end]");
    }

}
