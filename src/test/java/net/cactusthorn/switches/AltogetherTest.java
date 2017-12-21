package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.rules.Switches;
import static net.cactusthorn.switches.SwitchParameter.*;

public class AltogetherTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void altogether() {
		assertTrue(switches.turnedOn("altogether", host("www.debian.org"), ip("127.55.0.1")));
	}
}
