package net.cactusthorn.switches;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cactusthorn.switches.xml.Switches;

public class Loader {

	public Switches load(InputStream is) throws JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(net.cactusthorn.switches.xml.Switches.class);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		return (Switches) unmarshaller.unmarshal(is);
	}
}
