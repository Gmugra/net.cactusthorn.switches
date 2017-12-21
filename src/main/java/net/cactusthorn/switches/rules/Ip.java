package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.net.util.SubnetUtils;

import net.cactusthorn.switches.SwitchParameter;

import static net.cactusthorn.switches.SwitchParameter.*;
import net.cactusthorn.switches.xml.SubnetAdapter;

@XmlRootElement(name = "ip")
@XmlAccessorType(XmlAccessType.NONE)
class Ip extends Rule {
	
	private Ip() {}
	
	@XmlRootElement(name = "address")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class IpAddress extends Rule {

		private IpAddress() {}
		
		@XmlJavaTypeAdapter(value = RuleSplittedValueAdapter.class, type = SplittedValue.class)
		@XmlAttribute(name = "ipv4")
		private SplittedValue ipv4;
		
		@Override
		public boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			
			return find(IP,parameters).filter(ip -> compareWithWildcard((String)ip.getValue(), ipv4)).isPresent();
		}
		
		@Override
		public String toString() {
			return ipv4.splitted.toString();
		}

		@Override
		public int hashCode() {
			return ipv4.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof IpAddress)) return false;
			IpAddress other = (IpAddress) obj;
			return ipv4.equals(other.ipv4);
		}
	}
	
	@XmlRootElement(name = "subnet")
	@XmlAccessorType(XmlAccessType.NONE)
	private static  class IpSubnet extends Rule {
		
		private IpSubnet() {}
		
		@XmlJavaTypeAdapter(value = SubnetAdapter.class, type = SubnetUtils.SubnetInfo.class)
		@XmlAttribute(name = "ipv4mask")
		private SubnetUtils.SubnetInfo subnetInfo;
		
		@Override
		protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
			return find(IP,parameters).filter(ip -> subnetInfo.isInRange((String)ip.getValue())).isPresent();
		}
		
		@Override
		public String toString() {
			return subnetInfo.getCidrSignature();
		}

		@Override
		public int hashCode() {
			return subnetInfo.getCidrSignature().hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof IpSubnet)) return false;
			IpSubnet other = (IpSubnet) obj;
			return subnetInfo.getCidrSignature().equals(other.subnetInfo.getCidrSignature());
		}
	}
	
	@XmlAttribute(name = "active")
	protected boolean active = true;
	
	@XmlElement(name = "address")
	Set<IpAddress> addresses;
	
	@XmlElement(name = "subnet")
	Set<IpSubnet> subnets;
	
	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		if (!active || (addresses == null && subnets == null) ) return true;
		
		boolean validA = addresses != null && addresses.stream().anyMatch(a -> a.active(currentDateTime, parameters) );
		boolean validS = subnets != null && subnets.stream().anyMatch(s -> s.active(currentDateTime, parameters) );
		
		return (validA && subnets == null) || (validS && addresses == null ) || (validA || validS);
	}
}
