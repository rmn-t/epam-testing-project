package com.epam.controller.user;

import com.epam.controller.util.Views;
import junit.framework.TestCase;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class RegisterServletTest {
    String path = Views.REGISTER_JSP;
    final RegisterServlet registerServlet = new RegisterServlet();

    @Test
    public void EnsureRegisterServletDoGetSendsForwardToRegisterJSP() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        registerServlet.doGet(req,resp);
        verify(req,times(1)).getRequestDispatcher(path);
        verify(requestDispatcher).forward(req,resp);
    }
}