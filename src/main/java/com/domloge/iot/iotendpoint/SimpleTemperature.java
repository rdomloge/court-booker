package com.domloge.iot.iotendpoint;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="simpleTemperature", types=TemperatureReading.class)
public interface SimpleTemperature {
	@Value("#{target.temp}")
	float getValue();
	Date getTime();
}
