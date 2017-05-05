<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户管理</title>
</head>
<body>
	<div>
		<li><a href="user/addEditUser">增加用户</a></li>
		<!-- 让control拦截跳转到/addEditUser -->
		<li><a href="user/deleteUser">删除用户</a></li>
		<!-- 让control拦截跳转到deleteUser -->
		<li><a href="user/getUsers">分页查询用户</a></li>
		<!-- 让control拦截跳转到getUsers -->
	</div>
</body>
</html>