<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户登录</title>
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
            width: 100%;
            font-size: 16px;
            transition: background 0.3s;
        }
        .submit-btn:hover {
            background: #1976D2;
        }
        .error {
            color: #d32f2f;
            background: #ffebee;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
        }
        .login-link a {
            color: #2196F3;
            text-decoration: none;
        }
        body {
            background: url(https://cdn.luogu.com.cn/upload/image_hosting/cd3iwgqt.png) no-repeat center center;
            background-size: cover;
            background-attachment: fixed;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>用户登录</h2>
    <c:if test="${not empty error}">
        <div class="error">
            ${error}
        </div>
    </c:if>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label>用户名:</label>
            <input type="text" name="username" required>
        </div>
        
        <div class="form-group">
            <label>密码:</label>
            <input type="password" name="password" required>
        </div>
        
        <button type="submit" class="submit-btn">立即登录</button>
        <div class="login-link">
            没有账号？<a href="register.jsp">立即注册</a>
        </div>
    </form>
</div>
</body>
</html>