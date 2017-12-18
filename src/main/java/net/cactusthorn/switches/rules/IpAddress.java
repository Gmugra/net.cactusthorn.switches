package net.cactusthorn.switches.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.NONE)
public class IpAddress extends Rule {

	@XmlAttribute(name = "ipv4")
	protected String ipv4;
	
	@Override
	public String toString() {
		return ipv4;
	}
}
