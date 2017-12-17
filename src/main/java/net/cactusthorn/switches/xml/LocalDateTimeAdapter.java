package net.cactusthorn.switches.xml;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String,LocalDateTime> {

	private static final DateTimeFormatter dateTimeFormatter;
	
	static {
		
		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
		builder.appendPattern("[yyyy-MM-dd'T'HH:mm:ssXXX]");
		builder.appendPattern("[yyyy-MM-dd'T'HH:mm:ss]");
		builder.appendPattern("[yyyy-MM-dd]");
		
		builder
			.parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
			.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			.parseDefaulting(ChronoField.MICRO_OF_SECOND, 0)
			.parseDefaulting(ChronoField.MILLI_OF_SECOND, 0);
		
		dateTimeFormatter = builder.toFormatter();
	}
	
	@Override
	public LocalDateTime unmarshal(String value) throws Exception {
		try {
			ZonedDateTime zdt = ZonedDateTime.parse(value, dateTimeFormatter);
			return zdt.withZoneSameInstant(TimeZone.getDefault().toZoneId() ).toLocalDateTime();
		} catch (DateTimeParseException e ) {
			return LocalDateTime.parse(value, dateTimeFormatter);
		}
	}

	@Override
	public String marshal(LocalDateTime value) throws Exception {
		return value.format(dateTimeFormatter);
	}

}
