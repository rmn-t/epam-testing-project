package com.epam.controller.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Utility class, which sets max inactive time for the session.
 */
public class SessionActivitySetter implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(60 * 60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //
    }
}
