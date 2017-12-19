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
		assertTrue(switches.active("emptyIP"));
	}
	
	@Test
	public void notActiveIP() {
		assertTrue(switches.active("notActiveIP", ip("127.0.0.10")));
	}
	
	@Test
	public void activeIP() {
		assertTrue(switches.active("activeIP", ip("127.0.0.1")));
		assertFalse(switches.active("activeIP", ip("127.0.1.1")));
	}
	
	@Test
	public void subnet() {
		assertFalse(switches.active("subnet", ip("127.0.0.1")));
		assertTrue(switches.active("subnet", ip("10.1.1.99")));
		assertTrue(switches.active("subnet", ip("10.1.1.50")));
	}
	
	@Test
	public void mixed() {
		assertTrue(switches.active("mixedIP", ip("127.0.0.33")));
		assertTrue(switches.active("mixedIP", ip("10.1.1.99")));
		assertTrue(switches.active("mixedIP", ip("10.1.1.50")));
		assertFalse(switches.active("mixedIP", ip("127.0.1.10")));
	}
}
