<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑用户</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
        }
        .container { 
            width: 450px; 
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 25px;
        }
        .form-group { 
            margin-bottom: 20px; 
        }
        label { 
            display: block; 
            margin-bottom: 8px;
            font-weight: 500;
            color: #555;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            transition: border-color 0.3s;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #2196F3;
            outline: none;
            box-shadow: 0 0 0 2px rgba(33,150,243,0.2);
        }
        .submit-btn {
            background: #2196F3;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .submit-btn:hover {
            background: #0d8bf2;
        }
        a {
            color: #2196F3;
            text-decoration: none;
            transition: color 0.3s;
        }
        a:hover {
            color: #0d8bf2;
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>编辑用户</h2>
    <c:if test="${not empty error}">
        <div style="color: red; margin-bottom: 15px;">
            ${error}
        </div>
    </c:if>
    <form action="${pageContext.request.contextPath}/update" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="originalUsername" value="<%= request.getParameter("username") %>">
        
        <div class="form-group">
            <label>用户名:</label>
            <input type="text" name="username" value="${param.username}" required>
        </div>
        
        <div class="form-group">
            <label>密码:</label>
            <input type="password" name="password" value="${param.password}" required>
        </div>
        
        <input type="submit" class="submit-btn" value="更新">
        <a href="<%= request.getContextPath() %>/list" style="margin-left:20px">返回列表</a>
    </form>
</div>
</body>
</html>