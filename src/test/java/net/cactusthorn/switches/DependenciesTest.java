package net.cactusthorn.switches;

import static net.cactusthorn.switches.SwitchParameter.ip;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.junit.BeforeClass;
import org.junit.Test;

import net.cactusthorn.switches.rules.Switches;

public class DependenciesTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}

	@Test
	public void notActiveDependencies() {
		assertTrue(switches.active("notActiveDependencies"));
	}
	
	@Test
	public void simpleActiveDependencies() {
		assertTrue(switches.active("simpleActiveDependencies"));
	}
	
	@Test
	public void notActiveBecauseOfDepends() {
		assertFalse(switches.active("notActiveBecauseOfDepends"));
	}
	
	@Test
	public void mixedIPDepends() {
		assertTrue(switches.active("mixedIPDepends", ip("127.0.0.1")));
		assertFalse(switches.active("mixedIPDepends", ip("127.0.1.0")));
	}
	
	@Test
	public void activeCascade() {
		assertTrue(switches.active("activeCascade"));
	}
	
	@Test
	public void notActiveCascade() {
		assertFalse(switches.active("notActiveCascade"));
	}
}
