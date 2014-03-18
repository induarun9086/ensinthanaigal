package com.ensinthanaigal.server.util;

public class AdminUtil {
	public static String checkForNullOrEmpty(String stringToCheck,
			String dispStr) throws Exception {
		if (stringToCheck == null || stringToCheck.isEmpty()) {
			throw new Exception("The field " + dispStr
					+ " cannot be empty or null");
		}
		return stringToCheck;
	}

	public static boolean isNullOrEmpty(String str) throws Exception {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotNullOrEmpty(String str) throws Exception {
		return !isNullOrEmpty(str);
	}

}
