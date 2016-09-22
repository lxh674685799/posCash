<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<f:Const var="LABORATORY_LOAN"/>
<f:Const var="LABORATORY_NORMAL"/>

<html>
<head>
<title>实验室列表</title>


<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/laboratory/laboratory/edit.do";
}
</script>

<script post="text/javascript">
var zTree;
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
			url:'${ctx}/user/post/getTreeDate.do',
			autoParam:["id"]
		},
		callback:{
			onClick: onClick,
			beforeClick: beforeClick
		}
	};
	zTree = $.fn.zTree.init($("#postTree"), setting);

};
$(function(){
$("html").bind("mousedown", 
		function(event){
			if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
				hideMenu();
			}
		});
		//
$("#bulidTime").focus(function(){
	WdatePicker({dateFmt:'yyyy-MM-dd'});
});
});

function showMenu() {
	var cityObj = $("#postName");
	var cityOffset = $("#postName").offset();
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
		$("#postId").attr("value", treeNode.id);
		$("#postName").attr("value", treeNode.name);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('postId').value = '';
	document.getElementById('postName').value = '';
}

</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">实验室查询</legend>
<form action="list.do" method="post" id="searchform"  name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">实验室名称</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${laboratory.name }" /></td>	
			<td width="15%" align="right">实验室归属</td>
			<td width="25%" align="left">
			<input type="hidden" name="postId" id="postId" >
			<input id="postName" name="postName" type="text" readonly value="${laboratory.postName }"  
							 onclick="showMenu(); return false;" /> </td> 
			
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="laboratorys" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/laboratory/laboratory/list.do">
		
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
		
		<display:column title="实验室名称" style="text-align:left;"property="name"></display:column>
		<display:column title="实验室归属"  style="text-align:right;"property="post.name"></display:column> 
		<display:column title="创建时间" property="bulidTime" style="text-align:right;"></display:column> 
		<display:column title="负责人" property="principal.name" style="text-align:right;"></display:column> 
		<display:column title="状态"style="text-align:right;">
		<c:if test="${f.status == LABORATORY_LOAN}"> 借出</c:if>
		<c:if test="${f.status == LABORATORY_NORMAL}"> 正常</c:if>
		</display:column> 
		
		<display:column title="操作">
			<a href="${ctx}/laboratory/laboratory/edit.do?id=${f.id}">修改</a>
			<a href="${ctx}/laboratory/laboratory/del.do?id=${f.id}">删除</a>
			<c:if test="${f.status == LABORATORY_LOAN}"><a href="${ctx}/laboratory/laboratory/rebark.do?id=${f.id}">归还</a></c:if>
			<c:if test="${f.status == LABORATORY_NORMAL}"> 	<a href="${ctx}/laboratory/laboratory/loan.do?id=${f.id}">占用</a></c:if>
		
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
    <div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="postTree" class="ztree"></ul>
</div>
    
</html>