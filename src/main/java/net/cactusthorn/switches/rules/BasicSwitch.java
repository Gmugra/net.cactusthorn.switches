package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlAccessorType(XmlAccessType.NONE)
public class BasicSwitch extends Rule {
	
	protected BasicSwitch() {}

	@XmlAttribute(name = "name")
	protected String name;
	
	@XmlAttribute(name = "on")
	protected boolean on;
	
	@XmlElement(name = "schedule")
	protected Schedule schedule;
	
	@XmlElement(name = "ip")
	protected Ip ip;
	
	@XmlElement(name = "hosts")
	protected Hosts hosts;
	
	@XmlElement(name = "dependencies")
	protected Dependencies dependencies;
	
	@XmlElement(name = "alternatives")
	protected Alternatives alternatives;
	
	public String name() {
		return name;
	}
	
	@Override
	protected Set<String> dependencies() {
		if (dependencies == null )return EMPTY_STRING_SET;
		return dependencies.dependencies();
	}
	
	@Override
	protected Set<String> alternatives() {
		if (alternatives == null )return EMPTY_STRING_SET;
		return alternatives.alternatives();
	}
	
	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		if (!on) return false;
		
		//currentDateTime can not be null here
		if (schedule != null && !schedule.active(currentDateTime)) {
			return false;
		}
		
		if (ip != null && !ip.active(currentDateTime, parameters ) ) {
			return false;
		}
		
		if (hosts != null && !hosts.active(currentDateTime, parameters ) ) {
			return false;
		}
		
		return true;
	}
}
