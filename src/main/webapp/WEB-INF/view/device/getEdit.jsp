<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>

<f:Const var="DEVICE_STATUS_NORMAL"/>
<html>
<head>
<title>设备发放信息</title>
</head>
<script type="text/javascript"  src="${ctx }/resources/js/SysDialog.js"></script>
<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			deviceNumbers:{required:true},
			getUserName:{required:true},
			useFor:{required:true}
		},
		messages:{
			deviceNumbers:{required:"请选择要发放的设备！"},
			getUserName:{required:"请选择发给人！"},
			useFor:{required:"请输填写发放理由！"}
		}
	});
});
//弹出组织框
function showOrgDialog(){
	var userIds = $("#getUserId").val();
	var names = $("#getUserName").val();
	UserDialog({callback:dlgUserCallBack,
					ids:userIds,
				  names:names,
			   isSingle:true,
			       path:'${ctx }'});
};
//组织框返回数据   
function dlgUserCallBack(userIds, names){
	$("#getUserName").val(names);	
	$("#getUserId").val(userIds);	
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
//组织框返回数据   
function dlgDeviceCallBack(userIds, names){
	$("#deviceNumbers").val(names);	
	$("#deviceIds").val(userIds);	
};
</script>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">设备发放信息</legend>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${loan.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">发放设备:</td>
                <td align="left" class="left">
                 <input name="deviceNumbers" type="text" id="deviceNumbers"  style="width: 50%"/><a href="javascript:showOrgDeviceDialog();">选择</a>
                <input type="hidden" name="deviceIds" id="deviceIds" value=""/></td> 
            </tr>       
            <tr>
                <td align="right" class="tabRight">接收人:</td>
                <td align="left" class="left">
                   <input name="getUserName" type="text" id="getUserName" value="${loan.loanedUser.name }"/><a href="javascript:showOrgDialog();">选择</a>
                <input type="hidden" name="getUserId" id="getUserId" value=""/></td>               
            </tr>  
            <tr>
                <td align="right" class="tabRight">用途:</td>
                <td align="left" class="left">
                <input name="useFor" type="text" id="useFor"  value="${getDevice.useFor }" /></td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${getDevice.remark }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="保存" id="Button1" class="l-button l-button-submit"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
        </fieldset>
    </form>
</body>
</html>
