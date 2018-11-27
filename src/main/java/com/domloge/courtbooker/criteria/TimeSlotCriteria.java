package com.domloge.courtbooker.criteria;

import com.domloge.courtbooker.domain.TimeSlot;

public interface TimeSlotCriteria {
	
	boolean matches(TimeSlot s);
	
	TimeSlotCriteria[] getCriteria();

}
