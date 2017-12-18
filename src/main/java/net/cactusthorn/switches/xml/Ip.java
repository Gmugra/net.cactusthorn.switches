package net.cactusthorn.switches.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ip")
@XmlAccessorType(XmlAccessType.NONE)
public class Ip {
	
	@XmlAttribute(name = "active")
	protected boolean active = true;
	
	@XmlElement(name = "address")
	List<IpAddress> addresses;
	
	@XmlElement(name = "subnet")
	List<IpSubnet> subnets;
	
	public boolean active() {
		
		if (!active) return true;
		
		//TODO real implementation
		return false;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" 
				+ "active: " + active + ", "
				+ "addresses: " + addresses + ", "
				+ "subnets: " + subnets
				+ ")";
	}
}
