<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    // 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script src="//cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<script src="//cdn.jsdelivr.net/npm/dayjs@1.7.2/dayjs.min.js"></script>
<script src="//cdn.jsdelivr.net/npm/art-template@4.12.2/lib/template-web.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Pagination.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Select.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Placeholder.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Loading.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Dialog.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Follow.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/LightTip.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/ErrorTip.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/Validate.js"></script>
<script src="<%=basePath%>resources/luluui/peak/js/common/ui/DateTime.js"></script>
