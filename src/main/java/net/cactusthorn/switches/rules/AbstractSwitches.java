package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.Unmarshaller;

import net.cactusthorn.switches.SwitchParameter;

public abstract class AbstractSwitches<S extends BasicSwitch> implements Switches {
	
	protected ConcurrentHashMap<String, S> switchesMap = new ConcurrentHashMap<>();
	
	protected abstract List<S> switches();
	
	//Unmarshal Event Callbacks : https://docs.oracle.com/javaee/6/api/javax/xml/bind/Unmarshaller.html
	void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		switches().forEach(s -> switchesMap.put(s.name(), s));
	}
	
	void updateBy(AbstractSwitches<S> switches) {
		switches.switchesMap.entrySet().forEach(e -> switchesMap.put(e.getKey(), e.getValue()));
		switchesMap.keySet().retainAll(switches.switchesMap.keySet());
	}
	
	@Override
	public boolean exists(final String switchName ) {
		
		String name = switchName;
		if (switchName.indexOf('!') == 0 ) name = switchName.substring(1);
			
		return switchesMap.containsKey(name);
	}
	
	@Override
	public boolean turnedOn(final String switchName, final SwitchParameter<?>... parameters ) {
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		return turnedOnWithNot(switchName, currentDateTime, parameters);
	}
	
	protected boolean turnedOnWithNot(final String switchName, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters ) {
		String name = switchName;
		if (switchName.indexOf('!') == 0 ) name = switchName.substring(1);
		return !name.equals(switchName) ^ turnedOn(name, currentDateTime, parameters);
	}
	
	protected boolean turnedOn(final String switchName, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters ) {
		
		S $switch = switchesMap.get(switchName);
		if ($switch == null) return false;
		
		//any one dependency must be turned on
		if (!activeDependency($switch, currentDateTime, parameters) ) return false;
		
		//no one alternative must be turned on
		if (activeAlternative($switch, currentDateTime, parameters) ) return false;
		
		return $switch.active(currentDateTime, parameters );
	}
	
	protected boolean activeDependency(S $switch, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
	
		Set<String> dependenciesNames = $switch.dependencies();
		if (dependenciesNames.isEmpty() ) return true;
		
		return dependenciesNames.stream().anyMatch(d -> turnedOnWithNot(d, currentDateTime, parameters));
	}
	
	protected boolean activeAlternative(S $switch, final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		Set<String> alternativeNames = $switch.alternatives();
		if (alternativeNames.isEmpty() ) return false;
		return alternativeNames.stream().anyMatch(a -> turnedOn(a, currentDateTime, parameters));
	}
	
}
