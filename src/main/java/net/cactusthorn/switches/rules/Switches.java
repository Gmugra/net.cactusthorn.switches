package net.cactusthorn.switches.rules;

import net.cactusthorn.switches.SwitchParameter;

public interface Switches {

	boolean exists(final String switchName );
	
	boolean turnedOn(final String switchName, final SwitchParameter<?>... parameters );
}
