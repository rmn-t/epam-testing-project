package com.epam.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class CookieUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    public static final String[] VALID_COOKIE_LOCALES = {"en","ru"};
    public static final String LANG_COOKIE_NAME = "lang";
    public static final String DEFAULT_LOCALE = "en";

    public static String getLangFromCookies(Cookie[] cookies) {
        Cookie cookie;
        for (Cookie value : cookies) {
            cookie = value;
            if (LANG_COOKIE_NAME.equals(cookie.getName())) {
                logger.debug(cookie.getValue());
                if (Arrays.asList(VALID_COOKIE_LOCALES).contains(cookie.getValue())) {
                    return (cookie.getValue());
                } else {
                    return DEFAULT_LOCALE;
                }
            }
        }
        return DEFAULT_LOCALE;
    }

    public static String getCookieValueByName(Cookie[] cookies,String cookieName) {
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
