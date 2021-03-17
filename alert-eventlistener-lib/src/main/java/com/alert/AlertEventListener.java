package com.alert;
import org.springframework.context.ApplicationListener;

/**
 * Chat Event Monitor
 */
public class AlertEventListener implements ApplicationListener<AlertEvent> {
    
    
    private String name;
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
    }
}