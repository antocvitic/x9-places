package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.x9.foodle.util.MD5Utils;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {

	public static String LOGGED_IN_SESSION_ATTRIBUTE = "logged_in_user";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String redirect = req.getParameter("redirect");
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// TODO: how do we use the jBCrypt library?
		String passwordHash = MD5Utils.getMd5Hash(password);

		UserModel user = UserModel.getFromDbByLogin(username, passwordHash);

		if (user == null) {
			resp.sendRedirect("login_page.jsp?error=no_such_user");
		}

		HttpSession session = req.getSession();

		// TODO: make it more secure
		session.setAttribute(LOGGED_IN_SESSION_ATTRIBUTE, new Integer(user
				.getUserID()));

		resp.sendRedirect(redirect);
	}

}
