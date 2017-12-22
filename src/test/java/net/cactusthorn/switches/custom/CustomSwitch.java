package net.cactusthorn.switches.custom;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;
import net.cactusthorn.switches.rules.BasicSwitch;

@XmlRootElement(name = "switch")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomSwitch extends BasicSwitch {
	
	@XmlElement(name = "values")
	private Values values;

	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {
		
		if (!super.active(currentDateTime, parameters)) return false;
		
		if (values != null && !values.active(currentDateTime, parameters ) ) {
			return false;
		}
		
		return true;
	}
}
