package com.epam.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;

/**
 * Utility class that helps finding the cookies by cookie name.
 */
public class CookieUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    /**
     * Gets the value of the cookie if it exists or returns default value.
     *
     * @param cookies    array of cookies from request
     * @param cookieName the key representing cookie name
     * @return if cookie found by name - return it's value, otherwise returns empty string ""
     */
    public static String getCookieValueByName(Cookie[] cookies, String cookieName) {
        String defaultValue = "";
        if (cookies == null) {
            return defaultValue;
        }
        Cookie cookie;
        for (Cookie value : cookies) {
            cookie = value;
            if (cookieName.equals(cookie.getName())) {
                logger.debug(cookie.getValue());
                return (cookie.getValue());
            }
        }
        return defaultValue;
    }
}
