package it.unibo.iot.utils;


public class StringUtils {
	public static String firstToLower(String s) {
		if (s == null)
			return null;
		if (s.length() == 0)
			return "";
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
}
