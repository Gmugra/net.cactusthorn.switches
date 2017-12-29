package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.cactusthorn.switches.SwitchParameter;

import static net.cactusthorn.switches.SwitchParameter.*;

@XmlAccessorType(XmlAccessType.NONE)
class Hosts extends Rule {
	
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Host extends Rule {
		
		@XmlJavaTypeAdapter(RuleSplittedValueAdapter.class)
		@XmlAttribute(name = "name")
		private SplittedValue host;
		
		private boolean check(SwitchParameter<?> parameter) {
			return compareWithWildcard(parameter.stringValue(), host);
		}
		
		@Override protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			
			return find(HOST,parameters).filter(this::check).isPresent();
		}

		@Override public int hashCode() {
			return host.hashCode();
		}

		@Override public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof Host)) return false;
			Host other = (Host) obj;
			return host.equals(other.host);
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "host")
	private Set<Host> hosts;

	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		return !active || hosts.stream().anyMatch(h -> h.active(currentDateTime, parameters) );
	}
}
