package com.epam.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class GetCurrentUrlTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String currentURL = null;
        if( request.getAttribute("javax.servlet.forward.request_uri") != null ){
            currentURL = (String)request.getAttribute("javax.servlet.forward.request_uri");
        }
        if( currentURL != null && request.getAttribute("javax.servlet.forward.query_string") != null ){
            currentURL += "?" + request.getAttribute("javax.servlet.forward.query_string");
        }
        JspWriter out = pageContext.getOut();
        out.print(currentURL);
    }
}
