<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

    <div class="login-wrap">
      <h1>建造大赛管理后台</h1>
      <form action="<%=path%>admin/login" method="post" id="loginForm">
        <div class="form-item ui-input">
          <input type="text" name="adminLoginName" id="adminLoginName" placeholder="管理员登录名" required autocomplete="off">
        </div>
        <div class="form-item ui-input">
          <input type="password" name="adminPassword" id="adminPassword" class="ui-input" placeholder="管理员密码" required autocomplete="off">
        </div>
        <div class="form-item">
          <input type="submit" value="登录" class="ui-button ui-button-primary login-submit" role="button">
        </div>
      </form>
      <div class="message">${result}</div>
    </div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

  <script>
    new Validate($('#loginForm'), function () {
      $.ajax({
    	  url: '<%=path%>/admin/login',
    	  type: 'POST',
    	  data: {
    		  adminLoginName: $('#adminLoginName').val(),
    		  adminPassword: $('#adminPassword').val()
    	  }
      }).done(function(res){
    	  if(!res.code){
    		  if(res.data.adminRoleId==1){
    			  location.href ='<%=path%>/season/showSeasonView.do'
    		  }else{
	    		  location.href ='<%=path%>/formReview'
    		  }
    	  } else {
    		  alert(res.msg);
    	  }
      }).fail(function(err){
    	  alert(err);
      });
    }, {
      validate: [{
          id: 'adminLoginName',
          prompt: {
            ignore: '请输入管理员登录名'
          }
        },
        {
          id: 'adminPassword',
          prompt: {
            ignore: '请输入管理员密码'
          }
        }
      ]
    })
  </script>
</body>

</html>