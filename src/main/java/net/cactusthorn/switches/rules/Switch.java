package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "switch")
@XmlAccessorType(XmlAccessType.NONE)
class Switch extends Rule {
	
	private Switch() {}

	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "on")
	private boolean on;
	
	@XmlElement(name = "schedule")
	private Schedule schedule;
	
	@XmlElement(name = "ip")
	private Ip ip;
	
	@XmlElement(name = "hosts")
	private Hosts hosts;
	
	@XmlElement(name = "dependencies")
	private Dependencies dependencies;
	
	@XmlElement(name = "alternatives")
	private Alternatives alternatives;
	
	public String name() {
		return name;
	}
	
	@Override
	protected List<String> dependencies() {
		if (dependencies == null )return EMPTY_STRING_LIST;
		return dependencies.dependencies();
	}
	
	@Override
	protected List<String> alternatives() {
		if (alternatives == null )return EMPTY_STRING_LIST;
		return alternatives.alternatives();
	}
	
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
