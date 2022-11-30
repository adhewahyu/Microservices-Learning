package com.dan.msaudit;

import com.dan.shared.utility.PermissionUtility;
import com.dan.shared.utility.SharedUtility;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan(value = {"com.dan.shared"},useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                PermissionUtility.class,
                SharedUtility.class,
        }))
@ComponentScan(value = {"com.dan.msaudit"})
@EntityScan("com.dan.msaudit.model.entity")
@EnableJpaRepositories("com.dan.msaudit.repository")
@EnableSchedulerLock(defaultLockAtMostFor = "21s")
@SpringBootApplication
public class MsAuditApp {

    public static void main(String[] args) {
        SpringApplication.run(MsAuditApp.class, args);
    }

}
