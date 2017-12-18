package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class Switches {

	private Switches() {}
	
	@XmlElement(name = "switch")
	private List<Switch> switches;
	
	public boolean exists(final String switchName ) {
		return switches.stream().anyMatch(s -> switchName.equals(s.name()));
	}

	public boolean active(final String switchName ) {
		
		return active(switchName, null);
	}
	
	public boolean active(final String switchName, final String hostName ) {
		
		Optional<Switch> $switch = switches.stream().filter(s -> switchName.equals(s.name())).findFirst();
		if (!$switch.isPresent()) return false;
		
		final LocalDateTime currentDateTime = LocalDateTime.now();
		
		return $switch.get().active(currentDateTime, hostName );
	}
	
	@Override
	public String toString() {
		return switches.toString();
	}
}
