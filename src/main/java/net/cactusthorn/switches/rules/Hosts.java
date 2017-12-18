package net.cactusthorn.switches.rules;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hosts")
@XmlAccessorType(XmlAccessType.NONE)
public class Hosts extends Rule {
	
	private Hosts() {}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "host")
	private List<Host> hosts;

	boolean active(final String hostName) {
		
		return !active || hosts.stream().anyMatch(h -> h.active(hostName));
	}
}
