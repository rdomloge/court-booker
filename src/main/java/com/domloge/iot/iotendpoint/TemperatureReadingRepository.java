package com.domloge.iot.iotendpoint;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource(path = "temperatureReadings")
public interface TemperatureReadingRepository extends PagingAndSortingRepository<TemperatureReading, Integer> {

	List<TemperatureReading> findByTimeGreaterThan(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm.SSSZZZ")
			@Param("time")Date from);
	
	@Query(value="SELECT rownum() as ID, * FROM\n" + 
			"(\n" +
			"SELECT \n" + 
			"  CAST(time as DATE) as time, \n" + 
			"  source, \n" + 
			"  hostname,\n" + 
			"  AVG(temp) as temp\n" + 
			"FROM temperature_reading \n" + 
			"WHERE time >= TODAY()\n" + 
			"GROUP BY time,hostname\n" + 
			"ORDER BY time\n" + 
			")", 
			nativeQuery=true)
	TemperatureReading findAverageTempToday();
	
	@Query(value="SELECT rownum() as ID, * FROM\n" + 
			"(\n" +
			"SELECT \n" + 
			"  CAST(time as DATE) as time, \n" + 
			"  source, \n" + 
			"  hostname,\n" + 
			"  MIN(temp) as temp\n" + 
			"FROM temperature_reading \n" + 
			"WHERE time >= TODAY()\n" + 
			"GROUP BY time,hostname\n" + 
			"ORDER BY time\n" + 
			")", 
			nativeQuery=true)
	TemperatureReading findMinimumTempToday();
	
	@Query(value="SELECT rownum() as ID, * FROM\n" + 
			"(\n" +
			"SELECT \n" + 
			"  CAST(time as DATE) as time, \n" + 
			"  source, \n" + 
			"  hostname,\n" + 
			"  MAX(temp) as temp\n" + 
			"FROM temperature_reading \n" + 
			"WHERE time >= TODAY()\n" + 
			"GROUP BY time,hostname\n" + 
			"ORDER BY time\n" + 
			")", 
			nativeQuery=true)
	TemperatureReading findMaximumTempToday();
}
