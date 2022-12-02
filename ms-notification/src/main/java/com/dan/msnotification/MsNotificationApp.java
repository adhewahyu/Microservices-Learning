package com.dan.msnotification;

import com.dan.shared.configuration.RedisConfiguration;
import com.dan.shared.configuration.RestConfiguration;
import com.dan.shared.utility.CacheUtility;
import com.dan.shared.utility.SharedUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan(value = {"com.dan.shared"},useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                SharedUtility.class,
                CacheUtility.class,
                RedisConfiguration.class,
        }))
@ComponentScan(value = {"com.dan.msnotification"})
@EntityScan("com.dan.msnotification.model.entity")
@EnableJpaRepositories("com.dan.msnotification.repository")
@EnableAsync
@SpringBootApplication
public class MsNotificationApp {

    public static void main(String[] args) {
        SpringApplication.run(MsNotificationApp.class, args);
    }

}