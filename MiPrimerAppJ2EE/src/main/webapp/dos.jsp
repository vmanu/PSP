<%-- 
    Document   : dos
    Created on : 11-dic-2015, 10:23:36
    Author     : dam2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 style="background: blue">Hello World! 2</h1>
        <%
            response.getWriter().println(request.getSession().getAttribute("numero"));
            
            %>
    </body>
</html>
