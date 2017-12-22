package net.cactusthorn.switches.custom;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.rules.AbstractSwitches;
import net.cactusthorn.switches.rules.BasicSwitch;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomSwitches extends AbstractSwitches {

	private CustomSwitches() {}
		
	@XmlElement(name = "switch")
	private List<CustomSwitch> switches;
		
	@Override
	protected List<? extends BasicSwitch> getSwitches() {
		return switches;
	}
}
