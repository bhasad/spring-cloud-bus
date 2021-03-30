package com.alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
//import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;






//@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("")
public class AlertConfigProperties {
    public static final String PREFIX="alerts";
    
    //private Map<String, String> subscriptions = new HashMap<String,String>();
    private Map<String, Alert> alertObjs = new HashMap<String,Alert>();
    private Map<String, String> alerts = new HashMap<String,String>();

    private List<String> refreshBeans = new ArrayList<String>();
    
    
    private String refreshServerUrl = null;
    
    private final Map<String, String> status = new HashMap<>();

    @Autowired
    Environment env;

   /*  public Map<String, String> getSubscriptions() {
        return subscriptions;
    } 
 */
    public AlertConfigProperties()
    {
        System.out.println(status.size());  
    }

    @PostConstruct
    public void initialzeAlerts() 
    {
     
        getAllKnownProperties(env);
        for(Entry<String,String> alertProp : alerts.entrySet())
        {
            if(alertProp.getKey().contentEquals("refresh-server-url"))
            {
                setRefreshServerUrl(alertProp.getValue());
            }
            else if(alertProp.getKey().contentEquals("refreshBeans")) {
               refreshBeans = Arrays.asList(alertProp.getValue().split(",", -1));
            }else{
                String alertComponents [] = StringUtils.split(alertProp.getKey(), ".");
                //throw exception is size is more that three or less than three
                Alert alert = new Alert(alertComponents[0], alertComponents[1], alertComponents[2]);
                alert.setStatus(alertProp.getValue());
                this.alertObjs.put(alertProp.getKey(), alert);
            }
            
        }
        
    }

    public static String getAllKnownProperties(Environment env) {
        //Map<String, Object> rtn = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        if (env instanceof ConfigurableEnvironment) {
            for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
                if (propertySource instanceof EnumerablePropertySource) {
                    for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                        //rtn.put(key, propertySource.getProperty(key));
                        sb.append(key).append(" : ").append(propertySource.getProperty(key)).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    /* public void setSubscriptions(Map<String, String> subscriptions)
    {
        this.subscriptions = subscriptions;
    } */

    public Alert getAlert(String name)
    {
        return this.alertObjs.get(name);
    }

    public String getRefreshServerUrl()
    {
        return this.refreshServerUrl;
    }

    public String setRefreshServerUrl(String url)
    {
        return this.refreshServerUrl = url;
    }

    public Collection<Alert> getAlertObjs()
    {
        return alertObjs.values();
    }

    public Map<String,String> getAlerts()
    {
        return alerts;
    }

    public List<String> getRefreshBeans()
    {
        return refreshBeans;
    }

    public Map<String, Object> getAlertProperties()
    {
        Map<String, Object> alertProps = new HashMap<String, Object>();
        for(Alert alert : alertObjs.values())
        {
            alertProps.put(alert.getAlertPropertyName(), alert.getStatus());
        }
        return alertProps;
    }
}
