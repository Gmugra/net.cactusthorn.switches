package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.rules.Switches;
import static net.cactusthorn.switches.SwitchParameter.*;

public class IpTest {

	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void emptyIP() {
		assertTrue(switches.turnedOn("emptyIP"));
	}
	
	@Test
	public void notActiveIP() {
		assertTrue(switches.turnedOn("notActiveIP", ip("127.0.0.10")));
	}
	
	@Test
	public void activeIP() {
		assertTrue(switches.turnedOn("activeIP", ip("127.0.0.1")));
		assertFalse(switches.turnedOn("activeIP", ip("127.0.1.1")));
	}
	
	@Test
	public void subnet() {
		assertFalse(switches.turnedOn("subnet", ip("127.0.0.1")));
		assertTrue(switches.turnedOn("subnet", ip("10.1.1.99")));
		assertTrue(switches.turnedOn("subnet", ip("10.1.1.50")));
	}
	
	@Test
	public void mixed() {
		assertTrue(switches.turnedOn("mixedIP", ip("127.0.0.33")));
		assertTrue(switches.turnedOn("mixedIP", ip("10.1.1.99")));
		assertTrue(switches.turnedOn("mixedIP", ip("10.1.1.50")));
		assertFalse(switches.turnedOn("mixedIP", ip("127.0.1.10")));
	}
}
