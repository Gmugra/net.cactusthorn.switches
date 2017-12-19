package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "timeinterval")
@XmlAccessorType(XmlAccessType.NONE)
class TimeInterval extends Rule {
	
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
