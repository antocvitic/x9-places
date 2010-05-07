package com.x9.foodle.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserUtils {

	/**
	 * Returns the currently logged in user. Will return null if the user isn't
	 * logged in.
	 * 
	 * @param HttpServletRequest
	 *            req The input HTTP request
	 * @return the currently logged in user, or null if not logged in
	 */
	public final static int EDIT_LEVEL = 20;
	public final static int MARKCLOSE_LEVEL = 200;
	
	public static UserModel getCurrentUser(HttpServletRequest req,
			HttpServletResponse resp) {
		UserModel user = null;
		user = getCurrentUserLocalAccount(req, resp);
		if (user != null)
			return user;

		user = getCurrentUserFacebookAccount(req, resp);
		if (user != null)
			return user;

		return null;
	}

	private static UserModel getCurrentUserLocalAccount(HttpServletRequest req,
			HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		Cookie[] cookies = req.getCookies();

		Integer uid = null;
		String sessionToken = null;
		boolean thisIsALogin = false;

		if (session != null) {
			uid = (Integer) session
					.getAttribute(LoginController.LOGGED_IN_SESSION_USERID);
			sessionToken = (String) session
					.getAttribute(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN);
		}

		if (uid == null && cookies != null) {
			// load by session cookie if it exists
			for (Cookie c : cookies) {
				if (c.getName()
						.equals(LoginController.LOGGED_IN_SESSION_USERID)) {
					uid = new Integer(c.getValue());
					break;
				}
			}
			for (Cookie c : cookies) {
				if (c.getName().equals(
						LoginController.LOGGED_IN_SESSION_SESSION_TOKEN)) {
					sessionToken = c.getValue();
					break;
				}
			}
		}

		if (uid == null || sessionToken == null)
			return null;

		UserModel user = UserModel.getFromDbByID(uid);
		if (user == null)
			return null;
		
		if (!user.getSessionToken().equals(sessionToken)) {
			return null;
		}

		if (thisIsALogin) {
			LoginController.doPostLoginStuff(user, req, resp, false);
		}

		return user;
	}

	private static UserModel getCurrentUserFacebookAccount(
			HttpServletRequest req, HttpServletResponse resp) {
		// TODO: check for a logged in and connected facebook account
		return null;
	}
}
