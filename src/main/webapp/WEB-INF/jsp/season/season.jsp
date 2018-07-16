<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String ServerPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<header class="site-header manage-header">
	<h1 class="logo">建造大赛管理后台</h1>
	<div class="current-id" style="line-height: 60px;">
		<c:choose>
	    	<c:when test="${sId!=''}">
	    	   	当前赛季：${sId}/${sName}
	    	</c:when>
	    	<c:otherwise>
	    		当前赛季：无活跃赛季
	    	</c:otherwise>
	    </c:choose>
	</div>
	<ul class="site-nav">
		<li><a href="<%=path%>/admin/home">用户管理</a></li>
		<li class="active"><a href="<%=path%>/season/showSeasonView.do">赛季管理</a></li>
		<li><a href="<%=path%>/formReview">作品审核</a></li>
		<li><a href="<%=path%>/worksData">作品数据</a></li>
		<li><a href="<%=path%>/honor/backstage">荣誉榜单配置</a></li>
	</ul>
	<div class="logout-wrap"><a href="<%=path%>/admin/logout">退出登录</a></div>
</header>

<div class="main main-season">
	<div class="main-header">
		<h2>赛季管理</h2>
		<c:if test="${adminRoleId==1}">
			<button id="newSeason" class="ui-button ui-button-primary" role="button">创建新赛季</button>
		</c:if>
	</div>
	<table class="ui-table" id="seasonList">
		<tr>
			<th scope="col">赛季ID</th>
			<th scope="col">前台页面</th>
			<th scope="col">赛季名称</th>
			<th scope="col">赛季描述</th>
			<th scope="col">赛季关键词</th>
			<th scope="col">投稿时间</th>
			<th scope="col">赛季时间</th>
			<th scope="col">创建时间</th>
			<th scope="col">操作</th>
		</tr>
		<c:if test="${empty list}">
			<tr>
				<td colspan="8" align="center">还没有可用赛季，请超级管理员创建新赛季</td>
			</tr>
		</c:if>
		<c:if test="${!empty list}">
			<c:forEach var="season" items="${list}">
				<tr>
					<td>${season.id}</td>
					<td><%=ServerPath%>/jzds/${season.id}/pc/</td>
					<td>${season.seasonName}</td>
					<td>${season.seasonDescription}</td>
					<td>${season.seasonKeyWords}</td>
					<td>
						<jsp:useBean id="dateValue" class="java.util.Date"/>
						<jsp:setProperty name="dateValue" property="time" value="${season.submitStartTime}"/>
						<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/>
						--
						<jsp:setProperty name="dateValue" property="time" value="${season.submitEndTime}"/>
						<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/>
					</td>
					<td>
						<jsp:setProperty name="dateValue" property="time" value="${season.startTime}"/>
						<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/>
					--
						<jsp:setProperty name="dateValue" property="time" value="${season.endTime}"/> 
						<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/>
					</td>
					<td>
						<jsp:setProperty name="dateValue" property="time" value="${season.createTime}"/>
						<fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/></td>
					<td>
						<c:choose>
							<c:when test="${adminRoleId==1}">
								<a href="<%=path%>/match/matchconfigView?seasonId=${season.id}" class="ui-button ui-button-primary ui-button-small" role="button">赛事管理</a>
								<a href="<%=path%>/season/updateSeasonView?seasonId=${season.id}" class="ui-button ui-button-primary ui-button-small" role="button">修改赛季配置</a>
								<button type="button" data-id="${season.id}" data-type="delete" class="ui-button ui-button-warning ui-button-small" role="button">删除赛季</button>
								<c:if test="${season.seasonLife==1}">
									<button type="button" data-id="${season.id}" data-type="stop" class="ui-button ui-button-warning ui-button-small" role="button">结束当前赛季</button>
								</c:if>
								<c:if test="${season.seasonLife==0}">
									<button type="button" data-id="${season.id}" data-type="start" class="ui-button ui-button-success ui-button-small" role="button">开启当前赛季</button>
								</c:if>
							</c:when>
							<c:otherwise>
								<!-- 这个管理链接到作品审核 -->
								<a href="<%=path%>/formReview" class="ui-button ui-button-primary ui-button-small" role="button">赛事管理</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script type="text/javascript">
	$('#newSeason').on('click', function(){
	    $.ajax({
			url: '<%=path%>/season/judgeLife',
			type: 'POST',
			dataType: 'json'
		})
		.done(function(res){
		    if(!res.code){
                window.location.href="<%=path%>/season/createSeasonView.do";
			} else {
                $.lightTip.error(res.msg);
			}
		})
		.fail(function(err){
            $.lightTip.error('服务出错');
		})
		.always(function(res){
		    //
		});
	});


	function deleteOrStopSeason(id, url, type){
	    $.ajax({
			url: url,
			type: 'POST',
			data: {
                seasonId: id
			},
			dataType: 'json'
		})
            .done(function(res){
                if(!res.code){
                    window.location.reload();
                } else {
                    $.lightTip.error(res.msg);
                }
            })
            .fail(function(err){
                $.lightTip.error('服务出错');
            })
            .always(function(res){
                //
            });
	}

	$('#seasonList').on('click', 'button', function(){
        var api = {
            delete: '<%=path%>/season/deleteSeasonView',
            stop: '<%=path%>/season/stopSeasonView',
            start: '<%=path%>/season/startSeasonView'
        };

	    if($(this).data('type') === 'delete'){
            deleteOrStopSeason($(this).data('id'), api.delete, 'delete');
		} else if($(this).data('type') === 'stop'){
            deleteOrStopSeason($(this).data('id'), api.stop, 'stop')
		} else if($(this).data('type') === 'start'){
			deleteOrStopSeason($(this).data('id'), api.start, 'start')
		}
	});
</script>

</body>
</html>