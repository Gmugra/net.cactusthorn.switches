package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.rules.Switches;

import static net.cactusthorn.switches.SwitchParameter.ip;

public class DependenciesTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}

	@Test
	public void notActiveDependencies() {
		assertTrue(switches.turnedOn("notActiveDependencies"));
	}
	
	@Test
	public void simpleActiveDependencies() {
		assertTrue(switches.turnedOn("simpleActiveDependencies"));
	}
	
	@Test
	public void notActiveBecauseOfDepends() {
		assertFalse(switches.turnedOn("notActiveBecauseOfDepends"));
	}
	
	@Test
	public void mixedIPDepends() {
		assertTrue(switches.turnedOn("mixedIPDepends", ip("127.0.0.1")));
		assertFalse(switches.turnedOn("mixedIPDepends", ip("127.0.1.0")));
	}
	
	@Test
	public void activeCascade() {
		assertTrue(switches.turnedOn("activeCascade"));
	}
	
	@Test
	public void notActiveCascade() {
		assertFalse(switches.turnedOn("notActiveCascade"));
	}
	
	@Test
	public void myself() {
		assertTrue(switches.turnedOn("myself"));
	}
	
	@Test
	public void notSimple() {
		assertFalse(switches.turnedOn("notSimple"));
	}
	
	@Test
	public void totallyNot() {
		assertTrue(switches.turnedOn("totallyNot"));
	}
}
