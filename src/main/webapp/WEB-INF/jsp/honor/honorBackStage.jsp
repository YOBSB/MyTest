<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = "//"+request.getServerName()+path+"/";
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
        <li><a href="<%=path%>/worksData">作品数据</a></li>
        <li class="active"><a href="<%=path%>/honor/backstage">荣誉榜单配置</a></li>
    </ul>
    <div class="logout-wrap"><a href="<%=path%>/admin/logout">退出登录</a></div>
</header>
<div class="main main-review">
    <div class="sec-header"><button type="button" class="ui-button ui-button-success" id="addHonor">添加榜单</button></div>
    <div>
        <table class="ui-table" id="honorList">
            <thead>
            <tr>
                <th scope="col">序号</th>
                <th scope="col">作品ID</th>
                <th scope="col">迷你号</th>
                <th scope="col">添加时间</th>
                <th scope="col">修改时间</th>
                <th scope="col">操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<div id="addFormWrap" class="hide">
    <form id="addHonorForm">
        <div class="ui-input"><input type="number" id="wid" min="0" required placeholder="输入作品ID"></div>
        <div><input type="submit" value="确认添加" class="ui-button ui-button-primary"></div>
    </form>
</div>

<div id="updateFormWrap" class="hide">
    <form id="updateHonorForm">
        <div class="ui-input"><input type="number" id="rwid" min="0" required placeholder="输入作品ID"></div>
        <div><input type="submit" value="确认修改" class="ui-button ui-button-primary"></div>
    </form>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>


<script type="text/html" id="tplHonorItem">
    {{if list.length}}
    {{each list}}
    <tr>
        <td>{{$value.rank}}</td>
        <td>{{$value.worksId}}</td>
        <td>{{$value.miniId}}</td>
        <td>{{$value.createTime | dateFormat}}</td>
        <td>{{$value.updateTime | dateFormat}}</td>
        <td>
            <button type="button" class="ui-button ui-button-success ui-button-small" data-wid="{{$value.worksId}}"
                    data-rank="{{$value.rank}}" data-update>修改</button>
            <button type="button" class="ui-button ui-button-warning ui-button-small" data-rank="{{$value.rank}}" data-del>删除</button>
        </td>
    </tr>
    {{/each}}
    {{else}}
    <tr>
        <td colspan="6">暂无数据</td>
    </tr>
    {{/if}}

</script>

<script>
    template.defaults.imports.dateFormat = function (date, format) {
        format = format || 'YYYY-MM-DD HH:mm:ss';
        return dayjs(date).format(format);
    };

    var honorList = $('#honorList');
    var addHonorBtn = $('#addHonor');

    function HonorList() {
        this.api = {
            list: '<%=path%>/honor/getHonorList',
            add: '<%=path%>/honor/addHonorTop',
            update: '<%=path%>/honor/updateHonor',
            del: '<%=path%>/honor/delHonorTop'
        };

        this.init();
    }

    HonorList.prototype = {
        init: function(){
            this.getList();
            this.add();
            this.update();
            this.del();
        },
        getList: function(){
            $.ajax({
                url: this.api.list,
                type: 'get',
                data: {
                    page: 1,
                    num: 100
                },
                dataType: 'json'
            })
                .done(function(res){
                    if(!res.code){
                        var tplHonorItem = $('#tplHonorItem').html();
                        var listHtml = template.render(tplHonorItem, {
                            list: res.data.honorWorks || []
                        });

                        honorList.find('tbody').html(listHtml);
                    } else {
                        $.lightTip.error(res.msg);
                    }
                }).fail(function(res){
                $.lightTip.error('获取列表失败');
            })
                .always(function(res){
                    //
                })
        },
        add: function () {
            var addDialog;
            var _this = this;
            addHonorBtn.click(function() {
                if (addDialog) {
                    addDialog.show();
                } else {
                    addDialog = new Dialog({
                        title: '添加榜单',
                        content: $('#addFormWrap').show()
                    });
                }
            });
            new Validate($('#addHonorForm'), function () {
                $.ajax({
                    url: _this.api.add,
                    type: 'get',
                    data: {
                        wid: $('#wid').val()
                    },
                    dataType: 'json'
                })
                    .done(function(res){
                        if(!res.code){
                            location.reload();
                        } else {
                            $.lightTip.error(res.msg);
                        }
                    }).fail(function(res){
                    $.lightTip.error('添加服务出错，请重试');
                }).always(function(res){
                    //
                })
            }, {
                validate: [{
                    id: 'wid',
                    prompt: {
                        ignore: '请输入作品ID'
                    }
                }]
            });
        },
        update: function(){
            var updateDialog;
            var _this = this;

            honorList.on('click', 'button[data-update]', function(){
                var wid = $(this).data('wid');
                var rank = $(this).data('rank');

                if (updateDialog) {
                    updateDialog.show();
                } else {
                    updateDialog = new Dialog({
                        title: '修改榜单',
                        content: $('#updateFormWrap').show(),
                        onShow: function(){
                            $('#updateFormWrap').find('#rwid').val(wid);
                        }
                    });
                }
                new Validate($('#updateHonorForm'), function () {
                    $.ajax({
                        url: _this.api.update,
                        type: 'get',
                        data: {
                            wid: $('#updateHonorForm').find('#rwid').val(),
                            rank: rank
                        },
                        dataType: 'json'
                    })
                        .done(function(res){
                            if(!res.code){
                                location.reload();
                            } else {
                                $.lightTip.error(res.msg);
                            }
                        }).fail(function(res){
                        $.lightTip.error('修改榜单出错，请重试');
                    }).always(function(res){
                        //
                    })
                },{
                    validate: [{
                        id: 'rwid',
                        prompt: {
                            ignore: '请输入作品ID'
                        }
                    }]
                });


            });

        },
        del: function(){
            var _this = this;
            honorList.on('click', 'button[data-del]', function(){
                var rank = $(this).data('rank');
                new Dialog().confirm('\
        <h6>您确定要删除这条榜单吗？</h6>'
                    , {
                        buttons: [{
                            events: function(event) {
                                $.ajax({
                                    url: _this.api.del,
                                    type: 'get',
                                    data: {
                                        rank: rank
                                    },
                                    dataType: 'json'
                                })
                                    .done(function(res){
                                        if(!res.code){
                                            location.reload();
                                        } else {
                                            $.lightTip.error(res.msg);
                                        }
                                    }).fail(function(res){
                                    $.lightTip.error('删除服务出错，请重试');
                                }).always(function(res){
                                    //
                                });
                            }
                        }, {}]
                    });

            });

        }
    };

    new HonorList();
</script>

</body>
</html>