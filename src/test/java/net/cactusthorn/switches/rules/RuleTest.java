package net.cactusthorn.switches.rules;

import static org.junit.Assert.*;

import org.junit.Test;

import net.cactusthorn.switches.rules.Rule;

public class RuleTest {

	@Test
	public void splitOne() {
		assertEquals("[abc, 1234, wer, ddd]", Rule.splitByDot("abc.1234.wer.ddd").toString());
	}
	
	@Test
	public void splitAlone() {
		assertEquals("[singletherm]", Rule.splitByDot("singletherm").toString());
	}
	
	@Test
	public void splitDot() {
		assertEquals("[, ]", Rule.splitByDot(".").toString());
	}
	
	@Test
	public void compareEquals() {
		assertTrue(Rule.compareWithWildcard("AAA.bbb", "AAA.bbb"));
	}
	
	@Test
	public void compareWildcard() {
		assertTrue(Rule.compareWithWildcard("AAA.bbb.CCC", "AAA.*.CCC"));
	}
	
	@Test
	public void compareNotWildcard() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC", "AAA.*.CCC"));
	}
	
	@Test
	public void compareMoreWildcard() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", "AAA.*.CCC.*"));
	}
	
	@Test
	public void compareAll() {
		assertTrue(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", "*.*.*.*"));
	}
	
	@Test
	public void compareNotEquals() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", "AAA.bbb"));
	}
	
	@Test
	public void compareNotEqualsNotDot() {
		assertFalse(Rule.compareWithWildcard("ZZZbbb", "AAA.bbb"));
	}
	
	@Test
	public void compareNotEqualsNotSameSize() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb", "AAA.bbb.*"));
	}
}
