package net.cactusthorn.switches.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.NONE)
public class IpAddress {

	@XmlAttribute(name = "ipv4")
	protected String ipv4;
	
	@Override
	public String toString() {
		return ipv4;
	}
}
