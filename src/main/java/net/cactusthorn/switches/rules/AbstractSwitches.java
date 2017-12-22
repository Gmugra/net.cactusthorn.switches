package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.cactusthorn.switches.SwitchParameter;

public abstract class AbstractSwitches implements Switches {
	
	protected abstract List<? extends BasicSwitch> getSwitches();
	
	@Override
	public boolean exists(final String switchName ) {
		return getSwitches().stream().anyMatch(s -> switchName.equals(s.name()));
	}
	
	@Override
	public boolean turnedOn(final String switchName, final SwitchParameter<?>... parameters ) {
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		return turnedOn(switchName, currentDateTime, parameters);
	}
	
	protected boolean turnedOn(final String switchName, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters ) {
		
		Optional<? extends BasicSwitch> $switch = getSwitches().stream().filter(s -> switchName.equals(s.name())).findFirst();
		if (!$switch.isPresent()) return false;
		
		//any one dependency must be turned on
		if (!activeDependency($switch.get(), currentDateTime, parameters) ) return false;
		
		//no one alternative must be turned on
		if (activeAlternative($switch.get(), currentDateTime, parameters) ) return false;
		
		return $switch.get().active(currentDateTime, parameters );
	}
	
	protected boolean activeDependency(BasicSwitch $switch, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
	
		Set<String> dependenciesNames = $switch.dependencies();
		if (dependenciesNames.isEmpty() ) return true;
		return dependenciesNames.stream().anyMatch(d -> turnedOn(d, currentDateTime, parameters) );
	}
	
	protected boolean activeAlternative(BasicSwitch $switch, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		Set<String> alternativeNames = $switch.alternatives();
		if (alternativeNames.isEmpty() ) return false;
		return alternativeNames.stream().anyMatch(a -> turnedOn(a, currentDateTime, parameters));
	}
}