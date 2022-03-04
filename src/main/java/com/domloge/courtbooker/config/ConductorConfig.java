package com.domloge.courtbooker.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.domloge.courtbooker.Conductor;
import com.domloge.courtbooker.criteria.TimeSlotCriteria;

@Configuration
public class ConductorConfig {

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Lazy(value = true)
	public Conductor conductor(TimeSlotCriteria criteria) {
		return new Conductor(criteria);
	}
}
