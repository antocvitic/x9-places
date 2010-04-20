package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.x9.foodle.util.CookieUtils;

@SuppressWarnings("serial")
public class LogoutController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String redirect = req.getParameter("redirect");
		HttpSession session = req.getSession(false);

		if (session != null) {
			String SESSUSERID = session.getAttribute(LoginController.LOGGED_IN_SESSION_USERID).toString();
			String SESSTOKEN = session.getAttribute(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN).toString();
			
			session.removeAttribute(LoginController.LOGGED_IN_SESSION_USERID);
			session.removeAttribute(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN);
			session.invalidate();
			
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for(int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if ((c.getName().equals(SESSUSERID) && c.getValue().equals(SESSTOKEN))) {
						c.setMaxAge(0);
						resp.addCookie(c);
					}
				}
	        }
		}

		if (redirect == null) {
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
		} else {
			resp.sendRedirect(redirect);
		}
	}
}
