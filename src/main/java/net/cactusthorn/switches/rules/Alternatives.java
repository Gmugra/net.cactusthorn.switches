package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlAccessorType(XmlAccessType.NONE)
class Alternatives extends Rule {

	private Alternatives() {}
	
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Alternative extends Rule {
		
		private Alternative() {}
		
		@XmlAttribute(name = "to")
		private String switchName;

		@Override
		public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			throw new UnsupportedOperationException();
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "alternative")
	private List<Alternative> alternative;
	
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
		String switchName = ((BasicSwitch)parent).name();
		alternativeNames =
			alternative.stream()
				.filter(a -> !switchName.equals(a.switchName))
				.map(a -> a.switchName.replaceAll("^[!]+", "").trim())
				.filter(n -> !"!".equals(n))
				.collect(Collectors.toSet());
	}
}
