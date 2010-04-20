package com.x9.foodle.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.x9.foodle.user.LoginController;

/*
 * Return the sessionuserid and sessiontoken from a request if found as String
 * 
 * @param HttpServletRequest req
 *            The supplied request which may contain the cookie.
 * @return The sessionuserid and sessiontoken from the cookie if found otherwise null.
 */
public class CookieUtils {
	public static String cUserValue = "<your username>";
	
	public static String getUserid(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
        String UserValue = null;
        
        for(int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals(LoginController.LOGGED_IN_SESSION_USERID)) {
                UserValue = c.getValue();
                cUserValue = UserValue;
            }
        }
		return UserValue;
	}
	public static String getSessionToken(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
        String cSessionValue = null;
        
        for(int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals(LoginController.LOGGED_IN_SESSION_SESSION_TOKEN)) {
            	cSessionValue = c.getValue();
            }
        }
		return cSessionValue;
	}
}
