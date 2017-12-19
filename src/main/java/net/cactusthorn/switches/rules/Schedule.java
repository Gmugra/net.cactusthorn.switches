package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.NONE)
class Schedule extends Rule {

	private Schedule() {}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "timeinterval")
	private List<TimeInterval> timeIntervals;
	
	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		return !active || timeIntervals.stream().anyMatch(t -> t.active(currentDateTime));
	}
}
