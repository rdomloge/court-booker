package com.domloge.courtbooker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.domloge.courtbooker.domain.BookingResult;
import com.domloge.courtbooker.domain.TimeSlot;

public interface Booker {

	BookingResult book(TimeSlot slot) throws ClientProtocolException, IOException;
}
