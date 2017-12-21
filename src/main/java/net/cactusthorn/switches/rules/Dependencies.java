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
import javax.xml.bind.annotation.XmlRootElement;
import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "dependencies")
@XmlAccessorType(XmlAccessType.NONE)
class Dependencies extends Rule {

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
		String switchName = ((Switch)parent).name();
		dependsNames = depends.stream().filter(d -> !switchName.equals(d.switchName)).map(d -> d.switchName).collect(Collectors.toSet());
	}
}
