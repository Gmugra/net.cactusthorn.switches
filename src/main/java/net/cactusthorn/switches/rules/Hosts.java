package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.cactusthorn.switches.SwitchParameter;

import static net.cactusthorn.switches.SwitchParameter.*;

@XmlRootElement(name = "hosts")
@XmlAccessorType(XmlAccessType.NONE)
class Hosts extends Rule {
	
	private Hosts() {}
	
	@XmlRootElement(name = "host")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Host extends Rule {
		
		private Host() {}
		
		@XmlJavaTypeAdapter(value = RuleSplittedValueAdapter.class, type = SplittedValue.class)
		@XmlAttribute(name = "name")
		private SplittedValue host;
		
		@Override
		protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			
			return find(HOST,parameters).filter(h -> compareWithWildcard((String)h.getValue(), host)).isPresent();
		}
		
		@Override
		public String toString() {
			return host.splitted.toString();
		}

		@Override
		public int hashCode() {
			return host.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
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
