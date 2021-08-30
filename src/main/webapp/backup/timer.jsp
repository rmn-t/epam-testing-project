<%@ page
    language ="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays,java.util.List,com.epam.db.entities.Test"

%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    </head>
        <script>
            <%
                String clock = request.getParameter( "clock" );
                String abc = "" + request.getAttribute("parameter1");
                if( clock == null ) clock = "180";
                String pepa = (String) session.getAttribute("username");

            %>
            var timeout = <%=clock%>;
            var myName= '<%= session.getAttribute("username")%>';
            console.log(myName);
            var reqabc = '<%=request.getAttribute("parameter1")%>';
            let timeLeft = '<%= session.getAttribute("timeLeft" + request.getParameter("id"))%>'
            console.log(reqabc);
            function timer() {
                if( --timeout > 0 ) {
                    document.forma.clock.value = timeout;
                    window.setTimeout( "timer()", 1000 );
                } else {
                    document.forma.clock.value = "Time over";
                    ///disable submit-button etc
                }
            }
        </script>
    <body>
        <form action="#" name="forma">
            Seconds remaining: <input type="text" name="clock" value="<%=clock%>" style="border:0px solid white">
            </form>
        <script>
            timer();
        </script>
    </body>
</html>