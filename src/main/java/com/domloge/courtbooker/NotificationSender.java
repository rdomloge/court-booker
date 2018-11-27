package com.domloge.courtbooker;

import java.util.List;

import com.domloge.courtbooker.criteria.TimeSlotCriteria;
import com.domloge.courtbooker.domain.TimeSlot;

public interface NotificationSender {

	void sendNoSlotFail(TimeSlotCriteria criteria, List<TimeSlot> slots);
	
	void sendBookingFail(TimeSlotCriteria criteria, List<TimeSlot> matched, String failMessage);
	
	void sendSuccess(TimeSlotCriteria criteria, List<TimeSlot> matched, List<TimeSlot> booked);
}
