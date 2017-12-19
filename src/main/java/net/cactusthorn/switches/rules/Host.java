package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;
import static net.cactusthorn.switches.SwitchParameter.*;

@XmlRootElement(name = "host")
@XmlAccessorType(XmlAccessType.NONE)
public class Host extends Rule {
	
	private Host() {}
	
	private String hostName;
	private List<String> splittedHostName;
	
	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		return find(HOST,parameters).filter(h -> compareWithWildcard((String)h.getValue(), hostName, splittedHostName)).isPresent();
	}
	
	@XmlAttribute(name = "name")
	protected void setHostName(String hostName) {
		this.hostName = hostName; 
		splittedHostName = splitByDot(hostName);
	}
}
