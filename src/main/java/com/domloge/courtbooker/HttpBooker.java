package com.domloge.courtbooker;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domloge.courtbooker.domain.BookingResult;
import com.domloge.courtbooker.domain.Court;
import com.domloge.courtbooker.domain.TimeSlot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Component
public class HttpBooker implements Booker {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpBooker.class);

	@Autowired
	private HttpUtils httpUtils;
	
	@Override
	public BookingResult book(TimeSlot slot) throws ClientProtocolException, IOException {
		String json = httpUtils.loadCourts(slot);
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();
		List<Court> courts = mapper.readValue(json, typeFactory.constructCollectionLikeType(List.class, Court.class));
		Collections.sort(courts, new Comparator<Court>(){
			@Override
			public int compare(Court c1, Court c2) {
				return c2.getSequence() - c1.getSequence();
			}});
		LOGGER.info(courts.size() + " courts for slot "+slot);
		for (Court court : courts) {
			LOGGER.info("Court: "+court);
		}
		if(courts.size() < 1) {
			BookingResult result = new BookingResult();
			result.setMessage("No courts");
			result.setSuccess(false);
			return result;
		}
		LOGGER.info("Booking "+courts.get(0));
		BookingResult bookingResult = book(slot, courts.get(0));
		return bookingResult;
	}
	
	private BookingResult book(TimeSlot slot, Court court) throws IOException {
		String json = httpUtils.bookCourt(slot, court);
		ObjectMapper mapper = new ObjectMapper();
		BookingResult bookingResult = mapper.readValue(json, BookingResult.class);
		LOGGER.info("Booking result: "+bookingResult);
		if(bookingResult.isSuccess()) {
			String[] lines = {
					"Court booked",
					"Court "+court.getSectorReference(),
					slot.getDate().toString("EEE, dd MMM")+" -- "+slot.getStartTime().toString("HH:mm")				
			};
			Box.create(lines);
		}
		return bookingResult;
	}
}
