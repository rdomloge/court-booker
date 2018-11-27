package com.domloge.courtbooker.criteria;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.domloge.courtbooker.domain.TimeSlot;

class Criterion implements TimeSlotCriteria {

		private int dayOfWeek;
		private int hour;
		private int mins;
		
		public Criterion(int dayOfWeek, int hour, int mins) {
			super();
			this.dayOfWeek = dayOfWeek;
			this.hour = hour;
			this.mins = mins;
		}

		@Override
		public boolean matches(TimeSlot s) {
			DateTime date = s.getDate();
			DateTime startTime = s.getStartTime();
			return date.getDayOfWeek() == dayOfWeek 
					&& startTime.getHourOfDay() == hour 
					&& startTime.getMinuteOfHour() == mins
					&& Days.daysBetween(
								new DateTime().withTimeAtStartOfDay(), 
								date.withTimeAtStartOfDay()
							).getDays() == 7;
		}

		@Override
		public TimeSlotCriteria[] getCriteria() {
			throw new UnsupportedOperationException();
		}

		public String getDayOfWeek() {
			switch (dayOfWeek) {
			case 1:
				return "Monday";
			case 2:
				return "Tuesday";
			case 3:
				return "Wednesday";
			case 4:
				return "Thursday";
			case 5:
				return "Friday";
			case 6:
				return "Saturday";
			case 7:
				return "Sunday";
			default:
				throw new IllegalArgumentException(""+dayOfWeek);
			}
		}

		public int getHour() {
			return hour;
		}

		public int getMins() {
			return mins;
		}
		
	}