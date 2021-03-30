package com.baeldung;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class AlertConfigs {

    public AlertConfigs()
    {
        System.out.println("dadasdaa");
    }

    @PostConstruct
    public void initialzeAlerts() 
    {
        System.out.println("dadadsadasdadsas");
    }

    @Value("${alerts.eon1.source1.alert1}")
    public String eon1_source1_alert1;
   
    @Value("${alerts.eon2.source1.alert1}")
    public String eon2_source1_alert1;
    
    @Value("${alerts.eon4.source2.alert1}")
    public String eon4_source2_alert1;

    public void setEon1_source1_alert1(String val)
    {
        this.eon1_source1_alert1 = val;
    }

    public void setEon2_source1_alert1(String val)
    {
        this.eon2_source1_alert1 = val;
    }

    public void setEon4_source2_alert1(String val)
    {
        this.eon4_source2_alert1 = val;
    }

    public String getEon1_source1_alert1()
    {
        return this.eon1_source1_alert1;
    } 

    public String getEon2_source1_alert1()
    {
        return this.eon2_source1_alert1;
    }

    public String getEon4_source2_alert1()
    {
        return this.eon4_source2_alert1;
    }
}
