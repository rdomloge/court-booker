package com.domloge.iot.iotendpoint;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "flowReadings")
public interface FlowReadingRepository extends CrudRepository<FlowReading, Integer> {

	List<FlowReading> findByTimeGreaterThanEqual(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
			@Param("time")Date from);

	List<FlowReading> readAllByFlowLpmGreaterThan(Float lpm);

	List<FlowReading> readAllByFlowLpmGreaterThanAndTimeGreaterThanEqual(
		Float lpm, 
		@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") 
		@Param("time")
		Date from);

	@Modifying
	@Transactional
	int deleteByFlowLpmGreaterThan(Float lpm);

	@Modifying
	@Transactional
	int deleteByTimeLessThanEqual(
		@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") 
		@Param("time")
		Date before);

}
