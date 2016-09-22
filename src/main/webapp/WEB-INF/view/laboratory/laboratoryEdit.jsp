<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<html>
<head>
<title>编辑实验室信息</title>

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
			url:'${ctx}/user/org/getTreeDate.do',
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
</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${laboratory.id == null }">
			<legend class="legend">添加实验室信息</legend>
		</c:if>
		<c:if test="${laboratory.id != null }">
			<legend class="legend">修改【${laboratory.name }】信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${laboratory.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">实验室名称:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name"  validate="{required:true,messages:{required:'实验室名称不能为空！'}}" value="${laboratory.name }" /></td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">实验室所属:</td>
                <td align="left" class="left">
                <input type="hidden" name="postId" id="postId" value="${laboratory.post.id }">
				<input id="postName" type="text" readonly value="${laboratory.post.name }"  
							 onclick="showMenu(); return false;"  validate="{required:true,messages:{required:'请选择归属！'}}"/> </td>             
            </tr>  
            <tr>
                <td align="right" class="tabRight">创建时间:</td>
                <td align="left" class="left">
                <input name="bulidTime" type="text" id="bulidTime" value="${laboratory.bulidTime }" validate="{required:true,messages:{required:'请选择创建时间！'}}"/></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">负责人:</td>
                <td align="left" class="left">
                <input name="principalName" type="text" id="principalName" value="${laboratory.principal.name }" /></td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${laboratory.remark }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="保存" id="Button1" class="l-button l-button-submit"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>

    </form>
    <div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="postTree" class="ztree"></ul>
</div>
    
</body>
</html>
