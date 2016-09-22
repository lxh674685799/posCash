<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<html>
<head>
<title>编辑设备信息</title>

<script type="text/javascript">

$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			name:{required:true},
			typeName:{required:true},
			factoryId:{required:true},
			//model:{required:true},
			code:{required:true,number:true},
			countryCode:{required:true,number:true}
		},
		messages:{
			name:{required:"请输入设备型号！"},
			typeName:{required:"请选择设备类别！"},
			factoryId:{required:"请选择设备厂家！"},
			//model:{required:"请输入设备型号！"},
			code:{required:"请输入设备条码！",number:"条码只能为数字！"},
			countryCode:{required:"请输入国别码！",number:"国别码只能为数字！"}
		}
	});
});

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

</script>
</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${device.id == null }">
			<legend class="legend">添加设备信息</legend>
		</c:if>
		<c:if test="${device.id != null }">
			<legend class="legend">修改【${device.name }】信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${device.id }">
         <input type="hidden" name="number" value="${device.number }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">设备型号:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name" value="${device.name }" /></td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">设备类型:</td>
                <td align="left" class="left">
				<input id="typeName" type="text" name="typeName" readonly value="${device.type.name }"  
							 onclick="showMenu(); return false;" /> 
				 <input type="hidden" name="typeId" id="typeId" value="${device.type.id }"></td>             
            </tr>  
             <tr>
                <td align="right" class="tabRight">设备厂家:</td>
                <td align="left" class="left">
               <select id="factoryId" name="factoryId">
				<option value="">--请选择设备厂家--</option>
				<c:forEach var="f" items="${factorys }">
				<option value="${f.id }" <c:if test="${device.factory.id==f.id  }">selected</c:if> >${f.name }</option>
				</c:forEach>
				</select>
                </td>        
            </tr> 
            <!-- 
            <tr>
                <td align="right" class="tabRight">设备型号:</td>
                <td align="left" class="left">
                <input name="model" type="text" id="model" value="${device.model }" /></td>               
            </tr>  -->
             <tr>
                <td align="right" class="tabRight">设备条码:</td>
                <td align="left" class="left">
                <input name="code" type="text" id="code" value="${device.code }" /></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">国别码:</td>
                <td align="left" class="left">
                <input name="countryCode" type="text" id="countryCode" value="${device.countryCode }" /></td>               
            </tr> 
            <tr>
                <td align="right" class="tabRight">规格:</td>
                <td align="left" class="left">
                <input name="spec" type="text" id="spec" value="${device.spec }" /></td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${device.remark }</textarea>
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
	<ul id="typeTree" class="ztree"></ul>
</div>
    
</body>
</html>
