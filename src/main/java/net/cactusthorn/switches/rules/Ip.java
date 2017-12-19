package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "ip")
@XmlAccessorType(XmlAccessType.NONE)
public class Ip extends Rule {
	
	@XmlAttribute(name = "active")
	protected boolean active = true;
	
	@XmlElement(name = "address")
	List<IpAddress> addresses;
	
	@XmlElement(name = "subnet")
	List<IpSubnet> subnets;
	
	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		if (!active || (addresses == null && subnets == null) ) return true;
		
		boolean validA = addresses != null && addresses.stream().anyMatch(a -> a.active(currentDateTime, parameters) );
		boolean validS = subnets != null && subnets.stream().anyMatch(s -> s.active(currentDateTime, parameters) );
		
		return (validA && subnets == null) || (validS && addresses == null ) || (validA || validS);
	}
}
