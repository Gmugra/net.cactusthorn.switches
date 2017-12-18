package net.cactusthorn.switches;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import net.cactusthorn.switches.xml.Switches;

public class SwitchesLoader {
	
	private static final Schema SWITCHES_SCHEMA = loadSchema();
	
	private static final Schema loadSchema() {
		InputStream xsd = ClassLoader.getSystemResourceAsStream("switches.xsd");
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Source source = new StreamSource(xsd);
		try {
			return factory.newSchema(source);
		} catch (SAXException saxe ) {
			throw new ExceptionInInitializerError(saxe);
		}
	}

	public Switches load(InputStream is) throws JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(net.cactusthorn.switches.xml.Switches.class);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(SWITCHES_SCHEMA);
		return (Switches) unmarshaller.unmarshal(is);
	}
}
