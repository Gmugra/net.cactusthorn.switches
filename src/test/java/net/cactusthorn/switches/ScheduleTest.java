package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;

import net.cactusthorn.switches.rules.Switches;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

public class ScheduleTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void notActiveSchedule() {
		assertTrue(switches.active("notActiveSchedule"));
	}
	
	@Test
	public void activePassedSchedule() {
		assertFalse(switches.active("activePassedSchedule"));
	}
	
	@Test
	public void activeFutureSchedule() {
		assertFalse(switches.active("activeFutureSchedule"));
	}

	@Test
	public void activeWorkingSchedule() {
		assertTrue(switches.active("activeWorkingSchedule"));
	}
	
	@Test
	public void activeMultiSchedule() {
		assertTrue(switches.active("activeMultiSchedule"));
	}
}
