<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>商品厂家列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/device/factory/edit.do";
}
function exp(){
	window.location.href="${ctx}/device/factory/exp.do";
	}
function imp(){
    window.location.href="${ctx}/device/factory/impUI.do";
	}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">供货商查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">供货商名称：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${param.name }" /></td>
			<td width="15%" align="right">联系人：</td>
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
	<display:table name="factorys" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/device/factory/list.do">	
		<display:caption class="tooltar_btn">
		<input type="button" value="添 加" name="add" class="btn_small" onclick="toAdd()" />
		<input type="button" value="删除" name="toDel" class="btn_small" onclick="mycheckbox()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="供货商名称" style="text-align:left;"property="name"></display:column>
		<display:column title="电话" property="phone" style="text-align:right;"></display:column> 
		<display:column title="联系人" property="linkName" style="text-align:right;"></display:column> 
		<display:column title="联系人电话" property="mobile" style="text-align:right;"></display:column> 
		<display:column title="地址" property="address" style="text-align:right;" maxLength="20"></display:column> 
		<display:column title="操作">
			<a href="${ctx}/device/factory/edit.do?id=${f.id}">修改</a>
			<a href="${ctx}/device/factory/del.do?id=${f.id}">删除</a>
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>