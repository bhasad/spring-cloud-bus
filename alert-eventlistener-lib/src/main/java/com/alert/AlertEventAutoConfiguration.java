package com.alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@ConditionalOnClass(AlertEventListener.class)
@RemoteApplicationEventScan(basePackageClasses=AlertEvent.class)
public class AlertEventAutoConfiguration {

   /*  @Autowired
    private AlertEnvironmentManager aem; */

    @Autowired
    private AlertConfigProperties acp;

    @Bean
	@ConditionalOnMissingBean
	public AlertEnvironmentManager alertEnvironmentManager(ConfigurableEnvironment environment, RefreshScope rs) {
		return new AlertEnvironmentManager(environment, rs,acp);
	}

    @Bean
    public AlertEventListener AlertEventListener(){
        return new AlertEventListener("alertevent lib");
    }
    
}
