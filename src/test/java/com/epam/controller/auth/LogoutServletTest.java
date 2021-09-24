package com.epam.controller.auth;

import com.epam.controller.util.Routes;
import com.epam.db.model.User;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse resp = mock(HttpServletResponse.class);
    LogoutServlet logoutServlet = new LogoutServlet();

    @Test
    public void abc() throws ServletException, IOException {
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        session.setAttribute("currentUser",new User.Builder().setUsername("admin"));
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("currentUser")).thenReturn(new User.Builder().setUsername("admin"));
//        when(req.getRequestDispatcher(Routes.LOGOUT)).thenReturn(requestDispatcher);
        logoutServlet.doGet(req,resp);
        verify(req,times(1)).getRequestDispatcher(Routes.LOGOUT);
        verify(requestDispatcher).forward(req,resp);
    }


}