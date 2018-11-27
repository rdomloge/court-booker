package com.domloge.courtbooker.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class JodaDateDeserialiser extends StdDeserializer<DateTime> {

	private DateFormat format = new SimpleDateFormat("EEE',' dd MMM");
	
	protected JodaDateDeserialiser(Class<?> vc) {
		super(vc);
	}

	public JodaDateDeserialiser() {
		this(null);
	}

	@Override
	public DateTime deserialize(JsonParser parser, DeserializationContext ctx)
			throws IOException, JsonProcessingException {
		
		String text = parser.getText();
//        DateTime dateTime = DateTime.parse(text, new DateTimeFormatterFactory("EEE',' dd MMM").createDateTimeFormatter());
//		DateTime dateTime = DateTime.parse(text, DateTimeFormat.forPattern("EEE',' dd MMM"));
		Date date;
		try {
			date = format.parse(text);
		} 
		catch (ParseException e) {
			throw new IOException("Could not parse "+text, e);
		}
		DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.year().setCopy(DateTime.now().getYear());
        return dateTime;
	}

}
