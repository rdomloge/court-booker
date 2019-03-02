package com.domloge.iot.iotendpoint;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "flowReadings")
public interface FlowReadingRepository extends CrudRepository<FlowReading, Integer> {

	List<FlowReading> findByTimeGreaterThanEqual(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
			@Param("time")Date from);
	
	@Query(value =	"SELECT rownum() as ID, * FROM\n" + 
			"(\n" + 
			"SELECT \n" + 
			"  CAST(time as DATE) as time, \n" + 
			"  source, \n" + 
			"  hostname,\n" + 
			"  SUM(flow_lpm) as flow_lpm\n" + 
			"FROM flow_reading \n" + 
			"WHERE time >= :time\n" + 
			"GROUP BY time,hostname\n" + 
			"ORDER BY time\n" + 
			")",
			nativeQuery=true)
	List<FlowReading> findDailyAmountByTimeGreaterThanEqualNative(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
			@Param("time")Date from);
	
//	SELECT 
//	    DATE(created_at) AS create_date,
//	    COUNT(id) AS total
//	FROM
//	    data_table
//	GROUP BY 
//	    DATE(created_at)
	

//	List<FlowReading> readAllByFlowLpmGreaterThan(Float lpm);
//
//	List<FlowReading> readAllByFlowLpmGreaterThanAndTimeGreaterThanEqual(
//		Float lpm, 
//		@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") 
//		@Param("time")
//		Date from);

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
