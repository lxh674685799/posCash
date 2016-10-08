<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>编辑设备信息</title>

<script type="text/javascript">

$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			updatesum:{required:true,number:true}
		},
		messages:{
			updatesum:{required:"请输入入库存数量！",number:"入库存只能为数字！"}
		}
	});
});
</script>
</head>
<body>
    <form name="form" method="post" action="updateSum.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		
		<legend class="legend">【${goods.name }】商品入库</legend>
		
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${goods.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">商品名称:</td>
                <td align="left" class="left">
               ${goods.name }</td>               
            </tr>       
       
            
            <tr>
                <td align="right" class="tabRight">库存:</td>
                <td align="left" class="left">
               ${goods.sum}</td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">入库数量:</td>
                <td align="left" class="left">
                <input name="updatesum" type="text" id="updatesum"  /></td>               
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
