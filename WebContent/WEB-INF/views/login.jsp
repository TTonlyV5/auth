<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<form action="main" method="post"><!-- springMVC的Controller的@RequestMapping中的值 跳转到main-->
		<fieldset>
		<legend>创建用户</legend>
			<p>
				<label>用户名：</label> <input type="text" id="name" name="name" tabindex="1">
			</p>
			<p>
				<label>密码：</label> <input type="text" id="password" name="password" tabindex="3">
			</p>
			<p id="buttons">
			 <input id="submit" type="submit" tabindex="5" value="登陆">
			</p>
		</fieldset>
	</form>
</body>
</html>