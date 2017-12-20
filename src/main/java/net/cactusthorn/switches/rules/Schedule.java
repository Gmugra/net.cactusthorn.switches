package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.cactusthorn.switches.SwitchParameter;
import net.cactusthorn.switches.xml.LocalDateTimeAdapter;

@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.NONE)
class Schedule extends Rule {

	private Schedule() {}
	
	@XmlRootElement(name = "timeinterval")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class TimeInterval extends Rule {
		
		private TimeInterval() {}

		@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class, type = LocalDateTime.class)
		@XmlAttribute(name = "from")
		private LocalDateTime from;
		
		@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class, type = LocalDateTime.class)
		@XmlAttribute(name = "to")
		private LocalDateTime to;
		
		@Override
		public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			
			if (from != null && currentDateTime.isBefore(from) ) {
				return false;
			}
			if (to != null && currentDateTime.isAfter(to) ) {
				return false;
			}
			return true;
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "timeinterval")
	private List<TimeInterval> timeIntervals;
	
	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		return !active || timeIntervals.stream().anyMatch(t -> t.active(currentDateTime));
	}
}
