package com.epam.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MyTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
        getJspContext().getOut().print(request.getRequestURI() + "\n"
                + request.getRequestURL() + "\n"
                + request.getContextPath() + "\n"
                + request.getPathInfo() + "\n"
                + request.getPathTranslated() + "\n"
                + request.getServletPath()


                + request.getQueryString());
        /**
         *             response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
         *             response.setHeader("Pragma","no-cache"); // HTTP 1.0
         *             response.setHeader("Expires","0"); // if using a proxy server
         */

    }
}
