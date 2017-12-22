package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;

import net.cactusthorn.switches.rules.Switches;

import static org.junit.Assert.*;

import java.io.InputStream;
import javax.xml.bind.JAXBException;

public class ExistsAndSimpleTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}

	@Test
	public void existsFirst() {
		assertTrue(switches.exists("first"));
	}
	
	@Test
	public void existsSecond() {
		assertTrue(switches.exists("second"));
	}
	
	@Test
	public void existsXXX() {
		assertFalse(switches.exists("XXX"));
	}
	
	@Test
	public void simple() {
		assertTrue(switches.turnedOn("simple"));
	}
	
	@Test
	public void first() {
		assertFalse(switches.turnedOn("first"));
	}
	
	@Test
	public void notFirst() {
		assertTrue(switches.turnedOn("!first"));
	}
	
	@Test
	public void existsWithNot() {
		assertTrue(switches.exists("!first"));
	}
}
