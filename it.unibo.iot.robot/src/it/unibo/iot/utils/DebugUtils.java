package it.unibo.iot.utils;

public class DebugUtils {

	private static boolean debug = false;

	public static void debugOn() {
		debug = true;
	}

	public static void debugOff() {
		debug = false;
	}

	public static void println(String line) {
		if (debug) {
			System.out.println(line);
		}
	}

	public static void printMethod() {
		if (debug) {
			StackTraceElement e = new Exception().getStackTrace()[1];
			String classSimpleName = e.getClassName().substring(e.getClassName().lastIndexOf('.') + 1);
			println(classSimpleName + " - " + e.getMethodName());
		}
	}
}
