<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>设备发放信息列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="edit.do";
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">设备发放信息查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">发放人：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${param.name }" /></td>
			<td width="15%" align="right">接收人：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="linkName" id="linkName" value="${param.linkName }" /></td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>	
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="devices" uid="f" cellpadding="0" class="blues"
		cellspacing="0">	
		<display:caption class="tooltar_btn">
		<input type="button" value="添 加" name="add" class="btn_small" onclick="toAdd()" />
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
			
		<display:column title="发放人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.sendUserId}">${f.sendUser.name}</a>
		</display:column> 
		<display:column title="接收人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.getUserId}">${f.getUser.name}</a>
		</display:column> 
		<display:column title="发放时间" property="getDate" style="text-align:right;"></display:column> 
		<display:column title="操作">
			<a href="get.do?id=${f.id}">详情</a>
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>