package net.cactusthorn.switches;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import net.cactusthorn.switches.rules.BasicSwitches;
import net.cactusthorn.switches.rules.Switches;

public class SwitchesXMLLoader {
	
	public static final Schema SWITCHES_SCHEMA;
	static {
		try {
			SWITCHES_SCHEMA =  XMLSchemaLoader.fromSystemReource("switches.xsd");
		} catch (SAXException saxe ) {
			throw new ExceptionInInitializerError(saxe);
		}
	}
	
	private Schema switchesSchema = SWITCHES_SCHEMA;
	
	private Class<? extends Switches> switchesClazz = BasicSwitches.class;
	
	public SwitchesXMLLoader() {}
	
	public SwitchesXMLLoader(Class<? extends Switches> switchesClazz, Schema switchesSchema) {
		this.switchesClazz = switchesClazz;
		this.switchesSchema = switchesSchema;
	}
	
	@SuppressWarnings("unchecked")
	public <S extends Switches> S load(InputStream xml) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(switchesClazz);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(switchesSchema );
		return (S) unmarshaller.unmarshal(xml);
	}
	
	@SuppressWarnings("unchecked")
	public <S extends Switches> S load(Path xmlFile) throws JAXBException, IOException {

		return (S) load(Files.newInputStream(xmlFile ));
	}
}
