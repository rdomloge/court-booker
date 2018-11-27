package com.domloge.courtbooker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResult {
	
	/*
	 * {"Success":true,"AllowRetry":false,"Message":"Booking added to basket","StartTime":"/Date(1518942000000)/",
	 * "EndTime":"/Date(1518944400000)/","FacilityName":"Basingstoke","ActivityName":"Badminton","ResourceLocation":null}
	 */
	
	@JsonProperty("Success")
	private boolean success;

	@JsonProperty("Message")
	private String message;
	
	@JsonProperty("StartTime")
	private String startTimeString;
	
	@JsonProperty("EndTime")
	private String endTimeString;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStartTimeString() {
		return startTimeString;
	}

	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}

	public String getEndTimeString() {
		return endTimeString;
	}

	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}

	@Override
	public String toString() {
		return "BookingResult [success=" + success + ", message=" + message + ", startTimeString=" + startTimeString
				+ ", endTimeString=" + endTimeString + "]";
	}
	
	
}
