package net.cactusthorn.switches.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "switch")
@XmlAccessorType(XmlAccessType.NONE)
public class Switch {

	@XmlAttribute(name = "name")
	protected String name;
	
	@XmlAttribute(name = "on")
    protected boolean on;
	
	@XmlElement(name = "schedule")
	protected Schedule schedule;
	
	@XmlElement(name = "ip")
	protected Ip ip;
	
	public String name() {
		return name;
	}

	public boolean active() {
		if (!on) return false;
		
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
