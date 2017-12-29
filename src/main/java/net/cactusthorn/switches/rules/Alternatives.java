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
class Alternatives extends Rule {

	@XmlAccessorType(XmlAccessType.NONE)
	private static class Alternative extends Rule {
		
		@XmlAttribute(name = "to") private String switchName;

		@Override public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			throw new UnsupportedOperationException();
		}
	}
	
	static final class AlternativeNamesAdapter extends XmlAdapter<Alternative,String> {
		@Override public String unmarshal(Alternative value) throws Exception {
			String switchName = value.switchName.replaceAll("^[!]+", "").trim();
			if ("".equals(switchName)) {
				//as far ValidationEventHandler is not set for Unmarshaller, exception is hidden during processing
				throw new ValidationException("not allowed");
			}
			return switchName;
		}
		@Override public Alternative marshal(String value) throws Exception {
			Alternative depends = new Alternative();
			depends.switchName = value;
			return depends;
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlJavaTypeAdapter(AlternativeNamesAdapter.class)
	@XmlElement(name = "alternative")
	private Set<String> alternativeNames;

	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Set<String> alternatives() {
		if (!active) return EMPTY_STRING_SET;
		return alternativeNames;
	}

	//Unmarshal Event Callbacks : https://docs.oracle.com/javaee/6/api/javax/xml/bind/Unmarshaller.html
	void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (alternativeNames != null ) {
			String switchName = ((BasicSwitch)parent).name();
			alternativeNames.remove(switchName);
		}
	}
}
