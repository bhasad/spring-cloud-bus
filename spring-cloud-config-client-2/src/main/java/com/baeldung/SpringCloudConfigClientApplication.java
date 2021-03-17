package com.baeldung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RefreshScope
public class SpringCloudConfigClientApplication {

    @Value("${user.role}")
    private String role;

    @Value("${user.password}")
    private String password;

    @Value("${eon1.source1.alert1}")
    private String eon1_source1_alert1;
   
    @Value("${eon2.source1.alert1}")
    private String eon2_source1_alert1;
    
    @Value("${eon4.source2.alert1}")
    private String eon4_source2_alert1;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
	private BusProperties bp;

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }
    
    @RequestMapping(value = "/showProps/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String showProps() {
        //eventPublisher.publishEvent(new MyCustomEvent("this is event message", bp.getId(),username));

        /* RestTemplate rt = new RestTemplate();
        List<AlertQuery> aqs = new ArrayList<AlertQuery>();
        AlertQuery aq = new AlertQuery("dasd", "dasda","dasdasd");
        aqs.add(aq);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //httpEnitity       
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(aqs,headers);
        ResponseEntity<List<Alert>> responseEntity = rt.exchange("http://localhost:8889/alerts", 
                            HttpMethod.POST, requestEntity,new ParameterizedTypeReference<List<Alert>>() {});
        
        List<Alert> allalerts = responseEntity.getBody(); */

       return getAllKnownProperties(env);
       /*   return String.format( "eon1_source1_alert1 : %s eon2.source1.alert1 : %s eon4.source2.alert1 : %s.\n",
         eon1_source1_alert1, eon2_source1_alert1, eon4_source2_alert1);
  */
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
}
