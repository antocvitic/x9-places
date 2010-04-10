package com.x9.foodle.testservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.user.UserModel;
import com.x9.foodle.user.UserUtils;

@SuppressWarnings("serial")
public class UserDumper extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("uname");
		ServletOutputStream out = resp.getOutputStream();
		
		UserModel user = null;
		if (username == null) {
			user = UserUtils.getCurrentUser(req);
			if (user != null)
				username = user.getUsername();
		} else {
			user = UserModel.getFromDbByUsername(username);
		}
		
		if (user == null) {
			out.println("No user with username: " + username);
		} else {
			out.println("User: " + user);
		}
		
		
		out.println("<br/>");
		out.println("<br/>");
		
		out.println("<form action=\"dump-user\" method=\"GET\">");
		out.println("<input name=\"uname\" type=\"text\" value=\"" + username + "\"/>");
		out.println("<input type=\"submit\" value=\"Dump user\"/>");
		out.println("</form>");
	}

}
