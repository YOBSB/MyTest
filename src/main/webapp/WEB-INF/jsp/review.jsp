<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
        <c:choose>
            <c:when test="${adminRoleId==1}">
                <li><a href="<%=path%>/admin/home">用户管理</a></li>
                <li><a href="<%=path%>/season/showSeasonView.do">赛季管理</a></li>
                <%--<li class="active"><a href="<%=path%>/match/matchconfigView?seasonId=${seasonId}">赛事配置</a></li>--%>
                <li class="active"><a href="<%=path%>/formReview">作品审核</a></li>
                <li><a href="<%=path%>/worksData">作品数据</a></li>
                <li><a href="<%=path%>/honor/backstage">荣誉榜单配置</a></li>
            </c:when>
            <c:otherwise>
                <li class="active"><a href="<%=path%>/formReview">作品审核</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
    <div class="logout-wrap"><a href="<%=path%>/admin/logout">退出登录</a></div>
</header>

<div class="main main-review">
    <div class="sec-header">
        <div class="filter-box" id="filterBox">
            <a href="javascript:;" class="active" data-state="100" data-type="all">全部</a>
            <a href="javascript:;" data-state="1" data-type="state">已通过</a>
            <a href="javascript:;" data-state="0" data-type="state">未通过</a>
            <a href="javascript:;" data-state="2" data-type="state">已拒绝</a>
            <a href="javascript:;" data-state="3" data-type="state">已屏蔽</a>
        </div>
        <div class="search-box">
            <form id="searchFom">
                <input type="number" class="ui-input" name="searchId" id="searchId" placeholder="输入作品ID或迷你号" required>
                <button type="submit" class="ui-button ui-button-primary">查询</button>
            </form>
        </div>
    </div>
    <div>
        <table class="ui-table" id="worksList">
            <thead>
                <tr>
                    <th scope="col">作品ID</th>
                    <th scope="col">投稿时间</th>
                    <th scope="col">迷你号</th>
                    <th scope="col">姓名</th>
                    <th scope="col">QQ</th>
                    <th scope="col">邮箱</th>
                    <th scope="col">联系方式</th>
                    <th scope="col">团队成员</th>
                    <th scope="col">作品名称</th>
                    <th scope="col">作品介绍</th>
                    <th scope="col">作品图片</th>
                    <th scope="col">操作</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <div id="pagination" class="pagination"></div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script type="text/html" id="tplWorksItem">
    {{if list.length}}
    {{each list}}
    <tr>
        <td>{{$value.worksId}}</td>
        <td>{{$value.submissionTime | dateFormat}}</td>
        <td>{{$value.miniId}}</td>
        <td>{{$value.name}}</td>
        <td>{{$value.qq}}</td>
        <td>{{$value.mail}}</td>
        <td>{{$value.phone}}</td>
        <td>{{$value.teamMates}}</td>
        <td>{{$value.worksName}}</td>
        <td>{{$value.worksIntroduce}}</td>
        <td><img src="{{$value.worksMainImage}}"></td>
        <td>
            {{if $value.worksState == 0}}
            <button type="button" class="ui-button ui-button-success ui-button-small" data-state="{{$value.worksState}}"
                    data-uin="{{$value.miniId}}" data-action="pass">通过</button>
            <button type="button" class="ui-button ui-button-warning ui-button-small" data-state="{{$value.worksState}}"
                    data-uin="{{$value.miniId}}" data-action="refuse">拒绝</button>
            {{else if $value.worksState == 1}}
            <button type="button" class="ui-button ui-button-warning ui-button-small" data-state="{{$value.worksState}}"
                    data-uin="{{$value.miniId}}" data-action="reject">屏蔽</button>
            {{else if $value.worksState == 2}}
            <span>已拒绝</span>
            {{else if $value.worksState == 3}}
            <span>已屏蔽</span>
            {{/if}}
        </td>
    </tr>
    {{/each}}
    {{else}}
        <tr>
            <td colspan="12">暂无数据</td>
        </tr>
    {{/if}}

</script>
<script>
    template.defaults.imports.dateFormat = function (date, format) {
        format = format || 'YYYY-MM-DD HH:mm:ss';
        return dayjs(date).format(format);
    };

    function Review(){
        this.pageBox = null;

        this._api = {
            all: '<%=path%>/formReview/getAllWorks',
            state: '<%=path%>/formReview/getWorksByWorksState',
            search: '<%=path%>/formReview/search'
        };

        // 默认全部作品列表
        this.options = {
            type: 'all',
            state: '100',
            pageSize: 10,
            pageNum: 1,
            searchId: 0
        };

        this.init();
    }

    Review.prototype = {
        init: function(){
            this.getList(this.options);

            this.bindFilter();
            this.setState();
            this.searchEnv();
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

            var _data = {};
            var defaultData = {
                pageNum: _this.options.pageNum,
                pageSize: _this.options.pageSize
            };

            var _type = _this.options.type;

            switch (_type){
                case 'all':
                    _data = defaultData;
                    break;
                case 'state':
                    _data = $.extend({}, defaultData, {
                        worksState: _this.options.state
                    });
                    break;
                case 'search':
                    _data = {
                        id: _this.options.searchId
                    };
                    break;
            }

            $.ajax({
                type: "get",
                url: this._api[_this.options.type],
                data: _data,
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
                                _this.pageBox.num.length = response.data.pages * _this.options.pageSize;
                                _this.pageBox.show();
                            } else {
                                _this.pageBox = new Pagination($('#pagination'), {
                                    length: response.data.pages * _this.options.pageSize,
                                    every: _this.options.pageSize,
                                    onClick: function (el, cur) {
                                        _this.options.pageNum = cur;
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
        },
        /**
         * 筛选列表
         */
        bindFilter: function () {
            var _this = this;
            $('#filterBox').on('click', 'a', function () {
                $(this).addClass('active').siblings().removeClass('active');

                _this.options.pageNum = 1;
                _this.options.type = $(this).data('type');
                _this.options.state = $(this).data('state');

                _this.getList();
            });
        },
        /**
         * 搜索框
         */
        searchEnv: function(){
            var _this = this;
            var searchForm = $('#searchFom');

            new Validate(searchForm, function(){
                var searchId = $.trim($('#searchId').val());

                _this.options.type = 'search';
                _this.options.searchId = searchId;

                _this.getList();
            },{
                validate: [{
                    id: 'searchId',
                    prompt: {
                        ignore: '输入作品ID或迷你号'
                    }
                }]
            });
        },
        /**
         * 设置状态
         */
        setState: function(){
            var _this = this;
            $('#worksList').on('click', '[data-action]', function () {
                var uin = $(this).data('uin');
                // var targetState = $(this).data('state');
                var action = $(this).data('action');
                var state = 0;

                switch (action) {
                    case 'pass':
                        state = 1;
                        break;
                    case 'refuse':
                        state = 2;
                        break;
                    case 'reject':
                        state = 3;
                        break;
                }

                $.ajax({
                    type: "get",
                    url: "/eventsys/formReview/update",
                    data: {
                        miniId: uin,
                        worksState: state
                    },
                    xhrFields: {
                        withCredentials: true
                    },
                    crossDomain: true,
                    dataType: "json",
                    success: function (response) {
                        if (!response.code) {
                            // location.reload();
                            _this.getList();
                        } else {
                            $.lightTip.error(response.msg);
                        }
                    },
                    error: function () {
                        $.lightTip.error('设置失败');
                    }
                });
            });
        }
    };

    new Review();



</script>

</body>

</html>
