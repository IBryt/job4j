<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/create"  method='post'>
    <input type='hidden' name='action' value='add'><br>
    name :<input type='text' name='name' value=''><br>
    login:<input type='text' name='login' value=''><br>
    email:<input type='text' name='email' value=''><br>
    role  :<select name="role">
        <c:forEach items="${roles}" var="role">
            <option value=<c:out value="${role.id}"></c:out>><c:out value="${role.name}"></c:out></option>
        </c:forEach>
    </select><br>
    <input type='submit' value='submit'>
</form>
</body>
</html>
