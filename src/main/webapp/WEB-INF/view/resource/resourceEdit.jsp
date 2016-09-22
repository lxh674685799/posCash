<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>编辑系统资源信息</title>
</head>

<script type="text/javascript">
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
		<c:if test="${resource.id == null }">
		<legend class="legend">添加系统资源信息</legend>
		</c:if>
		<c:if test="${resource.id != null }">
			<legend class="legend">修改【${resource.title }】资源信息</legend>
		</c:if>
		
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${resource.id }">
         <input type="hidden" name="isParent" value="${resource.isParent }">
         <input type="hidden" name="parentId" value="${resource.parentId }">
       
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">名称:</td>
                <td align="left" class="left">
                <input name="title" type="text" id="title" ltype="text" validate="{required:true,messages:{required:'请输入资源名称！'}}" value="${resource.title }" /></td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">URL:</td>
                <td align="left" class="left">
                <input name="uri" type="text" id="uri" ltype="text" value="${resource.uri }" /></td>               
            </tr>  
              <tr>
                <td align="right" class="tabRight">描述:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="desc" name="desc" style="width:400px">${resource.desc }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="保存" id="Button1" class="l-button l-button-submit"/> 
			</td>
            </tr>      
        </table>
        </fieldset>
    </form>
</body>
</html>
