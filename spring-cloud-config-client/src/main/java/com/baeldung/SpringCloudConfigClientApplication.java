package com.baeldung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alert.Alert;
import com.alert.AlertEvent;
import com.alert.AlertQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RefreshScope
public class SpringCloudConfigClientApplication {

    @Value("${user.role}")
    private String role;

    @Value("${user.password}")
    private String password;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
	private BusProperties bp;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }
    
    @RequestMapping(value = "/whoami/{username}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        eventPublisher.publishEvent(new AlertEvent("this is event message", bp.getId(),username));
        return String.format("--------Hello %s! You are a(n) %s and your password is '%s'.\n", username, role, password);
    }

    @RequestMapping(value = "/props/{propName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,String> getProps(@PathVariable("propName") String propName) {
      
        //CustomProp cProp = new CustomProp();
        Map<String, String> m = new HashMap<String, String>();
        m.put("aaaa", "1111");
        m.put("bbbb", "2222");
        m.put("cccc", "3333");
        m.put("dddd", "4444");
        return m;
        
    }

    @PostMapping("/alerts")
    public List<Alert> getAlerts(@RequestBody List<AlertQuery> alertQueries)
    {
        for(AlertQuery aq : alertQueries)
        {
            System.out.println(aq.getEon()+" " +aq.getSource()+" "+aq.getName());
        }

        List<Alert> alerts = new ArrayList<Alert>();
        Alert alert1 = new Alert("eon1", "source1", "alert1");
        alert1.setStatus(true);
        Alert alert2 = new Alert("eon1", "source1", "alert2");
        alert2.setStatus(true);
        Alert alert3 = new Alert("eon2", "source1", "alert1");
        alert3.setStatus(false);
        Alert alert4 = new Alert("eon3", "source1", "alert1");
        alert4.setStatus(true);
        alerts.add(alert1);alerts.add(alert2);alerts.add(alert3);alerts.add(alert4);
        return alerts;
    }

}
