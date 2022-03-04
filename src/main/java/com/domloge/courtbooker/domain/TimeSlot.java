package com.domloge.courtbooker.domain;

import org.joda.time.DateTime;

import com.domloge.courtbooker.json.JodaDateDeserialiser;
import com.domloge.courtbooker.json.JodaTimeDeserialiser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSlot {
	
	/*
	 * [{"ParentId":35,"ParentTitle":null,"Name":"Badminton","Id":691,"RemainingSlots":4,"Instructor":null,"Location":"Basingstoke",
	 * 	"Facility":"Basingstoke","FacilityId":1,"Date":"Thu, 15 Feb","StartTime":"07:40","EndTime":"08:20","CanCancel":false,
	 * 	"Status":null,"ExcerciseCategory":null,"CanSelectCourt":true,"Description":null,"CanBook":true,"Items":[]},
	 * {"ParentId":35,"ParentTitle":null,"Name":"Badminton","Id":692,"RemainingSlots":5,"Instructor":null,"Location":"Basingstoke",
	 * 	"Facility":"Basingstoke","FacilityId":1,"Date":"Thu, 15 Feb","StartTime":"08:20","EndTime":"09:00","CanCancel":false,
	 * 	"Status":null,"ExcerciseCategory":null,"CanSelectCourt":true,"Description":null,"CanBook":true,"Items":[]}]
	 */
	
	@JsonProperty("Id")
	private int id;
	
	@JsonProperty("RemainingSlots")
	private int remainingCourts;
	
	@JsonProperty("Date")
	@JsonDeserialize(using=JodaDateDeserialiser.class)
	private DateTime date;
	
	@JsonProperty("StartTime")
	@JsonDeserialize(using=JodaTimeDeserialiser.class)
    private DateTime startTime;
	
	@JsonProperty("EndTime")
	@JsonDeserialize(using=JodaTimeDeserialiser.class)
    private DateTime endTime;
	
	@JsonProperty("CanBook")
    private boolean canBook;
    
	public int getRemainingCourts() {
		return remainingCourts;
	}

	public void setRemainingCourts(int remainingCourts) {
		this.remainingCourts = remainingCourts;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	public boolean isCanBook() {
		return canBook;
	}

	public void setCanBook(boolean canBook) {
		this.canBook = canBook;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Slot [id=" + id + ", remainingCourts=" + remainingCourts + ", date=" + date.toString("EEE, dd MMM") + ", startTime=" + startTime.toString("HH:mm")
				+ ", endTime=" + endTime.toString("HH:mm") + ", canBook=" + canBook + "]";
	}
}
