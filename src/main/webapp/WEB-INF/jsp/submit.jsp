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
<script src="${pageContext.request.contextPath}/resources/jquery-1.11.3.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>筑梦师建筑大赛</title>
</head>
<body>

<form action="<%=path%>/submit/submitWorks" method="post">
		迷你号:<input type="text" name="mini_id" id="mini_id" />
      <br>
      	姓名:<input type="text" name="name" id="name"/>
      <br>
      	QQ号:<input type="text" name="qq" id="qq"/>
      <br>
      	邮箱:<input type="text" name="mail" id="mail"/>
      <br>
      	联系方式:<input type="text" name="phone" id="phone"/>
      <br>
      	团队成员:<input type="text" name="team_mates" id="team_mates"/>
      <br>
      	选择作品:<select name="works_name">
				<option value="volvo">Volvo</option>
				<option value="saab">Saab</option>
				<option value="fiat" selected>Fiat</option>
				<option value="audi">Audi</option>
				</select>
      <br>
      	地图id:<input type="text" name="works_map_id" id="works_map_id"/>
      <br>
      	作品介绍:<input type="text" name="works_introduce" id="works_introduce"/>
      <br>
      	作品主图:<input type="text" name="works_main_image" id="works_image"/><br>
      	作品主图片缩略图：<input type="text" name="main_small_image" id="main_small_image"/><br>
      	
      <br>
      			作品截图1：<input type="text" name="image_1" id="image_1"/><br>
      			作品缩略图1：<input type="text" name="image_small_1" id="image_small_1"/><br>
      			作品截图2：<input type="text" name="image_2" id="image_2"/><br>
      			作品缩略图2：<input type="text" name="image_small_2" id="image_small_2"/><br>
      			作品截图3：<input type="text" name="image_3" id="image_3"/><br>
      			auth：<input type="text" name="auth" id="auth"/><br>
      			time：<input type="text" name="time" id="time"/><br>
      			sign：<input type="text" name="sign" id="sign"/><br>
      			s2t：<input type="text" name="s2t" id="s2t"/><br>
      
      <input type="submit" value="确定投稿"/>
    </form>
	<img alt="" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530843208&di=b9740ff327e632703d9433bd8e31144b&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.antik-group.com%2FWebRoot%2FStore2%2FShops%2Fes122041_REPLIKATE_DER_ANTIKE%2F4D0C%2FC869%2F1F46%2F93DE%2FDADE%2F50ED%2F8960%2F32BB%2FANKH_925_m.jpg">
    test<button id="test" name="test"><%=path%>/</button>
    <script type="text/javascript"> 
	$('#test').click(function(){
		$.ajax({
		            url:"test/getUser",
		            type:"get",
		            datatypt:"json",
		            data:{"userId":1,"table":"new_test_user"},
		            success:function(data){
		            	var str=data['user'];
		              alert(str.name);
		            }
		});
	})
</script>
</body>
</html>