package net.cactusthorn.switches.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "host")
@XmlAccessorType(XmlAccessType.NONE)
public class Host extends Rule {
	
	private Host() {}
	
	@XmlAttribute(name = "name")
	private String maskedHostName;
	
	boolean active(final String hostName) {
		
		return compareWithWildcard(hostName, maskedHostName);
	}
}
