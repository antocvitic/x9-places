package com.x9.foodle.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserUtils {

	/**
	 * Returns the currently logged in user. Will return null if the user isn't
	 * logged in.
	 * 
	 * @param req
	 *            the input http request
	 * @return the currently logged in user, or null if not logged in
	 */
	public static UserModel getCurrentUser(HttpServletRequest req) {
		UserModel user = null;
		user = getCurrentUserLocalAccount(req);
		if (user != null)
			return user;

		// TODO: check for a logged in and connected facebook account
		// user = getCurrentUserFacebookAccount(req);
		// if (user != null)
		// return user;

		return null;
	}

	public static UserModel getCurrentUserLocalAccount(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session == null) {
			return null;
		}

		Integer uid = (Integer) session
				.getAttribute(LoginController.LOGGED_IN_SESSION_ATTRIBUTE);

		if (uid == null) {
			return null;
		}

		UserModel user = UserModel.getFromDbByID(uid);

		return user;
	}
}
