package org.evreyatlanta.util;

public class StringManager {
	public static boolean isNullOrEmpty(String value) {  
		return (value == null || value.equals("N/A") || value.equals("null") || value.length() == 0);
	}	
}
