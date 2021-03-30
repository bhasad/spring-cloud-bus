package com.alert;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@EnableConfigurationProperties
@Configuration(proxyBeanMethods = false)
@Order(0)
public class AlertPropertySourceBootstrapConfiguration {
    
     
    @Bean
	@ConditionalOnMissingBean
	public AlertConfigProperties alertConfigProperties() {
		AlertConfigProperties alertConfig = new AlertConfigProperties();
		return alertConfig;
	}

	@Bean
	@ConditionalOnMissingBean(AlertPropertySourceLocator.class)
	@ConditionalOnProperty(name = AlertConfigProperties.PREFIX + ".refresh-server-url", matchIfMissing = false)
	public AlertPropertySourceLocator alertConfigServicePropertySource(AlertConfigProperties properties) {
		return new AlertPropertySourceLocator(properties);
	}
}

