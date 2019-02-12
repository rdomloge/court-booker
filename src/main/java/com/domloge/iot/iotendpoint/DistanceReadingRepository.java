package com.domloge.iot.iotendpoint;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource(path = "distanceReadings")
public interface DistanceReadingRepository extends PagingAndSortingRepository<DistanceReading, Integer> {

	List<DistanceReading> findBySource(String source);
	
	List<DistanceReading> findByHostname(String hostname);
	
	List<DistanceReading> findByTimeGreaterThanEqual(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
			@Param("time")Date from);
	
	List<DistanceReading> findTop50By();
}
