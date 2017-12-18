package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;

import net.cactusthorn.switches.rules.Switches;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

public class HostsTest {

	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void notActiveHosts() {
		assertTrue(switches.active("notActiveHosts", "www.debian.org"));
	}
	
	@Test
	public void activeHosts() {
		assertFalse(switches.active("activeHosts", "www.debian.org"));
		assertTrue(switches.active("activeHosts", "www.wikipedia.org"));
	}
	
	@Test
	public void nullHostName() {
		
		//when the hostName is not provided, hosts related rules are ignored
		assertTrue(switches.active("activeHosts"));
		assertTrue(switches.active("activeHosts", null));
	}
	
	
	@Test
	public void multipleMaskedHosts() {
		
		assertTrue(switches.active("multipleMaskedHosts", "wikipedia.org"));
		assertTrue(switches.active("multipleMaskedHosts", "www.wikipedia.org"));
		assertTrue(switches.active("multipleMaskedHosts", "en.wikipedia.org"));
		assertFalse(switches.active("multipleMaskedHosts", "www.debian.org"));
	}
}
