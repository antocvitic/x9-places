package com.x9.foodle.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageDispatcher {

	public static final String MSG_KEY = "msg_dispatcher_msg";

	public static void pushMsg(HttpServletRequest req, Message msg) {
		HttpSession session = req.getSession();
		session.setAttribute(MSG_KEY, msg);
	}

	public static Message popMsg(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null)
			return null;
		Object oMsg = session.getAttribute(MSG_KEY);
		session.removeAttribute(MSG_KEY);
		if (oMsg == null)
			return null;
		return (Message) oMsg;
	}

	public static void sendMsgRedirect(HttpServletRequest req,
			HttpServletResponse resp, String url, Message msg)
			throws IOException {
		sendMsgRedirectAbsolute(req, resp, req.getContextPath() + url, msg);
	}

	public static void sendMsgRedirectAbsolute(HttpServletRequest req,
			HttpServletResponse resp, String url, Message msg)
			throws IOException {
		pushMsg(req, msg);
		resp.sendRedirect(url);
	}

	public abstract static class Message {
		private String status;
		private String text;

		public Message(String status, String text) {
			super();
			this.status = status;
			this.text = text;
		}

		public String toJSON() {
			JSONObject json = new JSONObject();
			try {
				json.put("status", status);
				json.put("text", text);
				return json.toString();
			} catch (JSONException e) {
				throw new RuntimeException(
						"error generating json for Message: " + toString(), e);
			}
		}

		@Override
		public String toString() {
			return "Message [status=" + status + ", message=" + text + "]";
		}
	}

	public static class ErrorMessage extends Message {
		public ErrorMessage(String text) {
			super("error", text);
		}
	}

	public static class OkMessage extends Message {
		public OkMessage(String text) {
			super("ok", text);
		}
	}
}
