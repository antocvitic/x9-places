package com.x9.foodle.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.x9.foodle.util.CookieUtils;

public class UserUtils {

	/**
	 * Returns the currently logged in user. Will return null if the user isn't
	 * logged in.
	 * 
	 * @param HttpServletRequest req
	 *            The input HTTP request
	 * @return the currently logged in user, or null if not logged in
	 */
	public static UserModel getCurrentUser(HttpServletRequest req) {
		UserModel user = null;
		user = getCurrentUserLocalAccount(req);
		if (user != null)
			return user;

		user = getCurrentUserFacebookAccount(req);
		if (user != null)
			return user;

		return null;
	}


	private static UserModel getCurrentUserLocalAccount(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session == null) {
			return null;
		}
		Integer uid = null;
		String sessionToken = null;
		uid = (Integer) session.getAttribute(LoginController.LOGGED_IN_SESSION_USERID);
		sessionToken = (String) session.getAttribute(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN);

		if (uid == null) {
			//Load by session cookie if it exists
			String userid = CookieUtils.getUserid(req);
			if (userid != null) {
				uid = new Integer(userid);
				sessionToken = CookieUtils.getSessionToken(req);
				
				session.setAttribute(LoginController.LOGGED_IN_SESSION_USERID, uid);
				session.setAttribute(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN, sessionToken);
				
			} else {
				return null;
			}
		}
				
		UserModel user = UserModel.getFromDbByID(uid);

		if (!user.getSessionToken().equals(sessionToken)) {
			return null;
		}

		return user;
	}

	private static UserModel getCurrentUserFacebookAccount(
			HttpServletRequest req) {
		// TODO: check for a logged in and connected facebook account
		return null;
	}
}
