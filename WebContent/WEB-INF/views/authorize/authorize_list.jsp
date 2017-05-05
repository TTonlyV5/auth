<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授权列表</title>
</head>
<body>
	<div>
		<li><a href="authorize/userRole">用户角色首页</a></li>
		<!-- 让control拦截跳转到user_role_list -->
		<li><a href="authorize/getAuthorizes">删除角色</a></li>
		<!-- 让control拦截跳转到getAuthorizes -->
		<li><a href="authorize/getUserRoleBuUserId">根据用户id查询用户和角色的对应关系</a></li>
		<!-- 让control拦截跳转到getUserRoleBuUserId -->
		<li><a href="authorize/getAuthorizes">查询授权</a></li>
		<!-- 让control拦截跳转到getAuthorizes -->
		<li><a href="authorize/setAuthorize">设置授权</a></li>
		<!-- 让control拦截跳转到setAuthorize -->
	</div>
</body>
</html>