package com.epam.controller.util;

/**
 * Constants class. Holds only values for often used routes inside the application.
 */
public class Routes {
    public static final String APP_PATH = "/epam";
    public static final String LOGIN = APP_PATH + "/login";
    public static final String SLASH_LOGIN = "/login";
    public static final String SLASH_LOCALE_EDIT = "/locale/edit";
    public static final String REGISTER = "register";
    public static final String SLASH_REGISTER = "/register";
    public static final String LOGOUT = "logout";
    public static final String SLASH_LOGOUT = "/logout";
    public static final String HOME_TESTS = "tests?page=1&sort=name ASC&subject=0&perPage=10";
    public static final String SLASH_HOME_TESTS = APP_PATH + "/tests?page=1&sort=name ASC&subject=0&perPage=10";
    public static final String PASSED_TESTS = APP_PATH + "/passed/tests?page=1&sort=date DESC&perPage=10";
}
