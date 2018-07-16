<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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
        <li><a href="<%=path%>/admin/home">用户管理</a></li>
        <li class="active"><a href="<%=path%>/season/showSeasonView.do">赛季管理</a></li>
        <%--<li class="active"><a href="<%=path%>/match/matchconfigView?seasonId=${seasonId}">赛事配置</a></li>--%>
        <li><a href="<%=path%>/formReview">作品审核</a></li>
        <li><a href="<%=path%>/worksData">作品数据</a></li>
        <li><a href="<%=path%>/honor/backstage">荣誉榜单配置</a></li>
    </ul>
    <div class="logout-wrap"><a href="<%=path%>/admin/logout">退出登录</a></div>
</header>

<div class="main season-config-wrap">
    <div class="wrap">
        <div id="tabView" class="ui-tab-tabs">
            <a href="javascript:" class="ui-tab-tab checked" data-rel="tabTarget1">PC版配置</a>
            <a href="javascript:" class="ui-tab-tab" data-rel="tabTarget2">Mobile版配置</a>
        </div>
        <div class="ui-tab-contents" id="tabViewContents">
            <div class="ui-tab-content checked">
                <form id="pcForm" action="<%=path%>/match/save" method="post" class="config-form">
                    <input id="Id" name="Id" value="${Id}" type="hidden">
                    <input type="hidden" name="state" value="1">
                    <h3>公用配置</h3>
                    <div class="form-item">
                        <label for="seasonId">赛季ID：</label>
                        <input id="seasonId" class="ui-input" name="seasonId" value="${seasonId}" type="text" readonly>
                    </div>
                    <div class="form-item">
                        <label>顶部banner：</label>
                        <c:choose>
                            <c:when test="${match.bannerImage == null || match.bannerImage == ''}">
                                <label for="bannerImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="bannerImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="bannerImage" value="${match.bannerImage}">
                        <c:choose>
                            <c:when test="${match.bannerImage == null || match.bannerImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.bannerImage}" id="bannerImagePreview">
                            <a href="javascript:;" class="delete" data-id="bannerImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸1920px*650px -- banner各页面共用一张</div>
                    </div>
                    <h3>首页配置</h3>
                    <div class="form-item">
                        <label>建造主题图片：</label>
                        <c:choose>
                            <c:when test="${match.themeImage == null || match.themeImage == ''}">
                                <label for="themeImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="themeImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="themeImage" value="${match.themeImage}">
                        <c:choose>
                            <c:when test="${match.themeImage == null || match.themeImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.themeImage}" id="themeImagePreview">
                            <a href="javascript:;" class="delete" data-id="themeImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>赛事时间图片：</label>
                        <c:choose>
                            <c:when test="${match.timeImage == null || match.timeImage == ''}">
                                <label for="timeImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="timeImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="timeImage" value="${match.timeImage}">
                        <c:choose>
                            <c:when test="${match.timeImage == null || match.timeImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.timeImage}" id="timeImagePreview">
                            <a href="javascript:;" class="delete" data-id="timeImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>赛事奖励图片：</label>
                        <c:choose>
                            <c:when test="${match.rewardImage == null || match.rewardImage == ''}">
                                <label for="rewardImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="rewardImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="rewardImage" value="${match.rewardImage}">
                        <c:choose>
                            <c:when test="${match.rewardImage == null || match.rewardImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.rewardImage}" id="rewardImagePreview">
                            <a href="javascript:;" class="delete" data-id="rewardImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>参赛规则背景图：</label>
                        <c:choose>
                            <c:when test="${match.ruleImage == null || match.ruleImage == ''}">
                                <label for="ruleImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="ruleImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="ruleImage" value="${match.ruleImage}">
                        <c:choose>
                            <c:when test="${match.ruleImage == null || match.ruleImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.ruleImage}" id="ruleImagePreview">
                            <a href="javascript:;" class="delete" data-id="ruleImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="ruleContainer">赛事规则文案：</label>
                        <textarea name="rule" id="ruleContainer">${match.rule}</textarea>
                    </div>
                    <div class="form-item">
                        <label>评审机制背景图：</label>
                        <c:choose>
                            <c:when test="${match.reviewImage == null || match.reviewImage == ''}">
                                <label for="reviewImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="reviewImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="reviewImage" value="${match.reviewImage}">
                        <c:choose>
                            <c:when test="${match.reviewImage == null || match.reviewImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.reviewImage}" id="reviewImagePreview">
                            <a href="javascript:;" class="delete" data-id="reviewImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="reviewContainer">评审机制文案：</label>
                        <textarea name="review" id="reviewContainer" cols="30" rows="10">${match.review}</textarea>
                    </div>
                    <div class="form-item">
                        <label>评审嘉宾图片：</label>
                        <c:choose>
                            <c:when test="${match.judgeGuest == null || match.judgeGuest == ''}">
                                <label for="judgeGuest" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="judgeGuest" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="judgeGuest" value="${match.judgeGuest}">
                        <c:choose>
                            <c:when test="${match.judgeGuest == null || match.judgeGuest == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.judgeGuest}" id="judgeGuestPreview">
                            <a href="javascript:;" class="delete" data-id="judgeGuest"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>赛事介绍背景图：</label>
                        <c:choose>
                            <c:when test="${match.gameIntroduceImage == null || match.gameIntroduceImage == ''}">
                                <label for="gameIntroduceImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="gameIntroduceImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="gameIntroduceImage" value="${match.gameIntroduceImage}">
                        <c:choose>
                            <c:when test="${match.gameIntroduceImage == null || match.gameIntroduceImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.gameIntroduceImage}" id="gameIntroduceImagePreview">
                            <a href="javascript:;" class="delete" data-id="gameIntroduceImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="gameIntroduceContainer">赛事介绍文案：</label>
                        <textarea name="gameIntroduce" id="gameIntroduceContainer" cols="30"
                                  rows="10">${match.gameIntroduce}</textarea>
                    </div>
                    <div class="form-item">
                        <label>合作伙伴图片：</label>
                        <c:choose>
                            <c:when test="${match.partner == null || match.partner == ''}">
                                <label for="partner" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="partner" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="partner" value="${match.partner}">
                        <c:choose>
                            <c:when test="${match.partner == null || match.partner == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.partner}" id="partnerPreview">
                            <a href="javascript:;" class="delete" data-id="partner"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <h3>投稿页面配置</h3>
                    <div class="form-item">
                        <label>投稿说明背景图：</label>
                        <c:choose>
                            <c:when test="${match.submissionIntroduceImage == null || match.submissionIntroduceImage == ''}">
                                <label for="submissionIntroduceImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="submissionIntroduceImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="submissionIntroduceImage" value="${match.submissionIntroduceImage}">
                        <c:choose>
                            <c:when test="${match.submissionIntroduceImage == null || match.submissionIntroduceImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.submissionIntroduceImage}" id="submissionIntroduceImagePreview">
                            <a href="javascript:;" class="delete" data-id="submissionIntroduceImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="submissionIntroduceContainer">投稿说明文案：</label>
                        <textarea name="submissionIntroduce" id="submissionIntroduceContainer" cols="30"
                                  rows="10">${match.submissionIntroduce}</textarea>
                    </div>
                    <h3>本届作品页面配置</h3>
                    <div class="form-item">
                        <label>本届作品说明背景图：</label>
                        <c:choose>
                            <c:when test="${match.worksIntroduceImage == null || match.worksIntroduceImage == ''}">
                                <label for="worksIntroduceImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="worksIntroduceImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="worksIntroduceImage" value="${match.worksIntroduceImage}">
                        <c:choose>
                            <c:when test="${match.worksIntroduceImage == null || match.worksIntroduceImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.worksIntroduceImage}" id="worksIntroduceImagePreview">
                            <a href="javascript:;" class="delete" data-id="worksIntroduceImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="worksIntroduceContainer">本届作品说明文案：</label>
                        <textarea name="worksIntroduce" id="worksIntroduceContainer" cols="30"
                                  rows="10">${match.worksIntroduce}</textarea>
                    </div>
                    <div class="form-item">
                        <button type="submit" class="ui-button ui-button-success" role="button">保存配置</button>
                    </div>
                </form>
            </div>
            <div class="ui-tab-content">
                <form id="mobileForm" action="<%=path%>/match/save" method="post" class="config-form">
                    <input id="Id" name="Id" value="${Id}" type="hidden">
                    <input type="hidden" name="state" value="0">
                    <h3>公用配置</h3>
                    <div class="form-item">
                        <label for="mSeasonId">赛季ID：</label>
                        <input id="mSeasonId" class="ui-input" name="seasonId" value="${seasonId}" type="text" readonly>
                    </div>
                    <div class="form-item">
                        <label>顶部banner：</label>
                        <c:choose>
                            <c:when test="${match.mobileBannerImage == null || match.mobileBannerImage == ''}">
                                <label for="mobileBannerImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileBannerImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileBannerImage" value="${match.mobileBannerImage}">
                        <c:choose>
                            <c:when test="${match.mobileBannerImage == null || match.mobileBannerImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileBannerImage}" id="mobileBannerImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileBannerImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸750px*650px -- banner各页面共用一张</div>
                    </div>
                    <h3>首页配置</h3>
                    <div class="form-item">
                        <label>建造主题图片：</label>
                        <c:choose>
                            <c:when test="${match.mobileThemeImage == null || match.mobileThemeImage == ''}">
                                <label for="mobileThemeImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileThemeImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileThemeImage" value="${match.mobileThemeImage}">
                        <c:choose>
                            <c:when test="${match.mobileThemeImage == null || match.mobileThemeImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileThemeImage}" id="mobileThemeImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileThemeImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>赛事时间图片：</label>
                        <c:choose>
                            <c:when test="${match.mobileTimeImage == null || match.mobileTimeImage == ''}">
                                <label for="mobileTimeImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileTimeImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileTimeImage" value="${match.mobileTimeImage}">
                        <c:choose>
                            <c:when test="${match.mobileTimeImage == null || match.mobileTimeImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileTimeImage}" id="mobileTimeImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileTimeImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>赛事奖励图片：</label>
                        <c:choose>
                            <c:when test="${match.mobileRewardImage == null || match.mobileRewardImage == ''}">
                                <label for="mobileRewardImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileRewardImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileRewardImage" value="${match.mobileRewardImage}">
                        <c:choose>
                            <c:when test="${match.mobileRewardImage == null || match.mobileRewardImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileRewardImage}" id="mobileRewardImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileRewardImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>参赛规则背景图：</label>
                        <c:choose>
                            <c:when test="${match.mobileRuleImage == null || match.mobileRuleImage == ''}">
                                <label for="mobileRuleImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileRuleImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileRuleImage" value="${match.mobileRuleImage}">
                        <c:choose>
                            <c:when test="${match.mobileRuleImage == null || match.mobileRuleImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileRuleImage}" id="mobileRuleImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileRuleImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="mobileRuleContainer">赛事规则文案：</label>
                        <textarea name="mobileRule" id="mobileRuleContainer" cols="30"
                                  rows="10">${match.mobileRule}</textarea>
                    </div>
                    <div class="form-item">
                        <label>评审机制背景图：</label>
                        <c:choose>
                            <c:when test="${match.mobileReviewImage == null || match.mobileReviewImage == ''}">
                                <label for="mobileReviewImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileReviewImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileReviewImage" value="${match.mobileReviewImage}">
                        <c:choose>
                            <c:when test="${match.mobileReviewImage == null || match.mobileReviewImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileReviewImage}" id="mobileReviewImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileReviewImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="mobileReviewContainer">评审机制文案：</label>
                        <textarea name="mobileReview" id="mobileReviewContainer" cols="30"
                                  rows="10">${match.mobileReview}</textarea>
                    </div>
                    <div class="form-item">
                        <label>评审嘉宾图片：</label>
                        <c:choose>
                            <c:when test="${match.mobileJudgeGuest == null || match.mobileJudgeGuest == ''}">
                                <label for="mobileJudgeGuest" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileJudgeGuest" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileJudgeGuest" value="${match.mobileJudgeGuest}">
                        <c:choose>
                            <c:when test="${match.mobileJudgeGuest == null || match.mobileJudgeGuest == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileJudgeGuest}" id="mobileJudgeGuestPreview">
                            <a href="javascript:;" class="delete" data-id="mobileJudgeGuest"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label>赛事介绍背景图：</label>
                        <c:choose>
                            <c:when test="${match.mobileGameIntroduceImage == null || match.mobileGameIntroduceImage == ''}">
                                <label for="mobileGameIntroduceImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileGameIntroduceImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileGameIntroduceImage" value="${match.mobileGameIntroduceImage}">
                        <c:choose>
                            <c:when test="${match.mobileGameIntroduceImage == null || match.mobileGameIntroduceImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileGameIntroduceImage}" id="mobileGameIntroduceImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileGameIntroduceImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="mobileGameIntroduceContainer">赛事介绍文案：</label>
                        <textarea name="mobileGameIntroduce" id="mobileGameIntroduceContainer" cols="30"
                                  rows="10">${match.mobileGameIntroduce}</textarea>
                    </div>
                    <div class="form-item">
                        <label>合作伙伴图片：</label>
                        <c:choose>
                            <c:when test="${match.mobilePartner == null || match.mobilePartner == ''}">
                                <label for="mobilePartner" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobilePartner" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobilePartner" value="${match.mobilePartner}">
                        <c:choose>
                            <c:when test="${match.mobilePartner == null || match.mobilePartner == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobilePartner}" id="mobilePartnerPreview">
                            <a href="javascript:;" class="delete" data-id="mobilePartner"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <h3>投稿页面配置</h3>
                    <div class="form-item">
                        <label>投稿说明背景图：</label>
                        <c:choose>
                            <c:when test="${match.mobileSubmissionIntroduceImage == null || match.mobileSubmissionIntroduceImage == ''}">
                                <label for="mobileSubmissionIntroduceImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileSubmissionIntroduceImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileSubmissionIntroduceImage"
                               value="${match.mobileSubmissionIntroduceImage}">
                        <c:choose>
                            <c:when test="${match.mobileSubmissionIntroduceImage == null || match.mobileSubmissionIntroduceImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileSubmissionIntroduceImage}"
                                 id="mobileSubmissionIntroduceImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileSubmissionIntroduceImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="mobileSubmissionIntroduceContainer">投稿说明文案：</label>
                        <textarea name="mobileSubmissionIntroduce" id="mobileSubmissionIntroduceContainer" cols="30"
                                  rows="10">${match.mobileSubmissionIntroduce}</textarea>
                    </div>
                    <h3>本届作品页面配置</h3>
                    <div class="form-item">
                        <label>本届作品说明背景图：</label>
                        <c:choose>
                            <c:when test="${match.mobileWorksIntroduceImage == null || match.mobileWorksIntroduceImage == ''}">
                                <label for="mobileWorksIntroduceImage" class="ui-button" style="display: block;">上传</label>
                            </c:when>
                            <c:otherwise>
                                <label for="mobileWorksIntroduceImage" class="ui-button" style="display: none;">上传</label>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="mobileWorksIntroduceImage"
                               value="${match.mobileWorksIntroduceImage}">
                        <c:choose>
                            <c:when test="${match.mobileWorksIntroduceImage == null || match.mobileWorksIntroduceImage == ''}">
                                <div class="preview" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="preview" style="display: block;">
                            </c:otherwise>
                        </c:choose>
                            <img src="${match.mobileWorksIntroduceImage}" id="mobileWorksIntroduceImagePreview">
                            <a href="javascript:;" class="delete" data-id="mobileWorksIntroduceImage"></a>
                        </div>
                        <div class="notice">说明：图片尺寸</div>
                    </div>
                    <div class="form-item">
                        <label for="mobileWorksIntroduceContainer">本届作品说明文案：</label>
                        <textarea name="mobileWorksIntroduce" id="mobileWorksIntroduceContainer" cols="30"
                                  rows="10">${match.mobileWorksIntroduce}</textarea>
                    </div>
                    <div class="form-item">
                        <button type="submit" class="ui-button ui-button-success" role="button">保存配置</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--上传表单--%>
<div class="hide" id="fileUploads">
    <form class="hide">
        <input type="file" name="eventImage" id="bannerImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="themeImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="timeImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="rewardImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="ruleImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="reviewImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="judgeGuest" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="gameIntroduceImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="partner" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="submissionIntroduceImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="worksIntroduceImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileBannerImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileThemeImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileTimeImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileRewardImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileRuleImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileReviewImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileJudgeGuest" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileGameIntroduceImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobilePartner" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileSubmissionIntroduceImage" accept="image/*">
    </form>
    <form class="hide">
        <input type="file" name="eventImage" id="mobileWorksIntroduceImage" accept="image/*">
    </form>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<link rel="stylesheet" href="http://heby-test.mini1.cn/statics/jzds/libs/ueditor1_4_3/themes/default/css/ueditor.css">
<script src="http://heby-test.mini1.cn/statics/jzds/libs/ueditor1_4_3/ueditor.config.js"></script>
<script src="http://heby-test.mini1.cn/statics/jzds/libs/ueditor1_4_3/ueditor.all.min.js"></script>
<script src="http://heby-test.mini1.cn/statics/jzds/libs/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script src="//cdn.jsdelivr.net/npm/blueimp-file-upload@9.22.0/js/vendor/jquery.ui.widget.js"></script>
<script src="//cdn.jsdelivr.net/npm/blueimp-file-upload@9.22.0/js/jquery.fileupload.min.js"></script>

<script type="text/javascript">
    // tab
    $('#tabView').on('click', '.ui-tab-tab', function () {
        $(this).addClass('checked').siblings().removeClass('checked');
        $('#tabViewContents .ui-tab-content').eq($(this).index()).addClass('checked').siblings().removeClass('checked');
    });

    // 公用编辑器配置
    var commonEditorOptions = {
        initialFrameWidth: 600,
        initialFrameHeight: 240,
        toolbars: [
            [
                'anchor', //锚点
                'undo', //撤销
                'redo', //重做
                'bold', //加粗
                'indent', //首行缩进
                'italic', //斜体
                'underline', //下划线
                'strikethrough', //删除线
                'subscript', //下标
                'fontborder', //字符边框
                'superscript', //上标
                'formatmatch', //格式刷
                'source', //源代码
                'blockquote', //引用
                'pasteplain', //纯文本粘贴模式
                'selectall', //全选
                'preview', //预览
                'horizontal', //分隔线
                'removeformat', //清除格式
                'unlink', //取消链接
                'cleardoc', //清空文档
                'insertparagraphbeforetable', //"表格前插入行"
                'fontfamily', //字体
                'fontsize', //字号
                'paragraph', //段落格式
                'link', //超链接
                'searchreplace', //查询替换
                'help', //帮助
                'justifyleft', //居左对齐
                'justifyright', //居右对齐
                'justifycenter', //居中对齐
                'justifyjustify', //两端对齐
                'forecolor', //字体颜色
                'backcolor', //背景色
                'insertorderedlist', //有序列表
                'insertunorderedlist', //无序列表
                'fullscreen', //全屏
                'directionalityltr', //从左向右输入
                'directionalityrtl', //从右向左输入
                'rowspacingtop', //段前距
                'rowspacingbottom', //段后距
                'imagenone', //默认
                'imageleft', //左浮动
                'imageright', //右浮动
                'imagecenter', //居中
                'lineheight', //行间距
                'edittip ', //编辑提示
                'customstyle', //自定义标题
                'touppercase', //字母大写
                'tolowercase', //字母小写
            ]
        ]
    };

    // 初始化编辑器
    var editorContainers = [
        'ruleContainer',
        'reviewContainer',
        'gameIntroduceContainer',
        'submissionIntroduceContainer',
        'worksIntroduceContainer',
        'mobileRuleContainer',
        'mobileReviewContainer',
        'mobileGameIntroduceContainer',
        'mobileSubmissionIntroduceContainer',
        'mobileWorksIntroduceContainer'
    ];

    for (var i = 0, l = editorContainers.length; i < l; i++) {
        UE.getEditor(editorContainers[i], commonEditorOptions);
    }

    // 上传图片逻辑
    $('#fileUploads input[type="file"]').fileupload({
        url: 'http://image-test.mini1.cn/api/image/uploadCommon',
        type: 'post',
        dataType: 'json',
        autoUpload: true,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        formData: {
            platform: 'javasite',
            token: '891A6E81A88DA3C38D316956159E777C'
        }
    }).on('fileuploaddone', function (e, data) {
        var targetId = e.target.id;

        $('input[name="' + targetId + '"]').val(data.result.data.eventImage.cdn_url);
        $('#' + targetId + 'Preview')
            .attr('src', data.result.data.eventImage.cdn_url)
            .parent()
            .show();
        $('label[for="' + targetId + '"]').hide();

    }).on('fileuploadfail', function (e, data) {
        console.log(data);
    });

    // 删除图片重新上传
    $('.config-form').on('click', 'a.delete', function () {
        var targetId = $(this).data('id');

        $(this).parent().hide();
        $('label[for="' + targetId + '"]').show();
        $('input[name="' + targetId + '"]').val('');
        $('#' + targetId + 'Preview').attr('src', '');
    });

    // 表单提交
    $('.config-form').on('submit', function (e) {
        e.preventDefault();

        $(this).find('input[type="submit"]').prop('disabled', true).text('正在保存...');
        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            /*data: {
                state: $('input[name="state"]').val(),
                match: $(this).serialize()
            },*/
            data: $(this).serialize(),
            dataType: 'json'
        })
            .done(function (res) {
                if(!res.code){
                    location.href = '<%=path%>/season/showSeasonView.do';
                } else {
                    $.lightTip.error(res.msg);
                }
            })
            .fail(function (err) {
                $.lightTip.error('保存配置出错');
            })
            .always(function (res) {
                $(this).find('input[type="submit"]').prop('disabled', false).text('保存配置');
            })
    });
</script>
</body>
</html>