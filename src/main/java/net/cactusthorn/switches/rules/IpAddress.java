package net.cactusthorn.switches.rules;

import static net.cactusthorn.switches.SwitchParameter.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.NONE)
public class IpAddress extends Rule {

	protected String ipv4;
	private List<String> splittedIpV4;
	
	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		return find(IP,parameters).filter(ip -> compareWithWildcard((String)ip.getValue(), ipv4, splittedIpV4)).isPresent();
	}
	
	@XmlAttribute(name = "ipv4")
	protected void setIp(String ipv4) {
		this.ipv4 = ipv4; 
		splittedIpV4 = splitByDot(ipv4);
	}
}
