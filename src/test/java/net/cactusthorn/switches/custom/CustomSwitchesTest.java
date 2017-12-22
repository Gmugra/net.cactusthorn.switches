package net.cactusthorn.switches.custom;

import static net.cactusthorn.switches.SwitchParameter.*;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import net.cactusthorn.switches.SwitchesXMLLoader;
import net.cactusthorn.switches.XMLSchemaLoader;
import net.cactusthorn.switches.rules.Switches;

public class CustomSwitchesTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException, SAXException {
		InputStream xml = ClassLoader.getSystemResourceAsStream("custom-switches.xml");
		switches = new SwitchesXMLLoader(CustomSwitches.class, XMLSchemaLoader.fromSystemReource("custom-switches.xsd")).load(xml);
	}
	
	@Test
	public void altogether() {
		assertTrue(switches.turnedOn("altogether", of("value","superVALUE"), host("www.debian.org"), ip("127.55.0.1")));
	}
	
	@Test
	public void notAltogether() {
		assertFalse(switches.turnedOn("altogether", of("value","superVALUE?"), host("www.debian.org"), ip("127.55.0.1")));
	}
}
