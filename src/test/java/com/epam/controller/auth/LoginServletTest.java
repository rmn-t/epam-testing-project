package com.epam.controller.auth;

import com.epam.controller.util.Routes;
import com.epam.controller.util.Views;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServletTest {
    String path = Views.LOGIN_JSP;
    String home_path = Routes.HOME_TESTS;
    final LoginServlet loginServlet = new LoginServlet();

    @Test
    public void EnsureLoginServletDoGetSendsForwardToLoginJSP() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        loginServlet.doGet(req,resp);
        verify(req,times(1)).getRequestDispatcher(path);
        verify(requestDispatcher).forward(req,resp);
    }

//    @Test
//    public void testLoginPost() throws IOException, ServletException {
//        Map<String, String> parameters = new HashMap<String, String>();
//        HttpServletRequest req = mock(HttpServletRequest.class);
//        HttpServletResponse resp = mock(HttpServletResponse.class);
//        parameters.put("username","amy");
//        parameters.put("password","amy");
//        when(req.getParameter(anyString())).thenAnswer((Answer<String>) invocation -> parameters.get((String) invocation.getArguments()[0]));
//        loginServlet.doPost(req,resp);
//
//        verify(resp,times(1)).sendRedirect(home_path);
//    }

    


}
