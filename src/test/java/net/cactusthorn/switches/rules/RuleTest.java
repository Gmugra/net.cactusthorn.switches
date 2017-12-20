package net.cactusthorn.switches.rules;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.cactusthorn.switches.rules.Rule;

public class RuleTest {

	@org.junit.Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void splitOne() {
		assertEquals("[abc, 1234, wer, ddd]", new Rule.SplittedValue("abc.1234.wer.ddd").toString());
	}
	
	@Test
	public void splitAlone() {
		assertEquals("[singletherm]", new Rule.SplittedValue("singletherm").toString());
	}
	
	@Test
	public void splitDot() {
		assertEquals("[, ]", new Rule.SplittedValue(".").toString());
	}
	
	@Test
	public void compareEquals() {
		assertTrue(Rule.compareWithWildcard("AAA.bbb", new Rule.SplittedValue("AAA.bbb")));
	}
	
	@Test
	public void compareWildcard() {
		assertTrue(Rule.compareWithWildcard("AAA.bbb.CCC", new Rule.SplittedValue("AAA.*.CCC")));
	}
	
	@Test
	public void compareNotWildcard() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC", new Rule.SplittedValue("AAA.*.CCC")));
	}
	
	@Test
	public void compareMoreWildcard() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", new Rule.SplittedValue("AAA.*.CCC,*")));
	}
	
	@Test
	public void compareAll() {
		assertTrue(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", new Rule.SplittedValue("*.*.*.*")));
	}
	
	@Test
	public void compareNotEquals() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb.CCC.123", new Rule.SplittedValue("AAA.bbb")));
	}
	
	@Test
	public void compareNotEqualsNotDot() {
		assertFalse(Rule.compareWithWildcard("ZZZbbb", new Rule.SplittedValue("AAA.bbb")));
	}
	
	@Test
	public void compareNotEqualsNotSameSize() {
		assertFalse(Rule.compareWithWildcard("ZZZ.bbb", new Rule.SplittedValue("AAA.bbb.*")));
	}
	
	@Test
	public void immutable() {
		expectedException.expect(UnsupportedOperationException.class);
		new Rule.SplittedValue("*.*.*.*").splitted.add("x");
	}
}
