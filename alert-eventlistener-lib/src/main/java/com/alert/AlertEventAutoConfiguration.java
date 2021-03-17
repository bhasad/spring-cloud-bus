package com.alert;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AlertEventListener.class)
@RemoteApplicationEventScan(basePackageClasses=AlertEvent.class)
public class AlertEventAutoConfiguration {

    @Bean
    public AlertEventListener AlertEventListener(){
        return new AlertEventListener("client-2");
    }
    
}
