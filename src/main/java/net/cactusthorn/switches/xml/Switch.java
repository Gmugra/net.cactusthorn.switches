package net.cactusthorn.switches.xml;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "switch")
@XmlAccessorType(XmlAccessType.NONE)
class Switch {
	
	private Switch() {}

	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "on")
	private boolean on;
	
	@XmlElement(name = "schedule")
	private Schedule schedule;
	
	@XmlElement(name = "ip")
	private Ip ip;
	
	String name() {
		return name;
	}

	boolean active(final LocalDateTime currentDateTime) {
		
		if (!on) return false;
		
		if (schedule != null && !schedule.active(currentDateTime)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" 
				+ "name: " + name + ", "
				+ "on: " + on + ", "
				+ "schedule: " + schedule + ", "
				+ "ip: " + ip
				+ ")";
	}
}
