<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
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
	<div class="main-header">
		<h2>用户列表</h2>
		<c:choose>
			<c:when test="${adminUser.adminRoleId==1}">
				<a href="<%=path%>/admin/toRegister" class="ui-button ui-button-primary" role="button">创建新用户</a>
			</c:when>
		</c:choose>
	</div>
	<table class="ui-table">
		<tr>
			<th scope="col">ID</th>
			<th scope="col">登录名</th>
			<th scope="col">真实姓名</th>
			<th scope="col">等级</th>
			<th scope="col">创建时间</th>
			<th scope="col">更新时间</th>
			<th scope="col">操作</th>
		</tr>
		<c:forEach items="${list}" var="data">
		<tr>
			<td>${data.adminId }</td>
			<td>${data.adminLoginName }</td>
			<td>${data.adminRealName }</td>
			<td>
				<c:choose>
					<c:when test="${data.adminRoleId==1}">
						超级管理员
					</c:when>
					<c:otherwise>
						普通管理员
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<jsp:useBean id="dateValue" class="java.util.Date"/>
				<jsp:setProperty name="dateValue" property="time" value="${data.createTime}"/>
				<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/> 
			</td>
			<td>
				<c:choose>
					<c:when test="${data.updateTime==null}">
						--
					</c:when>
					<c:otherwise>
						<jsp:setProperty name="dateValue" property="time" value="${data.updateTime}"/>
						<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/> 
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${data.adminRoleId==1}">
					</c:when>
					<c:otherwise>
						<a href="<%=path%>/admin/upUser?adminId=${data.adminId}" class="ui-button ui-button-primary" role="button">修改</a>
						<a href="javascript:;" data-delete="${data.adminId}" class="ui-button ui-button-warning" role="button">删除</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:forEach>
	</table>
<%-- 	<jsp:include page="/WEB-INF/jsp/common/pager.jsp"/> --%>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script type="text/javascript">
    $('[data-delete]').click(function() {
        var elBtn = $(this);
        var userId = elBtn.data('delete');

        new Dialog().confirm('\
        <h6>您确定删除此用户吗？</h6>\
        <p>用户删除后，此用户的数据将无法恢复。</p>'
            , {
                buttons: [{
                    events: function(event) {
                        $.post("<%=path%>/admin/delete",{
                            adminId: userId
						},function(res){
                            if(!res.code){
                                window.location = '<%=path%>/admin/home';
							} else {
                                $.lightTip.error(res.msg);
							}
                        });
                    }
                }, {}]
            });
    });
</script>
</body>
</html>