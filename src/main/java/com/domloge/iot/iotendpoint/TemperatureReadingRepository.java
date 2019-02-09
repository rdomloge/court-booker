package com.domloge.iot.iotendpoint;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "temperatureReadings")
public interface TemperatureReadingRepository extends PagingAndSortingRepository<TemperatureReading, Integer> {

	List<TemperatureReading> findBySource(String source);
	
	List<TemperatureReading> findByHostname(String hostname);
	
}
