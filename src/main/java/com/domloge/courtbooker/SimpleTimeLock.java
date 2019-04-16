package com.domloge.courtbooker;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class SimpleTimeLock implements Timelock {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTimeLock.class);
	
	@Value("${GO_TIME:08:00}")
	private String goTimeStr;
	
	@Override
	public void waitForGoTime() {
		String[] parts = goTimeStr.split(":");
		int hr = Integer.parseInt(parts[0]);
		int mn = Integer.parseInt(parts[1]);
		DateTime goTime = new DateTime().withHourOfDay(hr).withMinuteOfHour(mn).withSecondOfMinute(0).withMillisOfSecond(0);
		if(goTime.isBeforeNow()) {
			goTime = goTime.plusDays(1);
		}
		LOGGER.info("Sleeping until {}", goTime);
		Duration dur = new Duration(new DateTime(), goTime);
		long sleep = dur.getMillis();
		LOGGER.info("Sleeping for {}ms", sleep);
		try {
			Thread.sleep(sleep);
			LOGGER.debug("Awake");
		} 
		catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		}
	}
}
