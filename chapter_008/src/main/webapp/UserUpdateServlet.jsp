<%@ page import="ru.job4j.logic.ValidateService" %>
<%@ page import="ru.job4j.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% User user = ValidateService.getInstance().findById(Integer.parseInt(request.getParameter("id"))); %>
    <form action="<%=request.getContextPath()%>/edit?id=<%=user.getId()%>" method='post'>
    <input type='hidden' name='action' value='update'><br>
    id    :<input type='text' name='id' value="<%=user.getId()%>" disabled><br>
    name  :<input type='text' name='name' value="<%=user.getName()%>"><br>
    login :<input type='text' name='login' value="<%=user.getLogin()%>"><br>
    email :<input type='text' name='email' value="<%=user.getEmail()%>"><br>
    date  :<input type='text' name='email' value="<%=user.getCreateDate().toString()%>" disabled><br>
    <input type='submit' value='submit'>
    </form>
</body>
</html>
