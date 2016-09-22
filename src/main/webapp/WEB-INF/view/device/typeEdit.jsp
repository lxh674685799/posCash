<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>编辑设备类型信息</title>
</head>

<script type="text/javascript">
$(function(){
	<%
	if(_obj_!=null){
	%>
	window.parent.loadTree();
	<%}%>
});

$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			classNumber:{required:true},
			name:{required:true}
		},
		messages:{
			classNumber:{required:"请填写分类号!"},
			name:{required:"请输入分类名称！"}
		}
	});
});
</script>

<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${type.id == null }">
		<legend class="legend">添加设备类型信息</legend>
		</c:if>
		<c:if test="${type.id != null }">
			<legend class="legend">修改【${type.name }】信息</legend>
		</c:if>
		
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${type.id }">
         <input type="hidden" name="isParent" value="${type.isParent }">
         <input type="hidden" name="parentId" value="${type.parentId }">
         <input type="hidden" name="path" value="${type.path }">
       
        <table cellpadding="0" cellspacing="0" class="table_add" >
        <tr>
                <td align="right" class="tabRight">设备分类号:</td>
                <td align="left" class="left">
                <input name="classNumber" type="text" id="classNumber"  value="${type.classNumber }" /></td>               
            </tr>  
            <tr>
                <td align="right" class="tabRight">设备类型名称:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name"  value="${type.name }" /></td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${type.remark }</textarea>
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
