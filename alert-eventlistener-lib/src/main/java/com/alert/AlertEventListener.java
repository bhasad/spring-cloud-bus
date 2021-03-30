package com.alert;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

/**
 * Chat Event Monitor
 */
public class AlertEventListener implements ApplicationListener<AlertEvent> {
    
    @Autowired
    private AlertEnvironmentManager aem;

    private String name;

    @Autowired
    private Environment env;


    public AlertEventListener(String name){
        this.name = name;
    }

    @Override
    public void onApplicationEvent(AlertEvent event){
       System.out.println(String.format("name is %s application%s Application%s Quietly say:\"%s\"",
                name,
                event.getOriginService(),
                event.getDestinationService(),
                event.getMessage()));
        Date dt = new Date();
        aem.setProperty("alerts.eon1.source1.alert1", dt.toString());
                //it would update alertConfig only when version is greater >
                //you would that 
    }
}