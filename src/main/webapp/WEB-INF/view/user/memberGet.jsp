<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<html>
<head>
<title>会员基本信息</title>
</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">【${member.name }】会员信息</legend>
	
		<!-- 保留字段 -->
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
         <tr>
                <td align="right" class="tabRight" width="20%">会员编号:</td>
                <td align="left" class="left">
                ${member.memberNo}</td>               
            </tr>              
             <tr>
                <td align="right" class="tabRight" width="20%">姓名:</td>
                <td align="left" class="left">
                ${member.name}</td>               
            </tr>          
            <tr>
                <td align="right" class="tabRight">联系电话:</td>
                <td align="left" class="left">
               ${member.phone }</td>               
            </tr> 
             <tr>
                 <td align="right" class="tabRight" valign="top">性别:</td>
                  <td align="left" class="left">
                   <c:if test="${member.sex}"><label for="rbtnl_0">男</label></c:if>
                     <c:if test="${!member.sex}"><label for="rbtnl_1">女</label></c:if>
                </td>           
            </tr>  
               <tr>
                <td align="right" class="tabRight">账户余额:</td>
                <td align="left" class="left">
                ${member.valueMnu }</td>               
            </tr>
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                ${member.remark }
                </td>
            </tr> 
            <tr align="center"><td colspan="2">
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>
    </form>
</body>
</html>
