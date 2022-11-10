package com.dan.msgateway;

import com.dan.shared.utility.SharedUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = {"com.dan.shared"},useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                SharedUtility.class
        }))
@ComponentScan(value = {"com.dan.msgateway"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MsGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(MsGatewayApp.class, args);
    }

}
