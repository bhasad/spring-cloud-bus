package com.alert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Order(0)
public class AlertPropertySourceLocator implements PropertySourceLocator {


    private AlertConfigProperties alertConfigProperties;

    public AlertPropertySourceLocator(AlertConfigProperties props)
    {
        this.alertConfigProperties = props;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        
        RestTemplate rt = creatRestTemplate();
        List<AlertQuery> aqs = createAlertQueries();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //httpEnitity       
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(aqs,headers);
        ResponseEntity<List<Alert>> responseEntity = rt.exchange(alertConfigProperties.getRefreshServerUrl(), 
                            HttpMethod.POST, requestEntity,new ParameterizedTypeReference<List<Alert>>() {});
        
        List<Alert> alerts = responseEntity.getBody();
        Map<String, Object> allAlertProps =  alertConfigProperties.getAlertProperties();
        
        for(Alert alert : alerts)
        {
            allAlertProps.put(alert.getAlertPropertyName(), alert.getAlertPropertyValue());
        }

        MapPropertySource mapPropertySource = new MapPropertySource("alertConfig", allAlertProps);
        return mapPropertySource;
        
    }

    private RestTemplate creatRestTemplate()
    {
        RestTemplate rt = new RestTemplate();
        return rt;
    }

    private List<AlertQuery> createAlertQueries()
    {
        Collection<Alert> alerts = alertConfigProperties.getAlerts();
        List<AlertQuery> alertQueries = new ArrayList<AlertQuery>(alerts.size());
        for(Alert alert : alerts)
        {
            AlertQuery aq = new AlertQuery(alert.getEon(), alert.getSource(), alert.getName());
            alertQueries.add(aq);
        }
        return alertQueries;
    }   
}
