package net.cactusthorn.switches.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subnet")
@XmlAccessorType(XmlAccessType.NONE)
public class IpSubnet extends Rule {
	
	@XmlAttribute(name = "ipv4mask")
	protected String ipv4mask;
	
	@Override
	public String toString() {
		return ipv4mask;
	}
}
