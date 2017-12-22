package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.rules.Switches;

import static net.cactusthorn.switches.SwitchParameter.ip;

public class AlternativesTest {

	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void notActiveAlternatives() {
		assertTrue(switches.turnedOn("notActiveAlternatives"));
	}
	
	@Test
	public void activeAlternatives() {
		assertTrue(switches.turnedOn("notActiveAlternatives"));
	}
	
	@Test
	public void singleActiveAlternative() {
		assertFalse(switches.turnedOn("singleActiveAlternative"));
	}
	
	@Test
	public void mixedIPAlternative() {
		assertFalse(switches.turnedOn("mixedIPAlternative", ip("127.0.0.1")));
		assertTrue(switches.turnedOn("mixedIPAlternative", ip("127.0.1.0")));
	}
	
}
