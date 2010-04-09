package com.x9.foodle.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * Returns a MD5 hash of {@code plaintext} as a 32 character long string.
	 * 
	 * Taken from: http://snippets.dzone.com/posts/show/3686
	 * 
	 * @param plaintext
	 *            the string to be hashed
	 * @return the MD5 hash of {@code plaintext}
	 */
	public static String getMd5Hash(String plaintext) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		byte[] data = plaintext.getBytes();
		m.update(data, 0, data.length);
		BigInteger i = new BigInteger(1, m.digest());
		return String.format("%1$032X", i);
	}
}
