package com.domloge.courtbooker;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.domloge.courtbooker.criteria.TimeSlotCriteria;
import com.domloge.courtbooker.domain.BookingResult;
import com.domloge.courtbooker.domain.Court;
import com.domloge.courtbooker.domain.TimeSlot;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Conductor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Conductor.class);

	@Autowired
	private HttpUtils httpUtils;
	
	@Autowired
	private Booker booker;
	
	private TimeSlotCriteria criteria;
	
	@Autowired
	private NotificationSender notificationSender;
	
	
	public Conductor(TimeSlotCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public void bookCourt() throws ClientProtocolException, IOException {
		LOGGER.info("~~~ START ~~~");
		httpUtils.login();
		
		List<TimeSlot> timeSlots = convert(httpUtils.loadTimetable());
		LOGGER.info(timeSlots.size()+" slots loaded");
		
		List<TimeSlot> desired = find(timeSlots);
		LOGGER.info(desired.size()+" matched");
		
		List<TimeSlot> courtsBooked = new LinkedList<>();
		for (TimeSlot slot : desired) {
			LOGGER.info("Match: "+slot.getDate().toString("EEE, dd MMM")+" -- "+slot.getStartTime().toString("HH:mm"));
			BookingResult result = booker.book(slot);
			if(result.isSuccess()) {
				courtsBooked.add(slot);
			}
			else {
				notificationSender.sendBookingFail(criteria, desired, result.getMessage());
				httpUtils.logout();
				return;
			}
		}
		
		if(courtsBooked.size() > 0) {
			CommitResult result = httpUtils.commit();
			if(result.isSuccess()) {
				notificationSender.sendSuccess(criteria, desired, courtsBooked);
			}
		}
		else {
			notificationSender.sendNoSlotFail(criteria, timeSlots);
		}
		
		httpUtils.logout();
	}
	
	
	
	private List<TimeSlot> find(List<TimeSlot> allSlots) {
		
		List<TimeSlot> match = new LinkedList<TimeSlot>();
		
		for (TimeSlot slot : allSlots) {
			if(criteria.matches(slot)){
				match.add(slot);
			}
		}
		
		return match;
	}
	
	private List<TimeSlot> convert(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();
		List<TimeSlot> slots = mapper.readValue(json, typeFactory.constructCollectionLikeType(List.class, TimeSlot.class));
		return slots;
	}
}
