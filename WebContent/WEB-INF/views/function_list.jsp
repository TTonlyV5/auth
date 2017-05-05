<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能列表</title>
</head>
<body>
	<div>
		<li><a href="functions/addFunction">增加功能</a></li>
		<!-- 让control拦截跳转到addFunction -->
		<li><a href="functions/deleteFunctionsById">根据id删除功能</a></li>
		<!-- 让control拦截跳转到deleteFunctionsById -->
		<li><a href="functions/findAllFunctions">查询全部功能</a></li>
		<!-- 让control拦截跳转到findAllFunctions -->
		<li><a href="functions/getSubFunctions">得到子菜单功能集合</a></li>
		<!-- 让control拦截跳转到getSubFunctions -->
		<li><a href="functions/buildMenuTreeForEdit">构建用于新建、修改使用的菜单（功能）树</a></li>
		<!-- 让control拦截跳转到buildMenuTreeForEdit -->
	</div>
</body>
</html>