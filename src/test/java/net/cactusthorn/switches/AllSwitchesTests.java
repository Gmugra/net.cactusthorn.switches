package net.cactusthorn.switches;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.cactusthorn.switches.rules.RuleTest;

@RunWith(Suite.class)
@SuiteClasses({
	SwitchesLoaderTest.class,
	ExistsAndSimpleTest.class,
	ScheduleTest.class,
	RuleTest.class,
	HostsTest.class,
	IpTest.class,
	DependenciesTest.class,
	AlternativesTest.class,
	AltogetherTest.class
})
public class AllSwitchesTests {}
