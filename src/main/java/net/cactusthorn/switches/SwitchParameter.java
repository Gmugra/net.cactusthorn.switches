/*******************************************************************************
 * Copyright (C) 2017, Alexei Khatskevich
 * All rights reserved.
 * 
 * Licensed under the BSD 2-clause (Simplified) License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://opensource.org/licenses/BSD-2-Clause
 ******************************************************************************/
package net.cactusthorn.switches;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;

public class SwitchParameter<T> extends SimpleEntry<String,T> {

	private static final long serialVersionUID = 0L;

	private SwitchParameter(String key, T value) {
		super(key,value);
	}
	
	public static <A> SwitchParameter<A> of(String key, A value) {
		return new SwitchParameter<A>(key, value);
	}

	public static final String HOST = "host";
	public static SwitchParameter<String> host(String host) {
		return new SwitchParameter<String>(HOST, host);
	}
	
	public static final String IP = "ip";
	public static SwitchParameter<String> ip(String ip) {
		return new SwitchParameter<String>(IP, ip);
	}
	
	public static Optional<SwitchParameter<?>> find(String name, SwitchParameter<?>... parameters) {
		if (parameters == null) return Optional.empty();
		for (SwitchParameter<?> parameter : parameters ) {
			if (parameter.getKey().equals(name) ) return Optional.of(parameter);
		}
		return Optional.empty();
	}
	
}
