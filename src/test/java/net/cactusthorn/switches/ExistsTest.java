package net.cactusthorn.switches;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.xml.Switches;

public class ExistsTest {

	@Test
	public void existsFirst() throws JAXBException {
		
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		
		Switches switches = new Loader().load(is);
		
		assertTrue(switches.exists("first"));
	}
	
	@Test
	public void existsSecond() throws JAXBException {
		
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		
		Switches switches = new Loader().load(is);
		
		assertTrue(switches.exists("second"));
	}
	
	@Test
	public void existsXXX() throws JAXBException {
		
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		
		Switches switches = new Loader().load(is);
		
		assertFalse(switches.exists("XXX"));
	}
}
