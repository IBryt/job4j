<%@ page import="ru.job4j.model.User" %>
<%@ page import="ru.job4j.logic.ValidateService" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="1">
    <br>
    <a href="UserCreateServlet.jsp">Create</a>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>login</td>
        <td>email</td>
        <td>createDate</td>
    </tr>
    <% for (Map.Entry<Integer, User> entry : ValidateService.getInstance().findAll().entrySet()) {%>
    <tr>
        <td><%=entry.getValue().getId()%></td>
        <td><%=entry.getValue().getName()%></td>
        <td><%=entry.getValue().getLogin()%></td>
        <td><%=entry.getValue().getEmail()%></td>
        <td><%=entry.getValue().getCreateDate()%></td>
        <td>
            <form action="<%=request.getContextPath()%>/UserUpdateServlet.jsp?id="<%=entry.getValue().getId()%>method='get'>
                <input type='hidden' name='id' value=<%=entry.getValue().getId()%>>
                <input type='submit' value='edit'>
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/list" method='post'>
                <input type='hidden' name='id' value=<%=entry.getValue().getId()%>>
                <input type='hidden' name='action' value='delete'>
                <input type='submit' value='delete'>
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
