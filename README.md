
# net.cactusthorn.switches

TODO

---

Switches.xml example:

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
* **AND** client ip match any rule in the ip "section"
* **AND** (switch "simple" is turned on **OR** switch "verysimple" is turned on)
* **AND** (switch "first" is turned off **AND** switch "mega" is turned off)
* hosts are ignored because not active

In simple words: 
* **AND** between blocks, **OR** inside blocks
* except "alternatives" block; all alternative switches should be turned off, only in this case switch with alternative can be active


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
