<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<html>
<head>
<title>会员充值</title>
<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			mun:{required:true,number:true},
		},
		messages:{
			mun:{required:"请输入充值金额",number:"请输入正确数字"},
		}
	});
});
</script>

</head>
<body>
    <form name="form" method="post" action="topUp.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">【${member.name }】会员充值</legend>
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${member.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            
              <tr>
                <td align="right" class="tabRight">会员编号:</td>
                <td align="left" class="left">
                ${member.memberNo }</td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">真实姓名:</td>
                <td align="left" class="left">
                ${member.name}</td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight" valign="top">性别:</td>
                  <td align="left" class="left">
                     <c:if test="${member.sex}">男</c:if>
                      <c:if test="${!member.sex}">女</c:if>
                </td>
            </tr>
            <tr>
                <td align="right" class="tabRight">联系电话:</td>
                <td align="left" class="left">
                ${member.phone }</td>               
            </tr>
             <tr>
                <td align="right" class="tabRight">现有金额:</td>
                <td align="left" class="left">
                ${member.valueMnu }</td>               
            </tr>
             <tr>
                <td align="right" class="tabRight">充值金额:</td>
                <td align="left" class="left">
                <input name="mun" type="text" id="mun"/></td>               
            </tr>
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                ${member.remark }
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="充值" id="Button1" class="l-button l-button-submit"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>
    </form>

</body>
</html>

