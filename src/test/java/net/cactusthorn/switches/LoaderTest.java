package net.cactusthorn.switches;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import net.cactusthorn.switches.xml.Switches;

public class LoaderTest {

	@Test
	public void loadVerySimple() throws JAXBException {
		
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		
		Switches switches =new Loader().load(is);
		
		assertNotNull(switches);
	}
}
