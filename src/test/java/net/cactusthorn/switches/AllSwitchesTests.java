package net.cactusthorn.switches;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SwitchesLoaderTest.class,
	ExistsAndSimpleTest.class,
	ScheduleTest.class
})
public class AllSwitchesTests {

}
