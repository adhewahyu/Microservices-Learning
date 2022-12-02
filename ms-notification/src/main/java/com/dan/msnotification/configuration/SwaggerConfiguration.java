package com.dan.msnotification.configuration;

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

    private static final Tag API_EMAIL = new Tag("Email APIs", "Provide APIs for Email Operations.");
    private static final Tag API_SMS = new Tag("SMS APIs", "Provide APIs for SMS Operations.");

    @Value("${swagger.host}")
    private String swaggerHost;
    
    private Docket getBaseDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.host(swaggerHost)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.msnotification.controller"))
                .build()
                .apiInfo(getDefaultApiInfo())
                .useDefaultResponseMessages(false);
    }
    
    private ApiInfo getDefaultApiInfo() {
        return new ApiInfo("MS Notification Docs", "API sandbox for Notification service. Only for development purpose and API discovery.", "v1.11", "http://swagger.io/terms/",
    		new Contact("Developer", "", "adhe.wahyu.ardanto@gmail.com"),
    		"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}

    @Bean
    public Docket docket() {
        return getBaseDocket()
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.msnotification.controller"))
                .build().groupName("All APIs")
                .tags(API_EMAIL, API_SMS);
    }
    
    @Bean
    public Docket emailDocket(){
        return getBaseDocket().groupName("Email Recipe").select()
            .paths(PathSelectors.regex("/email/v1/send")
                    .or(PathSelectors.regex("/email/v1/send-by-template"))
            )
            .build()
            .tags(API_EMAIL);
    }

    @Bean
    public Docket smsDocket(){
        return getBaseDocket().groupName("SMS Recipe").select()
                .paths(PathSelectors.regex("/sms/v1/send")
                )
                .build()
                .tags(API_SMS);
    }
    

}
