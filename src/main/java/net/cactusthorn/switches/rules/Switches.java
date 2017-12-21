package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class Switches {

	private Switches() {}
	
	@XmlElement(name = "switch")
	private List<Switch> switches;
	
	public boolean exists(final String switchName ) {
		return switches.stream().anyMatch(s -> switchName.equals(s.name()));
	}
	
	public boolean turnedOn(final String switchName, final SwitchParameter<?>... parameters ) {
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		return turnedOn(switchName, currentDateTime, parameters);
	}
	
	private boolean turnedOn(final String switchName, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters ) {
		
		Optional<Switch> $switch = switches.stream().filter(s -> switchName.equals(s.name())).findFirst();
		if (!$switch.isPresent()) return false;
		
		//any one dependency must be turned on
		if (!activeDependency($switch.get(), currentDateTime, parameters) ) return false;
		
		//no one alternative must be turned on
		if (activeAlternative($switch.get(), currentDateTime, parameters) ) return false;
		
		return $switch.get().active(currentDateTime, parameters );
	}
	
	private boolean activeDependency(Switch $switch, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
	
		List<String> dependenciesNames = $switch.dependencies();
		if (dependenciesNames.isEmpty() ) return true;
		return dependenciesNames.stream().anyMatch(d -> turnedOn(d, currentDateTime, parameters) );
	}
	
	private boolean activeAlternative(Switch $switch, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		List<String> alternativeNames = $switch.alternatives();
		if (alternativeNames.isEmpty() ) return false;
		return alternativeNames.stream().anyMatch(a -> turnedOn(a, currentDateTime, parameters));
	}
}
