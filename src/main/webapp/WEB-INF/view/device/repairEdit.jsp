<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>设备维修信息</title>
<f:Const var="DEVICE_STATUS_REPAIR"/>
</head>
<script type="text/javascript"  src="${ctx }/resources/js/SysDialog.js"></script>
<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			deviceNumbers:{required:true},
			repairUserName:{required:true}
		},
		messages:{
			deviceNumbers:{required:"请选择需要维修的设备！"},
			repairUserName:{required:"请分配维修人员！"}
		}
	});
});

//弹出组织框
function showOrgDialog(){
	var userIds = $("#repairUserId").val();
	var names = $("#repairUserName").val();
	UserDialog({callback:dlgUserCallBack,
					ids:userIds,
				  names:names,
			   isSingle:true,
			       path:'${ctx }'});
};

// 组织框返回数据   
function dlgUserCallBack(userIds, names){
	$("#repairUserName").val(names);	
	$("#repairUserId").val(userIds);	
}


//弹出组织框
function showOrgDeviceDialog(){
	var deviceIds = $("#deviceIds").val();
	var names = $("#deviceNumbers").val();
	OrgDeviceDialog({callback:dlgDeviceCallBack,
					ids:deviceIds,
				  names:names,
			   isSingle:false,
			   status: ${DEVICE_STATUS_REPAIR},
			       path:'${ctx }'});
};

// 组织框返回数据   
function dlgDeviceCallBack(userIds, names){
	$("#deviceNumbers").val(names);	
	$("#deviceIds").val(userIds);	
}
</script>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${repair.id == null }">
			<legend class="legend">添加设备维修信息</legend>
		</c:if>
		<c:if test="${repair.id != null }">
			<legend class="legend">修改设备维修信息</legend>
		</c:if>
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${repair.id }">
         <input type="hidden" name="sendDate" value="${repair.sendDate }">
         <input type="hidden" name="sendUserId" value="${repair.sendUserId }">
         <input type="hidden" name="repairStatus" value="${repair.repairStatus }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">需要维修设备:</td>
                <td align="left" class="left">
                 <input name="deviceNumbers" type="text" id="deviceNumbers" value="${repair.device.number }" style="width: 50%"/><a href="javascript:showOrgDeviceDialog();">选择</a>
               	 <input type="hidden" name="deviceIds" id="deviceIds" value=""/>
                </td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">分配维修人员:</td>
                <td align="left" class="left">
                <input name="repairUserName" type="text" id="repairUserName" value="${repair.repairUser.name }"/><a href="javascript:showOrgDialog();">选择</a>
                <input type="hidden" name="repairUserId" id="repairUserId" value=""/>            
            </tr>  
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="address" name="address" style="width:400px">${repair.remark }</textarea>
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
</body>
</html>
