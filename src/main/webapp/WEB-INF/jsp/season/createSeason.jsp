<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
<div class="main">
	<div class="manage-sec-create">
		<form action="<%=path%>/season/createSeason.do" method="post" id="createSeasonForm">
			<h2>创建新赛季</h2>
			<div class="form-item">
				<label for="seasonId">赛季ID</label>
				<input type="text" id="seasonId" name="seasonId" class="ui-input" placeholder="赛季ID，例：201807" autocomplete="off" required pattern="^[A-Za-z0-9]|[0-9]$">
			</div>
			<div class="form-item">
				<label for="seasonName">赛季名称</label>
				<input type="text" id="seasonName" name="seasonName" class="ui-input" placeholder="赛季名称" autocomplete="off" required>
			</div>
			<div class="form-item">
				<label for="seasonDescription">赛季描述</label>
				<textarea name="seasonDescription" id="seasonDescription" cols="30" rows="5" required class="ui-textarea" placeholder="赛季简要描述"></textarea>
			</div>
			<div class="form-item">
				<label for="seasonKeyWords">赛季关键词</label>
				<textarea name="seasonKeyWords" id="seasonKeyWords" cols="30" rows="5" required class="ui-textarea" placeholder="赛季关键词，用英文逗号间隔，如：迷你世界,建造大赛"></textarea>
			</div>
			<div class="form-item">
				<label for="dateRange">投稿时间</label>
				<span class="ui-input ui-date-range-input"><input type="date-range" id="contributeDates" required></span>
			</div>
			<div class="form-item">
				<label for="dateRange">赛季时间</label>
				<span class="ui-input ui-date-range-input"><input type="date-range" id="dateRange" required></span>
			</div>
			<div class="form-item">
				<button type="submit" class="ui-button ui-button-success" role="button">确认创建</button>
			</div>
		</form>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script>
	var dateRange = {
	    startTime: null,
		endTime: null
	};

	var contributeDates = {
        startTime: null,
        endTime: null
	};

	var hadSeasonId = false;

	function getTodayDate(){
	    var _date = new Date();

	    var year = _date.getFullYear();
	    var month = (_date.getMonth() + 1) < 10 ? '0'+(_date.getMonth()+1) : _date.getMonth()+1;
	    var date =  _date.getDate() < 10 ? '0'+_date.getDate() : _date.getDate();
	    return year + '-' + month + '-' + date;
	}

	$('#dateRange').dateTime({
		min: getTodayDate()
	}).change(function () {
		var result = this.value.split(' ');
        dateRange.startTime = new Date(result[0]+' 00:00:00').getTime();
        dateRange.endTime = new Date(result[2]+' 00:00:00').getTime();
    });

	$('#contributeDates').dateTime({
        min: getTodayDate()
    }).change(function () {
        var result = this.value.split(' ');
        contributeDates.startTime = new Date(result[0]+' 00:00:00').getTime();
        contributeDates.endTime = new Date(result[2]+' 00:00:00').getTime();
    });


	// 判断seasonid是否重复
	$('#seasonId').on('blur', function () {
	    var seasonId = $.trim($(this).val());

	    if(!seasonId){
	        return;
		}

		$.ajax({
			url: '<%=path%>/season/judgeSeasonId',
			type: 'POST',
			dataType: 'json',
			data: {
			    'seasonId': seasonId
			}
		})
			.done(function(res){
			    if(res.code > 0){
                    $.lightTip.error(res.msg);
                    hadSeasonId = true;
				} else {
                    hadSeasonId = false;
				}
			})
			.fail(function (err) {
                $.lightTip.error('检测赛季ID出错');
            })
			.always(function (res) {
				//
            });
    });

    new Validate($('#createSeasonForm'), function () {

        if(!dateRange.startTime){
            $.lightTip.error('请选择赛季时间');
            return;
		} else if(dateRange.startTime === dateRange.endTime){
            $.lightTip.error('您选择的时间区间太短，请重新选择');
            return;
		}

        if(!contributeDates.startTime){
            $.lightTip.error('请选择投稿时间');
            return;
        } else if(contributeDates.startTime === contributeDates.endTime){
            $.lightTip.error('您选择的投稿时间区间太短，请重新选择');
            return;
        }

		if(hadSeasonId){
            $.lightTip.error('赛季ID与已建赛季ID重复');
            return;
		}

        $.ajax({
            url: '<%=path%>/season/createSeason.do',
            type: 'POST',
            data: {
                seasonId: $('#seasonId').val(),
                seasonName: $('#seasonName').val(),
                seasonDescription: $('#seasonDescription').val(),
                seasonKeyWords: $('#seasonKeyWords').val(),
                startTime: dateRange.startTime,
                endTime: dateRange.endTime,
                submitStartTime: contributeDates.startTime,
                submitEndTime: contributeDates.endTime
            }
        }).done(function(res){
            if(!res.code){
                location.href = '<%=path%>/season/showSeasonView.do'
            } else {
                $.lightTip.error(res.msg);
            }
        }).fail(function(err){
            $.lightTip.error(err);
        });
    }, {
        validate: [{
            id: 'seasonId',
            prompt: {
                ignore: '请输入赛季ID',
				unmatch: '赛季ID格式错误'
            }
        }, {
			id: 'seasonName',
			prompt: {
				ignore: '请输入赛季名称'
			}
		}, {
            id: 'seasonDescription',
            prompt: {
                ignore: '请输入赛季描述'
            }
        }, {
            id: 'seasonKeyWords',
            prompt: {
                ignore: '请输入赛季关键词'
            }
        }]
    });
</script>
</body>

</html>