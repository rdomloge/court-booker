package com.domloge.courtbooker.criteria;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domloge.courtbooker.domain.TimeSlot;

public class DynamicTimeSlotCriteria implements TimeSlotCriteria {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicTimeSlotCriteria.class);
	
	public static final Map<String, Integer> dayMap = new HashMap<String, Integer>();
	
	static {
		dayMap.put("mon", DateTimeConstants.MONDAY);
		dayMap.put("tue", DateTimeConstants.TUESDAY);
		dayMap.put("wed", DateTimeConstants.WEDNESDAY);
		dayMap.put("thu", DateTimeConstants.THURSDAY);
		dayMap.put("fri", DateTimeConstants.FRIDAY);
		dayMap.put("sat", DateTimeConstants.SATURDAY);
		dayMap.put("sun", DateTimeConstants.SUNDAY);
	}
	
	private TimeSlotCriteria[] criteria;
	
	public DynamicTimeSlotCriteria(String[] args) {
		criteria = new TimeSlotCriteria[args.length];
		for (int i = 0; i < args.length; i++) {
			String string = args[i];
			if( ! string.matches("(mon|tue|wed|thu|fri|sat|sun)\\:\\d{2}:\\d{2}")) {
				throw new IllegalArgumentException(string);				
			}
			String[] parts = string.split(":");
			int day = dayMap.get(parts[0]);
			int hour = Integer.parseInt(parts[1]);
			int min = Integer.parseInt(parts[2]);
			criteria[i] = new Criterion(day, hour, min);
			logger.info("Matching courts for "+day+"("+parts[0]+") at "+hour+":"+min);
		}
	}
	
	@Override
	public boolean matches(TimeSlot s) {
		for (TimeSlotCriteria slotCriteria : criteria) {
			if(slotCriteria.matches(s)) return true;
		}
		return false;
	}

	@Override
	public TimeSlotCriteria[] getCriteria() {
		return criteria;
	}

}
