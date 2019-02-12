package com.domloge.iot.iotendpoint;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DistanceReading {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String source;
	
	private float distance_cm;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	private String hostname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public float getDistance_cm() {
		return distance_cm;
	}

	public void setDistance_cm(float distance_cm) {
		this.distance_cm = distance_cm;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}
