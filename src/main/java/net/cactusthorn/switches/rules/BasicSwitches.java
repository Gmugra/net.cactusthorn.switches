package net.cactusthorn.switches.rules;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class BasicSwitches extends AbstractSwitches<BasicSwitch> {

	private BasicSwitches() {}
	
	@XmlElement(name = "switch") private List<BasicSwitch> switches;
	
	@Override protected List<BasicSwitch> switches() {
		return switches;
	}
}
