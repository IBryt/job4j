<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="1">
    <br>
    <a href="${pageContext.servletContext.contextPath}/edit">add</a>
    <br>
    <a href="${pageContext.servletContext.contextPath}/signin">signin</a>
    <br>
    <c:if test="${error != ''}">
        <div style="background: red">
            <c:out value="${error}"></c:out>
        </div>
    </c:if>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>login</td>
        <td>email</td>
        <td>createDate</td>
    </tr>
    <c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.value.id}"></c:out></td>
        <td><c:out value="${user.value.name}"></c:out></td>
        <td><c:out value="${user.value.login}"></c:out></td>
        <td><c:out value="${user.value.email}"></c:out></td>
        <td><c:out value="${user.value.createDate}"></c:out></td>

        <td>
            <form action="${pageContext.servletContext.contextPath}/edit?id=" <c:out value="${user.value.id}"></c:out> method='get'>
                <input type='hidden' name='id' value=<c:out value="${user.value.id}"></c:out>>
                <input type='submit' value='edit'>
            </form>
        </td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/" method='post'>
                <input type='hidden' name='id' value=<c:out value="${user.value.id}"></c:out>>
                <input type='hidden' name='action' value='delete'>
                <input type='submit' value='delete'>
            </form>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
