package com.dan.mstask.housekeeping;

import com.dan.mstask.service.task.HousekeepTaskService;
import com.dan.shared.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HousekeepTaskJob {

    private final HousekeepTaskService housekeepTaskService;

    @Scheduled(cron = "${config.scheduler.audit.doHousekeepTask}")
    @SchedulerLock(name = "doHousekeepTask", lockAtLeastFor = "15S", lockAtMostFor = "20S")
    public void execute(){
        log.info(">> housekeepTaskService [start]");
        housekeepTaskService.execute(new BaseRequest());
        log.info(">> housekeepTaskService [end]");
    }

}
