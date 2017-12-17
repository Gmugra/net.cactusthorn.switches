package net.cactusthorn.switches.xml;

import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class Switches {

	@XmlElement(name = "switch")
	protected List<Switch> switches;
	
	public boolean exists(final String switchName ) {
		return switches.stream().anyMatch(s -> switchName.equals(s.name()));
	}
	
	public boolean active(final String switchName ) {
		
		Optional<Switch> $switch = switches.stream().filter(s -> switchName.equals(s.name())).findAny();
		if (!$switch.isPresent()) return false;
		
		return switches.stream().anyMatch(s -> switchName.equals(s.name()));
	}
	
	@Override
	public String toString() {
		return switches.toString();
	}
}
