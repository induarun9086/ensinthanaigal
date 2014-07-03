package com.ensinthanaigal.server.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

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
			return Boolean.valueOf((boolean) adminLogin);
		}
		return false;
	}

	public static void postTweet(String postedUrl) {

		String latestStatus = "A new posted has been added to the blog : "
				+ postedUrl;

		Twitter twitter = new TwitterFactory().getInstance();
		// api key and api secret
		twitter.setOAuthConsumer("46dxjDFXJ657rLVPLAZCeXVP1",
				"bBZVY2g7GyTOqBd0xH89JmsawumSZ8kqsgcmiFDxtf7pDz5KIh");

		try {
			// RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = new AccessToken(
					"151354520-ROjqoEgZRERdgbymMDYXYPCKJI2Ml8SK8csB5416",
					"OzUO8zWvOYWpnrBfNkOVItdcllzKSiMg42X9GeY6fjRFJ");
			twitter.setOAuthAccessToken(accessToken);
			Status status = twitter.updateStatus(latestStatus);
			System.out.println("Successfully updated the status to ["
					+ status.getText() + "].");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("status has not bee updated due to "
					+ e.getMessage());
		}

	}
}
