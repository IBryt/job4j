<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:set var="user" value="${user}"></c:set>
    <form action="${pageContext.servletContext.contextPath}/edit?id=<c:out value="${user.id}"></c:out>" method='post'>
    <input type='hidden' name='action' value='update'><br>
    id    :<input type='text' name='id' value=<c:out value="${user.id}"></c:out> disabled><br>
    name  :<input type='text' name='name' value=<c:out value="${user.name}"></c:out>><br>
    login :<input type='text' name='login' value=<c:out value="${user.login}"></c:out>><br>
    email :<input type='text' name='email' value=<c:out value="${user.email}"></c:out>><br>
    date  :<input type='text' name='createDate' value=<c:out value="${user.createDate}"></c:out> disabled><br>
    role  :<select name="role">
        <c:forEach items="${roles}" var="role">
            <option value=<c:out value="${role.id}"></c:out>><c:out value="${role.name}"></c:out></option>
        </c:forEach>
    </select><br>
    <input type='submit' value='submit'>
    </form>
</body>
</html>
