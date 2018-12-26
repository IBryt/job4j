<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background: red">
        <c:out value="${error}"></c:out>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/signin"  method='post'>
    <input type='hidden' name='action' value='add'><br>
    login:<input type='text' name='login' value=''><br>
    password:<input type='password' name='password' value=''><br>
    <input type='submit' value='submit'>
</form>
</body>
</html>
