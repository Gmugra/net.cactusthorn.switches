package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.rules.Switches;
import static net.cactusthorn.switches.SwitchParameter.*;

public class HostsTest {

	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void notActiveHosts() {
		assertTrue(switches.turnedOn("notActiveHosts", host("www.debian.org")));
	}
	
	@Test
	public void activeHosts() {
		assertFalse(switches.turnedOn("activeHosts", host("www.debian.org")));
		assertTrue(switches.turnedOn("activeHosts", host("www.wikipedia.org")));
	}
	
	@Test
	public void nullHostName() {
		assertFalse(switches.turnedOn("activeHosts"));
	}
	
	@Test
	public void multipleMaskedHosts() {
		
		assertTrue(switches.turnedOn("multipleMaskedHosts", host("wikipedia.org")));
		assertTrue(switches.turnedOn("multipleMaskedHosts", host("www.wikipedia.org")));
		assertTrue(switches.turnedOn("multipleMaskedHosts", host("en.wikipedia.org")));
		assertFalse(switches.turnedOn("multipleMaskedHosts", host("www.debian.org")));
	}
}
