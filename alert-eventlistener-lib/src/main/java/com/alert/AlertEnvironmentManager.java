package com.alert;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Entry point for making local (but volatile) changes to the {@link Environment} of a
 * running application. Allows properties to be added and values changed, simply by adding
 * them to a high-priority property source in the existing Environment.
 *
 * @author Dave Syer
 *
 */
@Component
@ManagedResource
public class AlertEnvironmentManager implements ApplicationEventPublisherAware {

	private static final String MANAGER_PROPERTY_SOURCE = "alertConfig";

	private Map<String, Object> map = new LinkedHashMap<String, Object>();

	private ConfigurableEnvironment environment;

	private RefreshScope rs;

	private AlertConfigProperties acp;

	private ApplicationEventPublisher publisher;

	public AlertEnvironmentManager(ConfigurableEnvironment environment, RefreshScope rs, AlertConfigProperties acp) {
		this.environment = environment;
		this.rs = rs;
		this.acp =acp;
		MutablePropertySources sources = environment.getPropertySources();
		if (sources.contains(MANAGER_PROPERTY_SOURCE)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) sources.get(MANAGER_PROPERTY_SOURCE).getSource();
			this.map = map;
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@ManagedOperation
	public Map<String, Object> reset() {
		Map<String, Object> result = new LinkedHashMap<String, Object>(this.map);
		if (!this.map.isEmpty()) {
			this.map.clear();
			publish(new EnvironmentChangeEvent(this.publisher, result.keySet()));
		}
		return result;
	}

	@ManagedOperation
	public void setProperty(String name, String value) {

		if (!this.environment.getPropertySources().contains(MANAGER_PROPERTY_SOURCE)) {
			synchronized (this.map) {
				if (!this.environment.getPropertySources().contains(MANAGER_PROPERTY_SOURCE)) {
					MapPropertySource source = new MapPropertySource(MANAGER_PROPERTY_SOURCE, this.map);
					this.environment.getPropertySources().addFirst(source);
				}
			}
		}

		if (!value.equals(this.environment.getProperty(name))) {
			this.map.put(name, value);
			for(String beanName : acp.getRefreshBeans())
			{
				rs.refresh(beanName);
			}
			publish(new EnvironmentChangeEvent(this.publisher, Collections.singleton(name)));
		}

	}

	@ManagedOperation
	public Object getProperty(String name) {
		return this.environment.getProperty(name);
	}

	private void publish(EnvironmentChangeEvent environmentChangeEvent) {
		if (this.publisher != null) {
			this.publisher.publishEvent(environmentChangeEvent);
		}
	}

}