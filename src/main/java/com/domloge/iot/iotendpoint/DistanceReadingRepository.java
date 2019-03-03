package com.domloge.iot.iotendpoint;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource(path = "distanceReadings")
public interface DistanceReadingRepository extends PagingAndSortingRepository<DistanceReading, Integer> {

	
	List<DistanceReading> findByTimeGreaterThanEqual(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
			@Param("time")Date from);
	
	@Query(value =	"SELECT rownum() as ID, * FROM\n" + 
			"(\n" + 
			"SELECT \n" + 
			"  CAST(time as DATE) as time, \n" + 
			"  source, \n" + 
			"  hostname,\n" + 
			"  AVG(distance_cm) as distance_cm\n" + 
			"FROM distance_reading \n" + 
			"WHERE time >= :time\n" + 
			"GROUP BY time,hostname\n" + 
			"ORDER BY time\n" + 
			")",
			nativeQuery=true)
	List<DistanceReading> findDailyAverageByTimeGreaterThanEqualNative(
			@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
			@Param("time")Date from);
}
