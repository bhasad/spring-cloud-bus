package com.alert;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;




@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(AlertConfigProperties.PREFIX)
public class AlertConfigProperties {
    public static final String PREFIX="wm.alerts";
    
    private Map<String, String> subscriptions = new HashMap<String,String>();
    private Map<String, Alert> alerts = new HashMap<String,Alert>();
    
    
    private String refreshServerUrl = null;
    
    private final Map<String, String> status = new HashMap<>();

    public Map<String, String> getSubscriptions() {
        return subscriptions;
    } 

    public AlertConfigProperties()
    {
        System.out.println(status.size());  
    }

    @PostConstruct
    public void initialzeAlerts() 
    {
        
        for(Entry<String,String> alertProp : subscriptions.entrySet())
        {
            String alertComponents [] = StringUtils.split(alertProp.getKey(), ".");
            //throw exception is size is more that three or less than three
            Alert alert = new Alert(alertComponents[0], alertComponents[1], alertComponents[2]);
            alert.setStatus(Boolean.valueOf(alertProp.getValue()));
            this.alerts.put(alertProp.getKey(), alert);
        }
        
    }

    public void setSubscriptions(Map<String, String> subscriptions)
    {
        this.subscriptions = subscriptions;
    }

    public Alert getAlert(String name)
    {
        return this.alerts.get(name);
    }

    public String getRefreshServerUrl()
    {
        return this.refreshServerUrl;
    }

    public String setRefreshServerUrl(String url)
    {
        return this.refreshServerUrl = url;
    }

    public Collection<Alert> getAlerts()
    {
        return alerts.values();
    }

    public Map<String, Object> getAlertProperties()
    {
        Map<String, Object> alertProps = new HashMap<String, Object>();
        for(Alert alert : alerts.values())
        {
            alertProps.put(alert.getAlertPropertyName(), Boolean.toString(alert.getStatus()));
        }
        return alertProps;
    }
}
