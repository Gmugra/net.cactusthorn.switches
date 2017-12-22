
# net.cactusthorn.switches

TODO

---

Switches.xml basic example:

```
<?xml version="1.0" encoding="UTF-8"?>
<switches>

	<switch name="first" on="false"/>

	<switch name="simple" on="true"/>

	<switch name="verysimple" on="true"/>

	<switch name="mega" on="false"/>

	<switch name="altogether" on="true">
		<schedule>
			<timeinterval from="2010-03-03T00:00:00" to="2011-03-03T00:00:00+03:00" />
			<timeinterval from="2012-05-05T00:00:00" to="2014-03-10T11:11:11" />
			<timeinterval from="2015-00-00T00:00:00"/>
		</schedule>
		<ip>
			<address ipv4="127.*.0.1"/>
			<subnet ipv4mask="10.1.1.0/24"/>
		</ip>
		<hosts active="false">
			<host name="wikipedia.org"/>
			<host name="*.wikipedia.org"/>
			<host name="www.debian.org"/>
		</hosts>
		<dependencies>
			<depends on="simple"/>
			<depends on="verysimple"/>
		</dependencies>
		<alternatives>
			<alternative to="first"/>
			<alternative to="mega"/>
		</alternatives>
	</switch>

</switches>
```

switch "altogether" is **on** when:
* current datetime match any of the timeintervals
* **AND** client ip match any mask in the "ip" section
* **AND** (switch "simple" is turned on **OR** switch "verysimple" is turned on)
* **AND** (switch "first" is turned off **AND** switch "mega" is turned off)
* hosts are ignored because not active in the example

In simple words:
* **AND** between rules-blocks, **OR** between conditions inside rule-blocks
* except "alternatives" block; all alternative switches should be turned off, only in this case switch with alternative can be active

---

# Customization

Supported rule set is basic. You sure need to extend it. To support rules based on HTTP schema, LDAP user roles, or whatever else.
It's relative trivial task.

1. Need to extend XSD-schema by new element(s).
For example, I created new schema **custom-switches.xsd** which provide new element "values":
https://github.com/Gmugra/net.cactusthorn.switches/blob/master/src/test/resources/custom-switches.xml

2. Most complex part: you need to implement class(es) to unmarshal your new element(s).
All of them are more or less same. You can take as example any implementation from basic set: Hosts, Schedule and so on.
For the **custom-switches.xsd** implementation for the new element "values" is here:
https://github.com/Gmugra/net.cactusthorn.switches/blob/master/src/test/java/net/cactusthorn/switches/custom/Values.java

3. Extend Switch class to support new rule(s). It's very simple. For the example it's looks like that:
```
package net.cactusthorn.switches.custom;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.SwitchParameter;
import net.cactusthorn.switches.rules.Switch;

@XmlRootElement(name = "switch")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomSwitch extends Switch {

	@XmlElement(name = "values")
	private Values values;

	@Override
	protected boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters) {

		if (!super.active(currentDateTime, parameters)) return false;

		if (values != null && !values.active(currentDateTime, parameters ) ) {
			return false;
		}

		return true;
	}
}
```
4. Implement new child of AbstractSwitches which will use your new CustomSwitch:
```
package net.cactusthorn.switches.custom;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.rules.AbstractSwitches;
import net.cactusthorn.switches.rules.Switch;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomSwitches extends AbstractSwitches {

	private CustomSwitches() {}

	@XmlElement(name = "switch")
	private List<CustomSwitch> switches;

	@Override
	protected List<? extends Switch> getSwitches() {
		return switches;
	}
}
```

Thats all. Now you can use SwitchesXMLLoader to load you configuration and use extended rule set:
```
switches = new SwitchesXMLLoader().load(CustomSwitches.class, schema, xml);
```
Full example:
https://github.com/Gmugra/net.cactusthorn.switches/blob/master/src/test/java/net/cactusthorn/switches/custom/CustomSwitchesTest.java

# License

Released under the BSD 2-Clause License
```
Copyright (C) 2017, Alexei Khatskevich
All rights reserved.

Licensed under the BSD 2-clause (Simplified) License (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://opensource.org/licenses/BSD-2-Clause
```
