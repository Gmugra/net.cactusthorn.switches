package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.cactusthorn.switches.SwitchParameter;

public abstract class Rule {
	
	static final class RuleSplittedValueAdapter extends XmlAdapter<String,SplittedValue> {

		@Override
		public SplittedValue unmarshal(String value) throws Exception {
			return new SplittedValue(value);
		}

		@Override
		public String marshal(SplittedValue value) throws Exception {
			return value.original;
		}
	}
	
	final static class SplittedValue {
		
		private String original;
		List<String> splitted;
		
		SplittedValue(String original) {
			this.original = original;
			splitByDot();
		}
		
		private final void splitByDot() {
			
			if (original == null) {
				splitted = Collections.emptyList();
				return; 
			}
			
			List<String> parts = new ArrayList<>();
			
			int pos = 0;
			for (int i = 0 ; i < original.length(); i++ ) {
				
				if (original.charAt(i) == '.' ) {
					parts.add(original.substring(pos, i) );
					pos = i + 1;
				}
			}
			parts.add(original.substring(pos ) );
			splitted = Collections.unmodifiableList(parts);
		}

		@Override
		public String toString() {
			return splitted.toString();
		}

		@Override 
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof SplittedValue)) return false;
			SplittedValue other = (SplittedValue) obj;
			return original.equals(other.original);
		}

		@Override
		public int hashCode() {
			return original.hashCode();
		}
	}
	
	protected final static boolean compareWithWildcard(String source, SplittedValue masked) {
		
		if (source.equals(masked.original)) return true;
		
		if (source.indexOf('.') == -1 || masked.original.indexOf('*') == -1) return false; 
		
		SplittedValue sourceSplitted = new Rule.SplittedValue(source);

		if (sourceSplitted.splitted.size() != masked.splitted.size()) return false;
		
		for (int i = 0 ; i < sourceSplitted.splitted.size(); i++ ) {
			if (	!sourceSplitted.splitted.get(i).equals(masked.splitted.get(i) ) 
					&& !"*".equals(masked.splitted.get(i) ) ) return false; 
		}
		
		return true;
	}
	
	protected static final Set<String> EMPTY_STRING_SET = Collections.emptySet();
	
	protected Set<String> dependencies() {
		return EMPTY_STRING_SET;
	}
	
	protected Set<String> alternatives() {
		return EMPTY_STRING_SET;
	}
	
	protected abstract boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters);
}
