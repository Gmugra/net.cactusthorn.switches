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
class Dependencies extends Rule {

	@XmlAccessorType(XmlAccessType.NONE)
	private static class Depends extends Rule {
		
		@XmlAttribute(name = "on") private String switchName;

		@Override public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			throw new UnsupportedOperationException();
		}
	}
	
	@XmlAttribute(name = "active")
	private boolean active = true;
	
	@XmlElement(name = "depends")
	private List<Depends> depends;
	
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
		String switchName = ((BasicSwitch)parent).name();
		dependsNames =
			depends.stream()
				.filter(d -> !switchName.equals(d.switchName))
				.map(d -> d.switchName.replaceAll("^[!]{2,}", "!").trim())
				.filter(n -> !"!".equals(n))
				.collect(Collectors.toSet());
	}
}
