<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>

<html>
<head>
<title>设备购入申请</title>
</head>
<script type="text/javascript"  src="${ctx }/resources/js/SysDialog.js"></script>
<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			deviceId:{required:true},
			number:{required:true,number:true},
			price:{required:true,number:true},
			ratifyUserName:{required:true},
			file:{isUploadFileExt:true}
		},
		messages:{
			deviceId:{required:"请选择要购买的设备！"},
			number:{required:"请输入购买数量！",number:"请输入数字！"},
			price:{required:"请输入购买价格！",number:"请输入数字！"},
			ratifyUserName:{required:"请选择审批人！"},
			file:{isUploadFileExt:"请选择一下格式文件：'.doc','.xls','.ppt','.pdf','.txt'！"}
		}
	});
});

//弹出组织框
function showOrgDialog(){
	var userIds = $("#userIds").val();
	var names = $("#ratifyUserName").val();
	UserDialog({callback:dlgOrgCallBack,
					ids:userIds,
				  names:names,
			   isSingle:true,
			       path:'${ctx }'});
};

// 组织框返回数据   
function dlgOrgCallBack(userIds, names){
	$("#ratifyUserName").val(names);	
	$("#userIds").val(userIds);	
}

</script>

<body>
    <form name="form" method="post" action="save.do" id="form" enctype="multipart/form-data">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${device.id == null }">
			<legend class="legend">添加设备购入申请</legend>
		</c:if>
		<c:if test="${device.id != null }">
			<legend class="legend">修改购入申请信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${device.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         <input type="hidden" name="status" value="${device.status}">
         <input type="hidden" name="buyUserId" value="${device.buyUserId}">
         <input type="hidden" name="ratifyTime" value="${device.ratifyTime}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">设备名称:</td>
                <td align="left" class="left">
               <select id="deviceId" name="deviceId">
				<option value=>--请选择购入设备--</option>
				<c:forEach var="d" items="${devices}">
				<option value="${ d.id }"
					<c:if test="${d.id==device.deviceId }">selected</c:if>>${d.name}</option>
					</c:forEach>
				</select>
				</td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">购入数量:</td>
                <td align="left" class="left">
               <input name="number" type="text" id="number" ltype="text"  /></td>
            </tr>  
             <tr>
                <td align="right" class="tabRight">购入价格（元）:</td>
                <td align="left" class="left">
               <input name="price" type="text" id="price" ltype="text" /></td>            
            </tr> 
             <tr>
                <td align="right" class="tabRight">附件金额:</td>
                <td align="left" class="left">
               <input name="amount" type="text" id="amount" ltype="text" /></td>            
            </tr> 
             <tr>
                <td align="right" class="tabRight">经费来源:</td>
                <td align="left" class="left">
               <input name="financialResources" type="text" id="financialResources" ltype="text" /></td>            
            </tr> 
            <tr>
                <td align="right" class="tabRight">仪器来源:</td>
                <td align="left" class="left">
               <input name="deviceResource" type="text" id="deviceResource" ltype="text" /></td>            
            </tr> 
            <tr>
                <td align="right" class="tabRight"><a href="javascript:showOrgDialog();">选择审批用户</a>:</td>
                <td align="left" class="left">
                <input name="ratifyUserName" type="text" id="ratifyUserName" ltype="text" />
					  <input type="hidden" name="userIds" id="userIds" value=""/>
                </td>               
            </tr> 
            <c:if test="${device.id != null }">
			 <tr>
                <td align="right" class="tabRight">审批时间:</td>
                <td align="left" class="left">
               ${device.ratifyTime }</td>               
            </tr> 
			</c:if>
             <tr>
                <td align="right" class="tabRight">附件:</td>
                <td align="left" class="left">
                <input name="file" type="file" id="file" ltype="text" /></td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${device.remark }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="申请" id="Button1" class="l-button l-button-submit"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br/>	
        </fieldset>

    </form>
</body>
</html>
