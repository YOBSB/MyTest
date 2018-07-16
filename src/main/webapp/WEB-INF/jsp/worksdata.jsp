<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <li><a href="<%=path%>/season/showSeasonView.do">赛季管理</a></li>
        <%--<li class="active"><a href="<%=path%>/match/matchconfigView?seasonId=${seasonId}">赛事配置</a></li>--%>
        <li><a href="<%=path%>/formReview">作品审核</a></li>
        <li class="active"><a href="<%=path%>/worksData">作品数据</a></li>
        <li><a href="<%=path%>/honor/backstage">荣誉榜单配置</a></li>
    </ul>
    <c:if test="${adminUser.adminRoleId==1}">

    </c:if>
    <div class="logout-wrap"><a href="<%=path%>/admin/logout">退出登录</a></div>
</header>


<div class="main main-season">
    <div>
        <table class="ui-table" id="worksList">
            <thead>
            <tr>
                <th scope="col">作品ID</th>
                <th scope="col">投稿时间</th>
                <th scope="col">迷你号</th>
                <th scope="col">作品名称</th>
                <th scope="col">作品介绍</th>
                <th scope="col">作品图片</th>
                <th scope="col">有效浏览量</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <div id="pagination" class="pagination"></div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script type="text/html" id="tplWorksItem">
    {{each list}}
    <tr>
        <td>{{$value.worksId}}</td>
        <td>{{$value.submissionTime | dateFormat}}</td>
        <td>{{$value.miniId}}</td>
        <td>{{$value.worksName}}</td>
        <td>{{$value.worksIntroduce}}</td>
        <td><img src="{{$value.worksMainImage}}"></td>
        <td>{{$value.worksHeat}}</td>
    </tr>
    {{/each}}
</script>

<script>
    template.defaults.imports.dateFormat = function (date, format) {
        format = format || 'YYYY-MM-DD HH:mm:ss';
        return dayjs(date).format(format);
    };

    function Works(){
        this.pageBox = null;
        this.pageNum = 1;
        this.pageSize = 10;

        this.init();
    }

    Works.prototype = {
        init: function(){
            this.getList();
        },
        /**
         * 渲染列表
         * @param data [Object]
         */
        renderList: function(data){
            var _this = this;
            var tplWorksItem = $('#tplWorksItem').html();
            var listHtml = template.render(tplWorksItem, {
                list: data.worksList || []
            });

            $('#worksList tbody').html(listHtml);
        },
        /**
         * 获取列表数据
         */
        getList: function(){
            var _this = this;

            $.ajax({
                type: "get",
                url: "/eventsys/worksData/getWorksData",
                data: {
                    pageNum: _this.pageNum,
                    pageSize: _this.pageSize
                },
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                dataType: "json",
                success: function (response) {
                    if (!response.code) {
                        // 数据格式统一，data全部返回数组
                        if (response.data.worksList.length) {
                            // 分页
                            if(_this.pageBox){
                                _this.pageBox.num.length = response.data.pages * _this.pageSize;
                                _this.pageBox.show();
                            } else {
                                _this.pageBox = new Pagination($('#pagination'), {
                                    length: response.data.pages * _this.pageSize,
                                    every: _this.pageSize,
                                    onClick: function (el, cur) {
                                        _this.pageNum = cur;
                                        _this.getList();
                                    }
                                });
                            }
                        }
                    } else {
                        _this.pageBox.num.length = 0;
                        _this.pageBox.show();

                        $.lightTip.error(response.msg);
                    }

                    // 渲染
                    _this.renderList(response.data)
                },
                error: function () {
                    $.lightTip.error('获取列表失败');
                }
            });
        }
    };

    new Works();


</script>
</body>

</html>
