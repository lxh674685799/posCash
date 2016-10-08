<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>商品列表</title>

<script type="text/javascript">
var zTree;
function toAdd(){
   window.location.href="${ctx}/goods/goods/edit.do";
}
$(function(){
	
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
	
});

function loadzTree(){
	var setting = {
		data: {
			key : {			
				name: "name"
			},	
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId"
			}
		},
		async: {
			enable: true,
			url:'${ctx}/device/type/getTreeDate.do',
			autoParam:["id"]
		},
		callback:{
			onClick: onClick,
			beforeClick: beforeClick
		}
	};
	zTree = $.fn.zTree.init($("#typeTree"), setting);

};


function showMenu() {
	var cityObj = $("#typeName");
	var cityOffset = $("#typeName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	loadzTree();
}
function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function beforeClick(treeId, treeNode) {
	zTree.expandNode(treeNode);
	if (treeNode.isParent) {		
		return (treeNode.click = false);
	}
}

function onClick(event, treeId, treeNode) {
	if (treeNode) {		
		$("#typeId").attr("value", treeNode.id);
		$("#typeName").attr("value", treeNode.name);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('typeId').value = '';
	document.getElementById('typeName').value = '';
}

function exportExcel(){    
    location.href="${ctx}/goods/goods/excelExport.do";    
} 
</script>


<body>
<fieldset class="fieldset">
	<legend class="legend">商品查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">商品名称：</td>
			<td width="20%" align="left">
			<input type="text" class="text" name="name" id="name" value="${goods.name }" /></td>
			<td width="12%" align="right">商品类型：</td>
			<td width="20%" align="left">
				<input id="typeName" type="text" name="typename" readonly value="${goods.typeName}"  
							 onclick="showMenu(); return false;" /> 
				 <input type="hidden" name="typeId" id="typeId" value="${goods.typeId }"></td>  
			</td>
			<td width="12%" align="right">商品条码：</td>
			<td width="20%" align="left">
			<input type="text" class="text" name="code" id="code" value="${goods.code}" /></td>
	
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
		<input type="button" value="导出Excel" name="excel" class="btn_small" onclick="exportExcel()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="商品名称" style="text-align:left;"property="name">
		</display:column>
		<display:column title="商品条码" property="code" style="text-align:right;"></display:column> 
		 <display:column title="商品类型"  style="text-align:right;">
		 		${f.type.name}
		 </display:column> 
		<display:column title="供货商"  style="text-align:right;">
			<a href="${ctx}/device/factory/get.do?factoryId=${f.factoryId}">${f.factory.name}</a>
		</display:column>
		<display:column title="销售价"  style="text-align:right;">
				${f.inPrice}元
		</display:column>
		<display:column title="库存"  style="text-align:right;">
				${f.sum}件
		</display:column>
		 <%-- <display:column title="库存" property="inventory" style="text-align:right;"></display:column>  --%>
		<display:column title="操作">
			<a href="${ctx}/goods/goods/edit.do?id=${f.id}">修改</a>
			<a href="${ctx}/goods/goods/del.do?id=${f.id}">删除</a>
			<a href="${ctx}/goods/goods/get.do?id=${f.id}">详情</a>
			<a href="${ctx}/goods/goods/toUpdateSum.do?id=${f.id}">入库</a>
			
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>

<div id="DropdownMenuBackground" style="display:none; position:absolute; height:150px;width:15%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="typeTree" class="ztree"></ul>
</div>
</body>
</html>