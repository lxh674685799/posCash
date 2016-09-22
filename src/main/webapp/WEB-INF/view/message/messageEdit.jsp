<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>


<f:Const var="USER_MESSAGE_TASK"/>
<f:Const var="USER_MESSAGE_INFO"/>

<html>
<head>
<title>发送消息</title>
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
			subject:{required:true},
			toUserNames:{required:true}
		},
		messages:{
			subject:{required:"请填写信息主题！"},
			toUserNames:{required:"请选择收信人！"}
		}
	});
	 
});
//弹出组织框
function showOrgDialog(){
	var toUserIds = $("#toUserIds").val();
	var toUserNames = $("#toUserNames").val();
	UserDialog({callback:dlgUserCallBack,
					ids:toUserIds,
				  names:toUserNames,
			   isSingle:false,
			       path:'${ctx }'});
};

//组织框返回数据   
function dlgUserCallBack(userIds, names){
	$("#toUserNames").val(names);	
	$("#toUserIds").val(userIds);	
};
   

</script>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">发送系统消息</legend>
			
		  <input type="hidden" name="id" value="${innerMessage.id }">
		   <input type="hidden" name="type" value="${USER_MESSAGE_INFO }">
		
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight" width="20%">消息主题:</td>
                <td align="left" class="left" width="80%" >
            	<input name="subject" id="subject" value="${innerMessage.subject }" type="text"/><font color="red">*必选</font>
				</td>               
            </tr>    
           <!--  <tr>
                <td align="right" class="tabRight">消息类型:</td>
                <td align="left" class="left">
            	<select name="type" id="type" >
            	<option value="${USER_MESSAGE_INFO }" >普通消息</option>
            	<option value="${USER_MESSAGE_TASK }" <c:if test="${USER_MESSAGE_TASK == innerMessage.type }">select</c:if> >分配任务</option>
            	</select>
				</td>               
            </tr>    -->
            
             <tr>
                <td align="right" class="tabRight">接收人:</td>
                <td align="left" class="left">
             	 <input name="toUserNames" type="text" id="toUserNames"/><a href="javascript:showOrgDialog();">选择</a>
                <input type="hidden" name="toUserIds" id="toUserIds" value=""/>
            </tr> 
            <tr>
                <td align="right" class="tabRight">内容:</td>
                <td align="left" class="left" width="80%" >
             	 <textarea id="content" name="content" style="display: none;"></textarea>	
				<textarea id="txt_html" >${lawsInfo.content}</textarea>		   
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
