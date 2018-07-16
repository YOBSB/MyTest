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

<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/libs/ueditor1_4_3/ueditor.config.js"></script> 
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/libs/ueditor1_4_3/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/libs/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/libs/ueditor1_4_3/themes/default/css/ueditor.css">  
<title>index页面</title>
</head>
<body>
	Index页面
</body>
</html>