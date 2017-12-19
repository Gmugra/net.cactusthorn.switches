package net.cactusthorn.switches.rules;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.cactusthorn.switches.SwitchParameter;

public abstract class Rule {

	//not private for unit-tests
	protected static final List<String> splitByDot(String str) {
		
		List<String> arr = new ArrayList<>();
		if (str == null) return arr; 
		
		int pos = 0;
		for (int i = 0 ; i < str.length(); i++ ) {
			
			if (str.charAt(i) == '.' ) {
				arr.add(str.substring(pos, i) );
				pos = i + 1;
			}
		}
		arr.add(str.substring(pos ) );
	
		return arr;
	}
	
	protected static final boolean compareWithWildcard(String source, String masked, List<String> maskedSplitted) {
		
		if (source.equals(masked)) return true;
		
		if (source.indexOf('.') == -1 || masked.indexOf('*') == -1) return false; 
		
		List<String> sourceParts = splitByDot(source);
		List<String> maskedParts = splitByDot(masked);
		
		if (sourceParts.size() != maskedParts.size()) return false;
		
		for (int i = 0 ; i < sourceParts.size(); i++ ) {
			if (!sourceParts.get(i).equals(maskedParts.get(i) ) && !"*".equals(maskedParts.get(i) ) ) return false; 
		}
		
		return true;
	}
	
	public abstract boolean active(final LocalDateTime currentDateTime, final SwitchParameter<?>... parameters);
}
