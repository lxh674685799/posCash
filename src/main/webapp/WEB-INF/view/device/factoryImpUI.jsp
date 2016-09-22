<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>

<script type="text/javascript">
function downModel(){
   window.location.href="${ctx}/device/factory/down.do";
}
</script>

<title>设备厂家信息导入</title>

<script type="text/javascript">
$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			impFile:{required:true}
		},
		messages:{
			impFile:{required:"请选择导入文件！"}
		}
	});
});
</script>
</head>
<body>
    <form name="factoryImpForm" method="post" action="imp.do" id="factoryImpForm" enctype="multipart/form-data">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<legend class="legend">导入厂家信息</legend>
        <table cellpadding="0" cellspacing="0" class="table_add" >      
            <tr>
                <td align="right" class="tabRight">选择导入文件:</td>
                <td align="left" class="left">
                <input name="impFile" type="file" id="impFile"  /></td>               
            </tr>  
            <tr align="center"><td colspan="2">
             <input type="button" value="模版下载" id="down" name="down" class="l-button l-button-submit" onclick="downModel()"/>
            <input type="submit" value="导入" id="imp" class="l-button l-button-submit"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>

    </form>
</body>
</html>
