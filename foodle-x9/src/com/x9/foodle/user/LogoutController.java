package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LogoutController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String redirect = req.getParameter("redirect");
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.removeAttribute(LoginController.LOGGED_IN_SESSION_USERID);
			session
					.removeAttribute(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN);
		}

		// TODO: remove cookie

		resp.sendRedirect(redirect);
	}

}
