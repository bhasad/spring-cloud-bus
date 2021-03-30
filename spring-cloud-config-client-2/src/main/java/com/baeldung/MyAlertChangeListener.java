package com.baeldung;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyAlertChangeListener implements ApplicationListener<EnvironmentChangeEvent>{
    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        System.out.println(event);
    }
}
