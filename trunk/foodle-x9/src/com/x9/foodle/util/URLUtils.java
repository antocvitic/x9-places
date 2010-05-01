package com.x9.foodle.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class URLUtils {

	public static String getCurrentURL(HttpServletRequest request) {
		String qs = request.getQueryString();
		qs = qs == null ? "" : qs;
		return request.getRequestURL() + "?" + qs;
	}

	/**
	 * Returns {@code s} as a URL encoded string, using UTF-8 as the character
	 * set.
	 * 
	 * @param s
	 *            the string to be encoded
	 * @return the encoded string
	 * @see URLEncoder#encode(String, String)
	 */
	public static String encode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"UTF-8 is not supported by the URLEncoder!", e);
		}
	}

	public static String encodeLatin(String s) {
		try {
			return URLEncoder.encode(s, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"UTF-8 is not supported by the URLEncoder!", e);
		}
	}

	/**
	 * Returns the URL encoded {@code s} as a regular UTF-8 string.
	 * 
	 * @param s
	 *            the string to be decoded
	 * @return the decoded string
	 * @see URLDecoder#decode(String, String)
	 */
	public static String decode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"UTF-8 is not supported by the URLDecoder!", e);
		}
	}

	public static String decodeLatin(String s) {
		try {
			return URLDecoder.decode(s, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"ISO-8859-1 is not supported by the URLDecoder!", e);
		}
	}

}
