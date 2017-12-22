package net.cactusthorn.switches;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.rules.Switches;


public class ScheduleTest {
	
	static Switches switches;
	
	@BeforeClass
	public static void init() throws JAXBException {
		InputStream is = ClassLoader.getSystemResourceAsStream("switches.xml");
		switches = new SwitchesXMLLoader().load(is);
	}
	
	@Test
	public void notActiveSchedule() {
		assertTrue(switches.turnedOn("notActiveSchedule"));
	}
	
	@Test
	public void activePassedSchedule() {
		assertFalse(switches.turnedOn("activePassedSchedule"));
	}
	
	@Test
	public void activeFutureSchedule() {
		assertFalse(switches.turnedOn("activeFutureSchedule"));
	}

	@Test
	public void activeWorkingSchedule() {
		assertTrue(switches.turnedOn("activeWorkingSchedule"));
	}
	
	@Test
	public void activeMultiSchedule() {
		assertTrue(switches.turnedOn("activeMultiSchedule"));
	}
}
