package com.dan.mstask.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final Tag API_TASKS = new Tag("Task APIs", "Provide APIs for Task Operations.");

    @Value("${swagger.host}")
    private String swaggerHost;
    
    private Docket getBaseDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.host(swaggerHost)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.mstask.controller"))
                .build()
                .apiInfo(getDefaultApiInfo())
                .useDefaultResponseMessages(false);
    }
    
    private ApiInfo getDefaultApiInfo() {
        return new ApiInfo("MS Task Docs", "API sandbox for Task service. Only for development purpose and API discovery.", "v1.11", "http://swagger.io/terms/",
    		new Contact("Developer", "", "adhe.wahyu.ardanto@gmail.com"),
    		"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}

    @Bean
    public Docket docket() {
        return getBaseDocket()
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.mstask.controller"))
                .build().groupName("All APIs")
                .tags(API_TASKS);
    }
    
    @Bean
    public Docket taskDocket(){
        return getBaseDocket().groupName("Task Recipe").select()
            .paths(PathSelectors.regex("/task/v1/add")
                    .or(PathSelectors.regex("/task/v1/submit"))
                    .or(PathSelectors.regex("/task/v1/search")))
            .build()
            .tags(API_TASKS);
    }
    

}
