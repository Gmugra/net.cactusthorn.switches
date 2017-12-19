package net.cactusthorn.switches.rules;

import static net.cactusthorn.switches.SwitchParameter.*;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.net.util.SubnetUtils;

import net.cactusthorn.switches.SwitchParameter;

@XmlRootElement(name = "subnet")
@XmlAccessorType(XmlAccessType.NONE)
public class IpSubnet extends Rule {
	
	private SubnetUtils.SubnetInfo subnetInfo;
	
	@Override
	public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		return find(IP,parameters).filter(ip -> subnetInfo.isInRange((String)ip.getValue())).isPresent();
	}
	
	@XmlAttribute(name = "ipv4mask")
	protected void setMask(String ipv4mask) {
		subnetInfo = new SubnetUtils(ipv4mask).getInfo();
	}
}
