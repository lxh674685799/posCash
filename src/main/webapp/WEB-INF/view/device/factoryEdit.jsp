<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>编辑供货商信息</title>

<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			name:{required:true},
			/* phone:{isTel:true}, */
			mobile:{required:true,isTel:true},
			address:{maxlength:50}
		},
		messages:{
			name:{required:"请输入供货商名称！"},
			/* phone:{isTel:"电话号码格式错误！"}, */
			mobile:{required:"请输入联系人手机号！",isTel:"手机号格式错误！"},
			address:{maxlength:"地址长度要求小于50个字符！"}
		}
	});
});

</script>

</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${factory.id == null }">
			<legend class="legend">添加供货商信息</legend>
		</c:if>
		<c:if test="${factory.id != null }">
			<legend class="legend">修改【${factory.name }】供货商信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${factory.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">名称:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name" value="${factory.name }" /></td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">固话:</td>
                <td align="left" class="left">
                <input name="phone" type="text" id="phone"  value="${factory.phone }"/></td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">联系人姓名:</td>
                <td align="left" class="left">
                <input name="linkName" type="text" id="linkName"  value="${factory.linkName }"/></td>               
            </tr> 
            <tr>
                <td align="right" class="tabRight">联系人手机号:</td>
                <td align="left" class="left">
                <input name="mobile" type="text" id="mobile"  value="${factory.mobile }" /></td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">地址:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="address" name="address" style="width:400px">${factory.address }</textarea>
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
