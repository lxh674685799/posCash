<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>


<f:Const var="USER_MESSAGE_TASK"/>
<f:Const var="USER_MESSAGE_INFO"/>

<html>
<head>
<title>消息回复</title>
</head>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript"  src="${ctx }/resources/js/SysDialog.js"></script>
<script type="text/javascript">

$(function() {
	var editor = new UE.ui.Editor();
	editor.render("txt_html"); 
	
	$("#send").click(function() {
		var data = editor.getContent();
		if(data.length == 0) {
			$.ligerMessageBox.warn("提示信息","内容不能为空！");
			return false ;
		}
		$('#content').val(data);
		$('#form').submit(); 
	});
	
   });

$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			subject:{required:true}
		},
		messages:{
			subject:{required:"请填写信息主题！"}
		}
	});
	 
});

</script>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">消息回复</legend>
			
		  <input type="hidden" name="id" value="">
		   <input type="hidden" name="type" value="${USER_MESSAGE_INFO }">
		
        <table cellpadding="0" cellspacing="0" class="table_add" >
           <tr>
                <td align="right" class="tabRight">收信人:</td>
                <td align="left" class="left">
             	 <input name="toUserName" type="text" id="toUserName" value="${message.fromUserName }" disabled/>
                <input type="hidden" name="toUserIds" id="toUserIds" value="${message.fromUserId }"/>
            </tr> 
            <tr>
                <td align="right" class="tabRight" width="20%">消息主题:</td>
                <td align="left" class="left" width="80%" >
            	<input name="subject" id="subject" type="text"/><font color="red">*必选</font>
				</td>               
            </tr>    
            <tr>
                <td align="right" class="tabRight">内容:</td>
                <td align="left" class="left" width="80%" >
             	 <textarea id="content" name="content" style="display: none;"></textarea>	
				<textarea id="txt_html" ></textarea>		   
            </tr> 
                  
            <tr align="center"><td colspan="2">
            <input type="button" value="发送" id="send" name="send" class="l-button l-button-submit" onclick="send()"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>

    </form>
</body>
</html>
