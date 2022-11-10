package com.dan.mstask;

import com.dan.shared.utility.SharedUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(value = {"com.dan.shared"},useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                SharedUtility.class,
        }))
@ComponentScan(value = {"com.dan.mstask"})
@EntityScan("com.dan.mstask.model.entity")
@EnableJpaRepositories("com.dan.mstask.repository")
@SpringBootApplication
public class MsTaskApp {

    public static void main(String[] args) {
        SpringApplication.run(MsTaskApp.class, args);
    }

}
