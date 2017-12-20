package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "dependencies")
@XmlAccessorType(XmlAccessType.NONE)
public class Dependencies extends Rule {

	private Dependencies() {}
	
	@XmlRootElement(name = "depends")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Depends extends Rule {
		
		private Depends() {}
		
		@XmlAttribute(name = "on")
		private String switchName;

		@Override
		public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			throw new UnsupportedOperationException();
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "depends")
	private List<Depends> switches;

	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	List<String> dependencies() {
		if (!active) return EMPTY;
		return switches.stream().map(s->s.switchName).collect(Collectors.toList());
	}
}
