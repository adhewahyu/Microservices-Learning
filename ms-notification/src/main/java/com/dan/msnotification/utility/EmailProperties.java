package com.dan.msnotification.utility;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Setter
@Getter
@Configuration
public class EmailProperties {

    @Value("${config.mail.protocol}")
    private String protocol;

    @Value("${config.mail.debug}")
    private Boolean debugMode;

    @Value("${config.mail.auth}")
    private Boolean useAuth;

    @Value("${config.mail.starttls-enable}")
    private Boolean enableTls;

    @Value("${config.mail.host}")
    private String host;

    @Value("${config.mail.port}")
    private Integer port;

    @Value("${config.mail.username}")
    private String username;

    @Value("${config.mail.password}")
    private String password;

    @Value("${config.mail.connect-timeout}")
    private Integer connectTimeout;

    @Value("${config.mail.timeout}")
    private Integer timeout;

    @Value("${config.mail.write-timeout}")
    private Integer writeTimeout;

    public Properties setEmailProperties(){
        Properties props = new Properties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", useAuth);
        props.put("mail.smtp.starttls.enable", enableTls);
        props.put("mail.debug", debugMode);

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.username",username);
        props.put("mail.smtp.password",password);
        props.put("mail.smtp.port",port);

        props.put("mail.smtp.connectiontimeout", connectTimeout);
        props.put("mail.smtp.timeout", timeout);
        props.put("mail.smtp.writetimeout", writeTimeout);

        return props;
    }

}
