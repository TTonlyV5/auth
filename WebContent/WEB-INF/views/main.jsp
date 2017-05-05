<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
</head>
<body>
	<div id="gloobal">
		<h4>登陆成功</h4>
		<p>
		<h5>详情：</h5>
		用户名：${user.name}<br /> 密码：${user.password}<br />
		</p>
	</div>
	<div>
		<li>安全权限</li>
		<li><a href="user/index">用户管理</a></li>
		<!-- 让control拦截跳转到user_list -->
		<li><a href="role/index">角色管理</a></li>
		<!-- 让control拦截跳转到role_list -->
		<li><a href="functions/index">菜单管理</a></li>
		<!-- 让control拦截跳转到function_list -->
		<li><a href="authorize/index">用户授权</a></li>
		<!-- 让control拦截跳转到authorize/authorize_list -->
		<li><a href="/">用户角色</a></li>
		<!-- 让control拦截跳转到user_role_list -->
	</div>
</body>
</html>