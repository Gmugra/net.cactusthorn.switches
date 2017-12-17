package net.cactusthorn.switches.xml;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "timeinterval")
@XmlAccessorType(XmlAccessType.NONE)
public class TimeInterval {

	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class, type = LocalDateTime.class)
	@XmlAttribute(name = "from")
	protected LocalDateTime from;
	
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class, type = LocalDateTime.class)
	@XmlAttribute(name = "to")
	protected LocalDateTime to;
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" 
				+ "from: " + from  + " " 
				+ "to: " + to
				+ ")";
	}
}
