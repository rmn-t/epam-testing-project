package com.epam;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    /**
     * example
     * @param req
     * @param resp
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String msg = "JDBC Pool connection successfully created";
        Connection con = null;
        try {
            String dataSourceName = getServletContext().getInitParameter("DataSource");
            Context context = (Context) new InitialContext().lookup("java:/comp/env");
            System.out.println(dataSourceName);
            DataSource dataSource = (DataSource) context.lookup("jdbc/mysql");
//            System.out.println(dataSource.getClass().getName());
            con = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
            msg = e.getMessage();
        } catch (SQLException e) {
            msg = e.getMessage();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // do nothing
                }
            }
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Hello World</h1>");
        out.write("<hr/>");
        out.write("<p>" + msg + "</p>");
        out.close();
    }
}
