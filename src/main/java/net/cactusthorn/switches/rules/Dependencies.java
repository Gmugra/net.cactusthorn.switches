package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.cactusthorn.switches.SwitchParameter;

@XmlAccessorType(XmlAccessType.NONE)
class Dependencies extends Rule {

	@XmlAccessorType(XmlAccessType.NONE)
	private static class Depends extends Rule {
		
		@XmlAttribute(name = "on") private String switchName;

		@Override public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			throw new UnsupportedOperationException();
		}
	}
	
	static final class DependsNamesAdapter extends XmlAdapter<Depends,String> {
		@Override public String unmarshal(Depends value) throws Exception {
			String switchName = value.switchName.replaceAll("^[!]{2,}", "!").trim();
			if ("!".equals(switchName) || "".equals(switchName)) {
				//as far ValidationEventHandler is not set for Unmarshaller, exception is hidden during processing
				throw new ValidationException("not allowed");
			}
			return switchName;
		}
		@Override public Depends marshal(String value) throws Exception {
			Depends depends = new Depends();
			depends.switchName = value;
			return depends;
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlJavaTypeAdapter(DependsNamesAdapter.class)
	@XmlElement(name = "depends")
	private Set<String> dependsNames;

	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Set<String> dependencies() {
		if (!active) return EMPTY_STRING_SET;
		return dependsNames;
	}
	
	//Unmarshal Event Callbacks : https://docs.oracle.com/javaee/6/api/javax/xml/bind/Unmarshaller.html
	void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (dependsNames != null ) {
			String switchName = ((BasicSwitch)parent).name();
			dependsNames.remove(switchName);
		}
	}
}
