package net.cactusthorn.switches.custom;

import static net.cactusthorn.switches.SwitchParameter.find;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;
import net.cactusthorn.switches.rules.Rule;

@XmlRootElement(name = "values")
@XmlAccessorType(XmlAccessType.NONE)
class Values extends Rule {

	private Values() {}
	
	@XmlRootElement(name = "str")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Value extends Rule {
		
		private Value() {}
		
		@XmlAttribute(name = "value")
		private String value;
		
		@Override
		protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			
			return find("value",parameters).filter(v -> value.equals(v.getValue())).isPresent();
		}
		
		@Override
		public String toString() {
			return value;
		}

		@Override
		public int hashCode() {
			return value.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof Value)) return false;
			Value other = (Value) obj;
			return value.equals(other.value);
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "str")
	private Set<Value> strs;
	
	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		return !active || strs.stream().anyMatch(s -> s.active(currentDateTime, parameters) );
	}
}
