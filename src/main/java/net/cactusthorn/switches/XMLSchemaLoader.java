package net.cactusthorn.switches;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XMLSchemaLoader {

	public static Schema fromInputStream(InputStream is) throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Source source = new StreamSource(is);
		return factory.newSchema(source);
	}
	
	public static Schema fromSystemReource(String resourceName) throws SAXException {
		return fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName));
	}
	
	public static Schema fromSystemReource(ClassLoader classLoader, String resourceName) throws SAXException, IOException {
		return fromInputStream(classLoader.getResource(resourceName ).openStream() );
	}
}
