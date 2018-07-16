<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<header class="site-header manage-header">
    <h1 class="logo">建造大赛管理后台</h1>
    <ul class="site-nav">
        <li class="active"><a href="<%=path%>/admin/home">用户管理</a></li>
        <li><a href="<%=path%>/season/showSeasonView.do">赛季管理</a></li>
    </ul>
    <div class="logout-wrap"><a href="<%=path%>/admin/logout">退出登录</a></div>
</header>

<div class="main main-manage">
    <div class="manage-sec-create">
        <form action="<%=path%>/admin/register" method="post" id="createUserForm">
            <h2>创建新用户</h2>
            <div class="form-item">
                <label for="adminLoginName">登录名</label>
                <input type="text" id="adminLoginName" name="adminLoginName" class="ui-input" placeholder="登录名" autocomplete="off" required>
            </div>
            <div class="form-item">
                <label for="adminRealName">用户姓名</label>
                <input type="text" id="adminRealName" name="adminRealName" class="ui-input" placeholder="真实姓名" autocomplete="off" required>
            </div>
            <div class="form-item">
                <label for="adminPassword">登录密码</label>
                <input type="password" id="adminPassword" name="adminPassword" class="ui-input" placeholder="登录密码" autocomplete="off" required>
            </div>
            <div class="form-item">
                <label for="adminRole">用户等级</label>
                <select name="adminRoleId" required id="adminRole">
                     <option value="0">管理员</option>
<!-- 							 <option value="1">超级管理员</option> -->
                </select>
            </div>
            <div class="form-item">
                <button type="submit" class="ui-button ui-button-success" role="button">立即创建</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script>
    $('select').selectMatch();

    var createUserForm = $('#createUserForm');

    new Validate(createUserForm, function () {
        $.ajax({
            url: '<%=path%>/admin/register',
            type: 'POST',
            data: createUserForm.serialize()
        }).done(function(res){
            if(!res.code){
                location.href = '<%=path%>/admin/home';
            } else {
                $.lightTip.error(res.msg);
            }
        }).fail(function(err){
            alert(err);
        });
    });
</script>
</body>
</html>