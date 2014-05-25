package com.ensinthanaigal.server.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdminUtil
{
    public static String checkForNullOrEmpty(String stringToCheck, String dispStr) throws Exception
    {
	if (stringToCheck == null || stringToCheck.isEmpty())
	{
	    throw new Exception("The field " + dispStr
		    + " cannot be empty or null");
	}
	return stringToCheck;
    }

    public static boolean isNullOrEmpty(String str) throws Exception
    {
	if (str == null || str.isEmpty())
	{
	    return true;
	}
	return false;
    }

    public static boolean isNotNullOrEmpty(String str) throws Exception
    {
	return ! isNullOrEmpty(str);
    }

    public static boolean checkSession(HttpServletRequest request)
    {
	HttpSession session = request.getSession();
	Object adminLogin = session.getAttribute("admin_login");
	if(adminLogin != null)
	{
	    return Boolean.valueOf((boolean)adminLogin);
	}
	return false;
    }
}
