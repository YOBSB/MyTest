<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- <input type="hidden" name="page" id="pageNo" value="${pageInfo.pageNum}"/>
<input type="hidden" name="rows" id="pageSize" value="${pageInfo.pageSize}"/> --%>

<table class="tables" width="100%" border="0" align="center" cellspacing="0" >
	<tr>
		<td>
<!-- 		显示记录数   <select name="pageSize" style="width:175px">
							 <option value="1">1</option>
							 <option value="2">2</option>
							 <option value="3">3</option>
							 <option value="4">4</option>
                </select> -->
			&nbsp;共 <strong>${pageInfo.total}</strong> 条记录  &nbsp;|&nbsp;
			&nbsp;<a href="./home?pageNum=1&pageSize=${pageInfo.pageSize}">首页</a>
			<c:choose>
				<c:when test="${pageInfo.prePage == 0}">
					&nbsp;<span>上一页</span>
				</c:when>
				<c:otherwise>
					&nbsp;<a href="./home?pageNum=${pageInfo.prePage}&pageSize=${pageInfo.pageSize}">上一页</a>				
				</c:otherwise>
			</c:choose>
<%-- 			<c:forEach begin="1" end="${pageInfo.total}">
		    </c:forEach> --%>
			<c:choose>
				<c:when test="${pageInfo.nextPage == 0}">
					&nbsp;<span>下一页</span>
				</c:when>
				<c:otherwise>
					&nbsp;<a href="./home?pageNum=${pageInfo.nextPage}&pageSize=${pageInfo.pageSize}">下一页</a>			
				</c:otherwise>
			</c:choose>
			&nbsp;<a href="./home?pageNum=${pageInfo.lastPage}&pageSize=${pageInfo.pageSize}">尾页</a><br/>
			共&nbsp;<strong>${pageInfo.pages}</strong> &nbsp;页 当前为：第&nbsp;<strong>${pageInfo.pageNum}</strong>&nbsp;页
<!-- 			&nbsp;跳转到第<input type="text" width="50px"/>页 -->
		</td>
	</tr>
</table>