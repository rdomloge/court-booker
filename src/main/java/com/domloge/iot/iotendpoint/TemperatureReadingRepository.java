package com.domloge.iot.iotendpoint;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource(path = "temperatureReadings")
public interface TemperatureReadingRepository extends PagingAndSortingRepository<TemperatureReading, Integer> {

	List<TemperatureReading> findBySource(String source);
	
	List<TemperatureReading> findByHostname(String hostname);
	
	List<TemperatureReading> findByTimeLessThanEqual(
			@DateTimeFormat(pattern="yyyy-MM-dd")
			@Param("time")Date from);
	
	List<TemperatureReading> findTop50By();
	
}
