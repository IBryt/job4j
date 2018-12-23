<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/create"  method='post'>
    <input type='hidden' name='action' value='add'><br>
    name :<input type='text' name='name' value=''><br>
    login:<input type='text' name='login' value=''><br>
    email:<input type='text' name='email' value=''><br>
    <input type='submit' value='submit'>
</form>
</body>
</html>
