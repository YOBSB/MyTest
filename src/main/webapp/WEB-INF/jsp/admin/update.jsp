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
    <div class="manage-sec-update">
        <form action="<%=path%>/admin/update" method="post" id="updateUserForm">
            <h2>修改用户</h2>
            <input type="hidden" id="adminId" name="adminId" value="${adminUser.adminId }">
            <div class="form-item">
                <label for="adminLoginName">登录名</label>
                <input type="text" id="adminLoginName" name="adminLoginName" class="ui-input" value="${adminUser.adminLoginName }" readonly>
            </div>
            <div class="form-item">
                <label for="adminRealName">用户姓名</label>
                <input type="text" id="adminRealName" name="adminRealName" class="ui-input"  value="${adminUser.adminRealName }" required placeholder="只能是中文且为真实姓名" autocomplete="off">
            </div>
            <div class="form-item">
                <label for="adminPassword">登录密码</label>
                <%--<input type="text" id="adminPassword" name="adminPassword" value="${adminUser.adminPassword }">--%>
                <input type="password" id="adminPassword" name="adminPassword" class="ui-input" required autocomplete="off" placeholder="请输入新的用户登录密码">
            </div>
            <div class="form-item">
                <label for="adminRole">用户等级</label>
                <select name="adminRoleId" required id="adminRole">
                     <option value="0">管理员</option>
<!-- 							 <option value="1">超级管理员</option> -->
                </select>
            </div>
            <div class="form-item">
                <button type="submit" class="ui-button ui-button-success" role="button">立即更新</button>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
<script>
    $('select').selectMatch();

    var updateUserForm = $('#updateUserForm');

    new Validate(updateUserForm, function () {
        $.ajax({
            url: '<%=path%>/admin/update',
            type: 'POST',
            data: updateUserForm.serialize()
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