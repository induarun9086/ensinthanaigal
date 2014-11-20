package com.ensinthanaigal.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminUtil {
	public static final int CREATE = 0;
	public static final int UPDATE = 1;
	public static final int DELETE = 2;

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

	public static boolean checkSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object adminLogin = session.getAttribute("admin_login");
		if (adminLogin != null) {
			return Boolean.valueOf(adminLogin.toString());
		}
		return false;
	}

	public static void postTweet(String postedUrl) {

		String latestStatus = "A new posted has been added to the blog : "
				+ postedUrl;

		/*Map<String, String> credentialsMap = DataUtil.getCredentials("twitter");
		Twitter twitter = new TwitterFactory().getInstance();
		// api key and api secret
		twitter.setOAuthConsumer(credentialsMap.get("APIkey"),
				credentialsMap.get("APIsecret"));

		try {

			AccessToken accessToken = new AccessToken(
					credentialsMap.get("Accesstoken"),
					credentialsMap.get("Accesstokensecret"));
			twitter.setOAuthAccessToken(accessToken);
			Status status = twitter.updateStatus(latestStatus);
			System.out.println("Successfully updated the status to ["
					+ status.getText() + "].");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("status has not been updated due to "
					+ e.getMessage());
		}*/

	}

	public static void witeHTMLToResponse(ServletContext context,
			String fileName, HttpServletResponse response) throws Exception {
		InputStream is = context.getResourceAsStream(fileName);
		OutputStream output = response.getOutputStream();
		byte[] buffer = new byte[is.available()];

		try {
			for (int length = 0; (length = is.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
			}
		} finally {
			try {
				output.close();
			} catch (IOException ignore) {
			}
			try {
				is.close();
			} catch (IOException ignore) {
			}
		}

	}
}
