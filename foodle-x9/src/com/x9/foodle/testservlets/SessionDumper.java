package com.x9.foodle.testservlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class SessionDumper extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
		resp.setDateHeader("Expires", 0); // prevents caching at the proxy
											// server

		ServletOutputStream out = resp.getOutputStream();
		HttpSession session = req.getSession(false);

		if (session != null) {
			@SuppressWarnings("unchecked")
			Enumeration<String> attribs = session.getAttributeNames();
			while (attribs.hasMoreElements()) {
				String attrib = attribs.nextElement();
				out.println(attrib + " : " + session.getAttribute(attrib));
				out.println("<br/>");
			}
		} else {
			out.println("No session");
		}
	}

}
