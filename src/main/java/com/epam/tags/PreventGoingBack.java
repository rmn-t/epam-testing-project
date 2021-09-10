package com.epam.tags;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PreventGoingBack extends SimpleTagSupport {

    @Override
    public void doTag() {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
        resp.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
        resp.setHeader("Pragma","no-cache"); // HTTP 1.0
        resp.setHeader("Expires","0"); // if using a proxy server
    }
}
