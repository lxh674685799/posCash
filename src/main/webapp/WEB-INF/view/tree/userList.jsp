<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>用户列表</title>
	<%@include file="/commons/include/get.jsp" %>
	<%@include file="/commons/include/page.jsp" %>
	<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
	<script type="text/javascript">
	var isSingle='${isSingle}';
	$(function(){
		$("#sysUserItem").find("tr").bind('click', function() {
			if(isSingle=='true'){
				var rad=$(this).find('input[name=userData]:radio');
				rad.attr("checked","checked");
			}else{
				var ch=$(this).find(":checkbox[name='userData']");
				window.parent.selectMulti(ch);
			}
		});
	});	 
	</script>
	<style type="text/css">
	html { overflow-x: hidden; }
	</style>
</head>
<body>
<form action="getUserByOrgId.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="25%" align="left">
			<input type="hidden" name="isSingle" value="${isSingle}" >
			<input type="hidden" name="orgId" value="${orgId}" >
			<input type="hidden" name="roleId" value="${roleId}" >
			</td>
		</tr>	
	</table>
</form>

	<div class="panel">
	    <c:if test="${isSingle==false}">
			<c:set var="checkAll">
					<input onclick="window.parent.selectAll(this);" type="checkbox" />
			</c:set>
		</c:if>
		 <display:table name="userList" id="sysUserItem" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
			<display:column title="${checkAll}" media="html" style="width:30px;">
				<c:choose>
					<c:when test="${isSingle==false}">
						<input onchange="window.parent.selectMulti(this);"  type="checkbox" class="pk" name="userData" value="${sysUserItem.id}#${sysUserItem.name}">
					</c:when>
					<c:otherwise>
						<input type="radio" class="pk" name="userData" value="${sysUserItem.id}#${sysUserItem.name}">
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column property="name" title="姓名"   style="text-align:left;"></display:column>
		</display:table>
	</div>
	<br>
	<div id="paging" class="page"></div>
</body>
</html>