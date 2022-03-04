package com.domloge.courtbooker;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.domloge.courtbooker.criteria.DynamicTimeSlotCriteria;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.domloge"})
@Configuration
public class Bootstrap implements ApplicationRunner, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String[] sourceArgs = args.getSourceArgs();
		if(null == sourceArgs || sourceArgs.length < 1) throw new IllegalArgumentException("No args");
		Conductor c = applicationContext.getBean(Conductor.class, new DynamicTimeSlotCriteria(sourceArgs));
		c.bookCourt();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
