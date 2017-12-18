package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "switch")
@XmlAccessorType(XmlAccessType.NONE)
class Switch extends Rule {
	
	private Switch() {}

	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "on")
	private boolean on;
	
	@XmlElement(name = "schedule")
	private Schedule schedule;
	
	@XmlElement(name = "ip")
	private Ip ip;
	
	@XmlElement(name = "hosts")
	private Hosts hosts;
	
	String name() {
		return name;
	}

	boolean active(final LocalDateTime currentDateTime, final String hostName) {
		
		if (!on) return false;
		
		//currentDateTime can not be null here
		if (schedule != null && !schedule.active(currentDateTime)) {
			return false;
		}
		
		//when the hostName is not provided, hosts related rules are ignored
		if (hostName != null && hosts != null && !hosts.active(hostName)) {
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
