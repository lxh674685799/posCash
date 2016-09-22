<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>编辑系统角色信息</title>

</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${role.id == null }">
			<legend class="legend">添加系统角色</legend>
		</c:if>
		<c:if test="${role.id != null }">
			<legend class="legend">修改【${role.name }】角色信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${role.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
             <tr>
                <td align="right" class="tabRight">名称:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name"  validate="{required:true}" value="${role.name}"/></td>               
            </tr>   
            <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${role.remark }</textarea>
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
