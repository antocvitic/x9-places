package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {

	public static String LOGGED_IN_SESSION_USERID = "logged_in_userid";
	public static String LOGGED_IN_SESSION_SESSION_TOKEN = "logged_in_session_token";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String redirect = req.getParameter("redirect");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String rememberme = req.getParameter("rememberme");

		UserModel user = null;

		if (username == null
				|| (user = UserModel.getFromDbByUsername(username)) == null) {
			resp.sendRedirect("login.jsp?error=no_such_user:" + username);
			return;
		}

		if (password == null
				|| !BCrypt.checkpw(password, user.getPasswordHash())) {
			resp.sendRedirect("login.jsp?error=bad_pwd:" + password);
			return;
		}

		doPostLoginStuff(user, req, resp, rememberme != null);

		if (redirect == null) {
			resp.sendRedirect(req.getContextPath() + "/profile.jsp");
		} else {
			resp.sendRedirect(redirect);
		}
	}

	public static void doPostLoginStuff(UserModel user, HttpServletRequest req,
			HttpServletResponse resp, boolean rememberme) {
		HttpSession session = req.getSession();

		session.setAttribute(LOGGED_IN_SESSION_USERID, new Integer(user
				.getUserID()));
		session.setAttribute(LOGGED_IN_SESSION_SESSION_TOKEN, user
				.getSessionToken());

		if (rememberme) {
			Cookie cuid = new Cookie(LOGGED_IN_SESSION_USERID, Integer
					.toString(user.getUserID()));
			cuid.setMaxAge(7 * 24 * 60 * 60); // 7 days
			resp.addCookie(cuid);

			Cookie ctoken = new Cookie(LOGGED_IN_SESSION_SESSION_TOKEN, user
					.getSessionToken());
			ctoken.setMaxAge(7 * 24 * 60 * 60);
			resp.addCookie(ctoken);
			// c.setSecure(true);
		}
	}

}
