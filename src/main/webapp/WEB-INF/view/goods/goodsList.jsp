<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>设备列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/goods/goods/edit.do";
}
</script>


<body>
<fieldset class="fieldset">
	<legend class="legend">设备查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">商品名称：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${goods.name }" /></td>
			<td width="15%" align="right">商品编码：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${goods.code}" /></td>
	
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="Goods" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/goods/goods/list.do">
		
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" name="add" class="btn_small"
				onclick="toAdd()" />
		<input type="button" value="删除" name="del" class="btn_small" onclick="mycheckbox()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="商品名称" style="text-align:left;"property="name">
		</display:column>
		<display:column title="设备条码" property="code" style="text-align:right;"></display:column> 
		<display:column title="商品类型" property="f.type.name" style="text-align:right;"></display:column> 
		<display:column title="国别码" property="countryCode" style="text-align:right;"></display:column>
		<display:column title="供货商"  style="text-align:right;">
			<a href="${ctx}/device/factory/get.do?factoryId=${f.factoryId}">${f.factory.name}</a>
		</display:column>
		 <display:column title="库存" property="inventory" style="text-align:right;"></display:column> 
		<display:column title="操作">
			<a href="${ctx}/goods/goods/edit.do?id=${f.id}">修改</a>
			<a href="${ctx}/goods/goods/del.do?id=${f.id}">删除</a>
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>

<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="typeTree" class="ztree"></ul>
</div>
</body>
</html>