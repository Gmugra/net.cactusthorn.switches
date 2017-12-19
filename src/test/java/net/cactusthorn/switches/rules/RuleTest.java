package net.cactusthorn.switches.rules;

import static org.junit.Assert.*;

import org.junit.Test;

import net.cactusthorn.switches.rules.Rule;

import static net.cactusthorn.switches.rules.Rule.*;

public class RuleTest {

	@Test
	public void splitOne() {
		assertEquals("[abc, 1234, wer, ddd]", splitByDot("abc.1234.wer.ddd").toString());
	}
	
	@Test
	public void splitAlone() {
		assertEquals("[singletherm]", splitByDot("singletherm").toString());
	}
	
	@Test
	public void splitDot() {
		assertEquals("[, ]", splitByDot(".").toString());
	}
	
	@Test
	public void compareEquals() {
		assertTrue(Rule.compareWithWildcard("AAA.bbb", "AAA.bbb", splitByDot("AAA.bbb")));
	}
	
	@Test
	public void compareWildcard() {
		assertTrue(Rule.compareWithWildcard("AAA.bbb.CCC", "AAA.*.CCC", splitByDot("AAA.*.CCC")));
	}
	
	@Test
	public void compareNotWildcard() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC", "AAA.*.CCC", splitByDot("AAA.*.CCC")));
	}
	
	@Test
	public void compareMoreWildcard() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", "AAA.*.CCC.*", splitByDot("AAA.*.CCC,*")));
	}
	
	@Test
	public void compareAll() {
		assertTrue(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", "*.*.*.*", splitByDot("*.*.*.*")));
	}
	
	@Test
	public void compareNotEquals() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", "AAA.bbb", splitByDot("AAA.bbb")));
	}
	
	@Test
	public void compareNotEqualsNotDot() {
		assertFalse(Rule.compareWithWildcard("ZZZbbb", "AAA.bbb", splitByDot("AAA.bbb")));
	}
	
	@Test
	public void compareNotEqualsNotSameSize() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb", "AAA.bbb.*", splitByDot("AAA.bbb.*")));
	}
}
