package net.cactusthorn.switches.xml;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.NONE)
class Schedule {

	private Schedule() {}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "timeinterval")
	private List<TimeInterval> timeIntervals;
	
	boolean active(final LocalDateTime currentDateTime) {
		
		return !active || timeIntervals.stream().anyMatch(t -> t.active(currentDateTime));
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "("
				+ "active: " + active + ", "
				+ timeIntervals
				+ ")";
	}
}
