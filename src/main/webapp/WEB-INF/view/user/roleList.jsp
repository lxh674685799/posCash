<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>系统角色列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/user/role/edit.do";
}

function showSetAuth(roleId){
	$.ligerDialog.open({ url: '${ctx}/resource/resource/getByRole.do?roleId='+roleId, height: 500,width: null,title:"角色分配权限",modal:false });
}

</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">角色查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">角色名称：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${param.name }" /></td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
	<input type="hidden" name="totalCount" value="${page.totalCount}">
	<input type="hidden" name="page" value="${page.pageIndex}">
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="roles" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/user/role/list.do">
		
		<display:caption class="tooltar_btn">
		<input type="button" value="添 加" name="add" class="btn_small" onclick="toAdd()" />
		</display:caption>
		
		<display:column title="名称" style="text-align:left;"property="name"></display:column>
		<display:column title="备注" property="remark" style="text-align:right;"></display:column> 
		<display:column title="操作">
		<c:if test="${f.id !='1000'}">
			<a href="${ctx}/user/role/edit.do?id=${f.id}">修改</a>
			<a href="${ctx}/user/role/del.do?id=${f.id}">删除</a>
			
		</c:if><a href="javascript:showSetAuth('${f.id }');">设置权限</a>
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>