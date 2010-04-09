package com.x9.foodle.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class QuickURLEncoder {
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

	/**
	 * Returns the URL encoded {@code s} as a regular UTF-8 string.s
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
}
