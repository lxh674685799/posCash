<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>

<f:Const var="DEVICE_STATUS_NORMAL"/>

<html>
<head>
<title>设备借出信息</title>
</head>
<script type="text/javascript"  src="${ctx }/resources/js/SysDialog.js"></script>
<script type="text/javascript" src="${path }/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			deviceNumbers:{required:true},
			loanedUserName:{required:true},
			returnTime:{required:true},
			forWhat:{required:true},
			postName:{required:true}
		},
		messages:{
			deviceNumbers:{required:"请选择要借出的设备！"},
			loanedUserName:{required:"请选择借给人！"},
			returnTime:{required:"请选择归还时间！"},
			forWhat:{required:"请填写借出理由！"},
			postName:{required:"请选择借到单位！"}
		}
	});
	 
	 var returnTime = document.getElementById('returnTime');
	 returnTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});
	  }
});
//弹出组织框
function showOrgDialog(){
	var userIds = $("#loanedUserId").val();
	var names = $("#loanedUserName").val();
	UserDialog({callback:dlgUserCallBack,
					ids:userIds,
				  names:names,
			   isSingle:true,
			       path:'${ctx }'});
};

// 组织框返回数据   
function dlgUserCallBack(userIds, names){
	$("#loanedUserName").val(names);	
	$("#loanedUserId").val(userIds);	
};


//弹出组织框
function showOrgDeviceDialog(){
	var deviceIds = $("#deviceIds").val();
	var names = $("#deviceNumbers").val();
	OrgDeviceDialog({callback:dlgDeviceCallBack,
					ids:deviceIds,
				  names:names,
			   isSingle:false,
			   status: ${DEVICE_STATUS_NORMAL},
			       path:'${ctx }'});
};

// 组织框返回数据   
function dlgDeviceCallBack(userIds, names){
	$("#deviceNumbers").val(names);	
	$("#deviceIds").val(userIds);	
};

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
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${loan.id == null }">
			<legend class="legend">添加设备借出信</legend>
		</c:if>
		<c:if test="${loan.id != null }">
			<legend class="legend">修改设备借出信</legend>
		</c:if>
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${loan.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">借出设备:</td>
                <td align="left" class="left">
                <input name="deviceNumbers" type="text" id="deviceNumbers"  style="width: 50%"/><a href="javascript:showOrgDeviceDialog();">选择</a>
                <input type="hidden" name="deviceIds" id="deviceIds" value=""/>
                </td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">借给人:</td>
                <td align="left" class="left">
                <input name="loanedUserName" type="text" id="loanedUserName" value="${loan.loanedUser.name }"/><a href="javascript:showOrgDialog();">选择</a>
                <input type="hidden" name="loanedUserId" id="loanedUserId" value=""/>
                </td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">归还时间:</td>
                <td align="left" class="left">
                <input name="returnTime" type="text" id="returnTime"  value="${loan.returnTime }" /></td>               
            </tr> 
            <tr>
                <td align="right" class="tabRight">借出理由:</td>
                <td align="left" class="left">
                <input name="forWhat" type="text" id="forWhat"  value="${loan.forWhat }" /></td>               
            </tr> 
            <tr>
                <td align="right" class="tabRight">借到地点:</td>
                <td align="left" class="left">
                 <input name="postId" type="hidden" id="postId" value="${loan.post.id }">
                <input name="postName" type="text" id="postName" readonly
						 onclick="showMenu(); return false;" value="${loan.post.name }" /></td>               
            </tr>
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${loan.remark }</textarea>
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
