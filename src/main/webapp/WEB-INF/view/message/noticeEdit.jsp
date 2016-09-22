<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>

<html>
<head>
<title>通知公告</title>
</head>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript">
 
$(function() {
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			subject:{required:true},
		},
		messages:{
			subject:{required:"请填写通知公告主题！"},	
		}
	});
	
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
   

</script>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${notice.id == null }">
			<legend class="legend">发布通知公告</legend>
		</c:if>
		<c:if test="${notice.id != null }">
			<legend class="legend">修改通知公告</legend>
		</c:if>
		
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${notice.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         <input type="hidden" name="createUserId" value="${notice.createUserId}">
         <input type="hidden" name="createDate" value="${notice.createDate}">
          <input type="hidden" name="createUserName" value="${notice.createUserName}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight" width="20%">通知公告主题:</td>
                <td align="left" class="left" width="80%" >
            	<input name="subject" id="subject" value="${notice.subject }" type="text" /><font color="red">*必填</font>
				</td>               
            </tr>        
            <tr>
                <td align="right" class="tabRight">内容:</td>
                <td align="left" class="left" width="80%" >
             	 <textarea id="content" name="content" style="display: none;"></textarea>	
				<textarea id="txt_html" >${notice.content}</textarea>		   
            </tr>         
            <tr align="center"><td colspan="2">
            <input type="button" value="发布" id="send" name="send" class="l-button l-button-submit" onclick="send()"/> 
			<input type="button" value="取消" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>

    </form>
</body>
</html>
