package net.cactusthorn.switches.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.NONE)
public class Schedule {

	@XmlAttribute(name = "active")
	protected boolean active;
	
	@XmlElement(name = "timeinterval")
	List<TimeInterval> timeIntervals;
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "("
				+ "active: " + active + ", "
				+ timeIntervals
				+ ")";
	}
}
