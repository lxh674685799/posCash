<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>用户列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/user/user/edit.do";
}
</script>

<script type="text/javascript">
function loadzTree(){
	var setting = {
		data: {
			key : {			
				name: "name"
			},	
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: 100
			}
		},
		async: {
			enable: true,
			url:"${ctx}/device/type/getTreeDate.do",
			autoParam:["id"]
		},
		callback:{
			onClick: onClick
		}
	};
	$.fn.zTree.init($("#typeTree"), setting);

};
$(function(){

$("html").bind("mousedown", 
		function(event){
			if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
				hideMenu();
			}
		});
		
});

function showMenu() {
	var cityObj = $("#typeName");
	var cityOffset = $("#typeName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	loadzTree();
}
function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
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

</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">用户查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">姓名:</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${param.name }" /></td>
			<td width="15%" align="right">所在部门:</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="postName" id="postName" value="${param.postName }" /></td>
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
	<display:table name="users" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/user/user/list.do">
		
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
		
		<display:column title="姓名" style="text-align:left;"property="name">
		</display:column>
		<display:column title="性别" style="text-align:right;">
			 ${f.sex==true?"男":"女"}
		</display:column> 
		<display:column title="email" property="email" style="text-align:right;"></display:column> 
		<display:column title="联系电话" property="phone" style="text-align:right;"></display:column> 
		<display:column title="所在部门" property="post.name" style="text-align:right;"></display:column> 
		<display:column title="所属角色" property="role.name" style="text-align:right;"></display:column> 
		<display:column title="备注" property="remark" style="text-align:right;"></display:column> 
		<display:column title="操作">
			<a href="${ctx}/user/user/edit.do?id=${f.id}">修改</a>
		<c:if test="${f.id !='1000'}">
			<a href="${ctx}/user/user/del.do?id=${f.id}">删除</a>
		</c:if>
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