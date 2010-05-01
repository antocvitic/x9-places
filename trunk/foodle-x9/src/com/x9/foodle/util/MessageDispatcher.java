package com.x9.foodle.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MessageDispatcher {

	public static final String ERROR_MSG_KEY = "error_msg";

	// TODO: don't send raw strings, which cannot be i18nized, but status codes
	// or something

	public static void pushMsg(HttpServletRequest req, String errorMsg) {
		HttpSession session = req.getSession();
		session.setAttribute(ERROR_MSG_KEY, errorMsg);
	}

	public static String popMsg(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null)
			return null;
		Object oMsg = session.getAttribute(ERROR_MSG_KEY);
		session.removeAttribute(ERROR_MSG_KEY);
		if (oMsg == null)
			return null;
		return (String) oMsg;
	}

	public static void sendMsgRedirect(HttpServletRequest req,
			HttpServletResponse resp, String url, String msg)
			throws IOException {
		sendMsgRedirectAbsolute(req, resp, req.getContextPath() + url, msg);
	}

	public static void sendMsgRedirectAbsolute(HttpServletRequest req,
			HttpServletResponse resp, String url, String msg)
			throws IOException {
		pushMsg(req, msg);
		resp.sendRedirect(url);
	}
}
