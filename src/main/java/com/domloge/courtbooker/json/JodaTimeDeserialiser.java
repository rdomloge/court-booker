package com.domloge.courtbooker.json;

import java.io.IOException;

import org.joda.time.DateTime;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class JodaTimeDeserialiser extends StdDeserializer<DateTime> {
	
	protected JodaTimeDeserialiser(Class<?> vc) {
		super(vc);
	}

	public JodaTimeDeserialiser() {
		this(null);
	}

	@Override
	public DateTime deserialize(JsonParser parser, DeserializationContext ctx)
			throws IOException, JsonProcessingException {
		
		String text = parser.getText();
        return DateTime.parse(text, new DateTimeFormatterFactory("HH:mm").createDateTimeFormatter());
	}
}
