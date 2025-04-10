<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>教师管理</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
        }
        .container {
            width: 800px;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        th {
            background-color: #2196F3;
            color: white;
            padding: 12px;
            text-align: left;
        }
        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        tr:hover {
            background-color: #f5f5f5;
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
        .action-btn {
            background: #2196F3;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
            margin-right: 5px;
        }
        .action-btn:hover {
            background: #0d8bf2;
        }
        .pagination {
            margin-top: 30px;
            text-align: center;
        }
        .pagination a {
            padding: 8px 15px;
            margin: 0 4px;
            background: #f0f0f0;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .pagination a:hover {
            background: #2196F3;
            color: white;
        }
        .navbar {
            display: flex;
            gap: 20px;
            margin-bottom: 25px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 6px;
        }
        .nav-link {
            padding: 8px 15px;
            border-radius: 4px;
            color: #2196F3;
            transition: all 0.3s;
        }
        .nav-link:hover {
            background: #2196F3;
            color: white;
            text-decoration: none;
        }
        .nav-link.active {
            background: #2196F3;
            color: white;
        }
    </style>
</head>
<body>
<div class="navbar">
    <a href="<%= request.getContextPath() %>/login.jsp" class="nav-link active">山河大学计算机系师生后台管理系统</a>
    <a href="<%= request.getContextPath() %>/list" class="nav-link">用户管理</a>
    <a href="<%= request.getContextPath() %>/liststudent" class="nav-link">学生管理</a>
    <a href="<%= request.getContextPath() %>/listteacher" class="nav-link">教师管理</a>
</div>
<div class="container">
    <h2>教师列表</h2>
    <a href="${pageContext.request.contextPath}/addteacher.jsp" class="action-btn">添加教师</a>

    <table>
        <tr>
            <th>姓名</th>
            <th>教授科目</th>
            <th>电话</th>

            <th>操作</th>
        </tr>
        <c:forEach items="${teachers}" var="teacher">
            <tr>
                <td>${teacher.name}</td>
                <td>${teacher.phone}</td>
                <td>${teacher.subject}</td>


                <td>
                    <a href="${pageContext.request.contextPath}/deleteteacher?name=${teacher.name}"
                       onclick="return confirm('确定要删除吗？')" class="action-btn">删除</a>
                    <a href="${pageContext.request.contextPath}/updateteacher?name=${teacher.name}&subject=${teacher.subject}&phone=${teacher.phone}}" class="action-btn">编辑</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty teachers}">
            <tr><td colspan="3">暂无教师数据</td></tr>
        </c:if>
    </table>

    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <a href="${pageContext.request.contextPath}/listteacher?page=${i}">${i}</a>
        </c:forEach>
    </div>
</div>
</body>
</html>