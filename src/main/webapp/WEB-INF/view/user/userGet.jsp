<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<html>
<head>
<title>用户信息</title>
</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">【${user.name }】用户信息</legend>
	
		<!-- 保留字段 -->
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >             
             <tr>
                <td align="right" class="tabRight" width="20%">姓名:</td>
                <td align="left" class="left">
                ${user.name}</td>               
            </tr>          
            <tr>
                <td align="right" class="tabRight">联系电话:</td>
                <td align="left" class="left">
               ${user.phone }</td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">email:</td>
                <td align="left" class="left">
                ${user.email }</td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">所属部门：</td>
                <td align="left" class="left">
               ${user.post.name }</td>               
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
