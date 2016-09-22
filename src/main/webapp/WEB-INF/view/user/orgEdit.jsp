<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>编辑组织机构信息</title>
</head>

<script post="text/javascript">
$(function(){
	<%
	if(_obj_!=null){
	%>
	window.parent.loadTree();
	<%}%>
});
</script>

<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${post.id == null }">
		<legend class="legend">添加组织机构信息</legend>
		</c:if>
		<c:if test="${post.id != null }">
			<legend class="legend">修改【${post.name }】信息</legend>
		</c:if>
		
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${post.id }">
         <input type="hidden" name="isParent" value="${post.isParent }">
         <input type="hidden" name="parentId" value="${post.parentId }">
         <input type="hidden" name="path" value="${post.path }">
       
        <table cellpadding="0" cellspacing="0" class="table_add" >
         <tr>
                <td align="right" class="tabRight">机构代码:</td>
                <td align="left" class="left">
                <input name="code" type="text" id="code" ltype="text" validate="{required:true}" value="${post.code }" /></td>               
            </tr>
            <tr>
                <td align="right" class="tabRight">名称:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name" ltype="text" validate="{required:true}" value="${post.name }" /></td>               
            </tr>
            <tr>
                <td align="right" class="tabRight">负责人:</td>
                <td align="left" class="left">
                <input name="user" type="text" id="user" ltype="text"  value="${post.user }" /></td>               
            </tr>
            <tr>
                <td align="right" class="tabRight">联系电话:</td>
                <td align="left" class="left">
                <input name="phone" type="text" id="phone" ltype="text" validate="{phone:true}" value="${post.phone }" /></td>               
            </tr>
             <tr>
                <td align="right" class="tabRight">地址:</td>
                <td align="left" class="left">
                <input name="address" type="text" id="address" ltype="text" value="${post.address }" /></td>               
            </tr>
            <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${post.remark }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="保存" id="save" class="l-button l-button-submit"/> 
			</td>
            </tr>      
        </table>
        </fieldset>
    </form>
</body>
</html>
