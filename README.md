
# net.cactusthorn.switches

TODO

---

Switches.xml basic example:

```xml
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

# Dependencies

Core idea that application component need to check _only one_ switch.
All conditions about when switch is turned on must be part of the configuration.
But rules are combined with **AND**, it's simple convention which is perfect fine for most of practical use cases.

However, what to do if you need OR between rules? -> Split feature and use dependencies.
Example:
```xml
<!-- "one" will be active if ip=127.0.0.1 AND hots=www.debian.org -->

<switch name="one" on="true">
	<ip>
		<address ipv4="127.0.0.1"/>
	</ip>
	<hosts>
		<host name="www.debian.org"/>
	</hosts>
</switch>
```

```xml
<!-- "one" will be active if ip=127.0.0.1 OR hots=www.debian.org -->

<switch name="one" on="true">
	<dependencies>
		<depends on="address"/>
		<depends on="host"/>
	</dependencies>
</switch>

<switch name="address" on="true">
	<ip>
		<address ipv4="127.0.0.1"/>
	</ip>
</switch>

<switch name="host" on="true">
	<hosts>
		<host name="www.debian.org"/>
	</hosts>
</switch>
```

---

# "NOT" notation
In real live switches often are using as part of some other configuration(s).
E.g. some application component is waiting some configuration property, annotation or whatever else with switch-name inside,
to check switch state and do the work "left or rigth" way.

Sometimes you need to check that the switch is turned on, and some times that the switch is NOT turned on.
To avoid unnearnessy "client" code and switches configurations exist simple "NOT" notation:
Exclamation character(!) as prefix of switch name.
Example:
```java
switches.turnedOn("first"); //return false

switches.turnedOn("!first"); //return true

```
It's simple convenient.
Especially when you have several components which need to do work depends of _different_ state of the same switch.

Also "NOT" notation is allowed for dependencies.
Example:
```xml
<switch name="first" on="false"/>

<switch name="itIsTurnedOn" on="true">
	<dependencies>
		<depends on="!first"/>
	</dependencies>
</switch>
```

---

# Customization

Supported rule set is basic. You sure need to extend it. To support rules based on HTTP schema, LDAP user roles, or whatever else.
It's relative trivial task.

1. Need to extend XSD-schema by new element(s).
For example, I created new schema **custom-switches.xsd** which provide new element "values":
https://github.com/Gmugra/net.cactusthorn.switches/blob/master/src/test/resources/custom-switches.xsd

2. Most complex part: you need to implement class(es) to unmarshal your new element(s).
All of them are more or less same. You can take as example any implementation from basic set: Hosts, Schedule and so on.
For the **custom-switches.xsd** implementation for the new element "values" is here:
https://github.com/Gmugra/net.cactusthorn.switches/blob/master/src/test/java/net/cactusthorn/switches/custom/Values.java

3. Extend Switch class to support new rule(s). It's very simple. For the example it's looks like that:
```java
package net.cactusthorn.switches.custom;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.cactusthorn.switches.SwitchParameter;
import net.cactusthorn.switches.rules.BasicSwitch;

@XmlAccessorType(XmlAccessType.NONE)
public class CustomSwitch extends BasicSwitch {

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
```java
package net.cactusthorn.switches.custom;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cactusthorn.switches.rules.AbstractSwitches;

@XmlRootElement(name = "switches")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomSwitches extends AbstractSwitches<CustomSwitch> {

	private CustomSwitches() {}

	@XmlElement(name = "switch")
	private List<CustomSwitch> switches;

	@Override
	protected List<CustomSwitch> switches() {
		return switches;
	}
}
```

Thats all. Now you can use SwitchesXMLLoader to load you configuration and use extended rule set, e.g.:
```java
InputStream xml = ClassLoader.getSystemResourceAsStream("custom-switches.xml");
Switches switches = new SwitchesXMLLoader(CustomSwitches.class, XMLSchemaLoader.fromSystemReource("custom-switches.xsd")).load(xml);
```
Full example:
https://github.com/Gmugra/net.cactusthorn.switches/blob/master/src/test/java/net/cactusthorn/switches/custom/CustomSwitchesTest.java

# WatchSwitches

Implementation which run Thread with WatchService to, on the fly, upload changes when the source file changed
  * https://docs.oracle.com/javase/8/docs/api/java/nio/file/WatchService.html

```java
Path xml = Paths.get("/switches/switches.xml");
Schema schema = XMLSchemaLoader.fromSystemReource("custom-switches.xsd");
SwitchesXMLLoader loader = new SwitchesXMLLoader(CustomSwitches.class, schema);
WatchSwitches<CustomSwitch> ws = new WatchSwitches<>(xml, loader );
```

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
