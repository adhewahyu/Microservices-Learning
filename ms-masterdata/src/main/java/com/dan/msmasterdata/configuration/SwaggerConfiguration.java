package com.dan.msmasterdata.configuration;

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

    private static final Tag API_PROVINCE = new Tag("Province APIs", "Provide APIs for Province Operations.");
    private static final Tag API_CITY = new Tag("City APIs", "Provide APIs for City Operations.");

    @Value("${swagger.host}")
    private String swaggerHost;
    
    private Docket getBaseDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.host(swaggerHost)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.msmasterdata.controller"))
                .build()
                .apiInfo(getDefaultApiInfo())
                .useDefaultResponseMessages(false);
    }
    
    private ApiInfo getDefaultApiInfo() {
        return new ApiInfo("MS Master Data Docs", "API sandbox for Master Data service. Only for development purpose and API discovery.", "v1.11", "http://swagger.io/terms/",
    		new Contact("Developer", "", "adhe.wahyu.ardanto@gmail.com"),
    		"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}

    @Bean
    public Docket docket() {
        return getBaseDocket()
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.msmasterdata.controller"))
                .build().groupName("All APIs")
                .tags(API_PROVINCE, API_CITY);
    }
    
    @Bean
    public Docket locationDocket(){
        return getBaseDocket().groupName("Location Recipe").select()
            .paths(PathSelectors.regex("/province/v1/add")
                    .or(PathSelectors.regex("/province/v1/update"))
                    .or(PathSelectors.regex("/province/v1/delete"))

                    .or(PathSelectors.regex("/city/v1/add"))
                    .or(PathSelectors.regex("/city/v1/update"))
                    .or(PathSelectors.regex("/city/v1/delete"))
            )
            .build()
            .tags(API_PROVINCE, API_CITY);
    }
    

}
