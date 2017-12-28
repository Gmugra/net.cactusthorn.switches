package net.cactusthorn.switches;

import org.junit.Test;
import org.xml.sax.SAXException;

import net.cactusthorn.switches.custom.CustomSwitch;
import net.cactusthorn.switches.custom.CustomSwitches;
import net.cactusthorn.switches.rules.WatchSwitches;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

public class WatchSwitchesTest {

	@Test
	public void first() throws URISyntaxException, IOException, JAXBException, InterruptedException, SAXException {
		
		Path xml = Paths.get(getClass().getClassLoader().getResource("custom-switches-watch.xml").toURI());
		Path xmlFalse = Paths.get(getClass().getClassLoader().getResource("custom-switches-watch-false.xml").toURI());
		Path xmlTrue = Paths.get(getClass().getClassLoader().getResource("custom-switches-watch-true.xml").toURI());
		Files.copy(xmlFalse, xml, StandardCopyOption.REPLACE_EXISTING );
	
		Schema schema = XMLSchemaLoader.fromSystemReource("custom-switches.xsd");
		SwitchesXMLLoader loader = new SwitchesXMLLoader(CustomSwitches.class, schema);
		WatchSwitches<CustomSwitch> ws = new WatchSwitches<>(xml, loader );
		
		assertFalse(ws.turnedOn("first"));
		
		Files.copy(xmlTrue, xml, StandardCopyOption.REPLACE_EXISTING );
		Thread.sleep(1000); //give a bit time for WatchSwitches thread to reload files
		
		assertTrue(ws.turnedOn("first"));
	}
	
}
