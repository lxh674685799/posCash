<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<html>
<head>
<title>编辑会员信息</title>
<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			memberNo:{required:true},
			name:{required:true},
			phone:{required:true,isTel:true},
			valueMnu:{number:true},
		},
		messages:{
			memberNo:{required:"请输入会员编号！"},
			name:{required:"请输入真实姓名！"},
			phone:{required:"请输入联系电话！",isTel:"请输入正确号码！"},
			valueMnu:{number:"请输入正确数字"},
		}
	});
});
</script>

</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${member.id == null }">
			<legend class="legend">添加会员信息</legend>
		</c:if>
		<c:if test="${member.id != null }">
			<legend class="legend">修改【${member.name }】会员信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${member.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            
              <tr>
                <td align="right" class="tabRight">会员编号:</td>
                <td align="left" class="left">
                <input name="memberNo" type="text" id="memberNo"  value="${member.memberNo }"<c:if test="${member.id != null }">
			readonly="readonly"
		</c:if> /><font color="red">*必填</font></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">真实姓名:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name" value="${member.name}"/><font color="red">*必填</font></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight" valign="top">性别:</td>
                  <td align="left" class="left">
                    <input id="sex_man" type="radio" name="sex" value="true" <c:if test="${member.sex}">checked="checked"</c:if> /><label for="rbtnl_0">男</label> &nbsp;&nbsp;&nbsp;&nbsp;
                    <input id="sex_woman" type="radio" name="sex" value="false"  <c:if test="${!member.sex}">checked="checked"</c:if>/><label for="rbtnl_1">女</label>
                </td>
            </tr>
            <tr>
                <td align="right" class="tabRight">联系电话:</td>
                <td align="left" class="left">
                <input name="phone" type="text" id="phone" value="${member.phone }" /><font color="red">*必填</font></td>               
            </tr>
             <tr>
                <td align="right" class="tabRight">充值金额:</td>
                <td align="left" class="left">
                <input name="valueMnu" type="text" id="valueMnu" value="${member.valueMnu }" /></td>               
            </tr>
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${member.remark }</textarea>
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
