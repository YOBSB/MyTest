<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=path%>/initializeController/initial.do" method="post">
		<input type="hidden" name="super_admin_username_id" /><br>
		<input type="text" name="super_admin_username" placeholder="超级管理员登录名"  /><br>
		<input type="text" name="super_admin_realname" placeholder="超级管理员真实姓名" /><br>
		<input type="text" name="super_admin_password" placeholder="超级管理员密码" /><br>
		<input type="text" name="super_admin_keychain" placeholder="密钥串" /><br>
		<input type="submit" value="确认初始化"/><br>
	</form>
</body>


