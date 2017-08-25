package engine.util;

public class Log {

	private static LogMode logMode = LogMode.DEBUG;

	/**
	 * Sets the level of information to be sent to the console.
	 * 
	 * @param mode
	 *            DEBUG - All information RELEASE - Only information necessary
	 *            to the User (fatal errors, etc) IN_DEV - Only log errors and
	 *            notable events (hybrid between release and debug)
	 */
	public static void setLogMode(LogMode mode) {
		logMode = mode;
	}

	/**
	 * Converts a String[] to a String by concatting each element sequentially.
	 * 
	 * @param array
	 *            The array to convert to a String
	 * @param endl
	 *            The line break character separating each string element. Null
	 *            specifies no break.
	 */
	public static String stringArrayToString(String[] array, String endl) {
		if (array.length > 0) {
			String r = array[0];
			for (int i = 1; i < array.length; ++i) {
				r = r.concat(array[i]).concat(endl);
			}
			return r;
		}
		return null;
	}

	/**
	 * 
	 * Sends info including debug info to the console. Only displays in DEBUG
	 * mode.
	 *
	 * @param x
	 *            The <code>Object</code> to be printed.
	 */
	public static void log(Object o) {
		if (logMode == LogMode.DEBUG) {
			print("INFO", o);
		}
	}

	/**
	 * 
	 * Sends info including debug info to the console. Only displays in IN_DEV
	 * mode.
	 *
	 * @param x
	 *            The <code>Object</code> to be printed.
	 */
	public static void important(Object o) {
		if (logMode == LogMode.IN_DEV || logMode == LogMode.DEBUG) {
			print("IMPORTANT", o);
		}
	}

	/**
	 * 
	 * Sends error info including debug info to the console. Only displays in
	 * IN_DEV mode.
	 *
	 * @param x
	 *            The <code>Object</code> to be printed.
	 */
	public static void error(Object o) {
		err("ERROR", o);
	}

	private static void print(String type, Object o) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String prefix = type + "[@";
		for (int i = stackTraceElements.length - 1; i > 2; i--) {
			StackTraceElement s = stackTraceElements[i];
			prefix = prefix.concat(s.getClassName() + "." + s.getMethodName() + "(" + s.getLineNumber() + ")");
		}
		System.out.println(prefix + "]: \"" + String.valueOf(o) + "\"");
	}

	private static void err(String type, Object o) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String prefix = type + "[@";
		for (int i = stackTraceElements.length - 1; i > 2; i--) {
			StackTraceElement s = stackTraceElements[i];
			prefix = prefix.concat(s.getClassName() + "." + s.getMethodName() + "(" + s.getLineNumber() + ")");
		}
		System.err.println(prefix + "]: \"" + String.valueOf(o) + "\"");
	}
}
