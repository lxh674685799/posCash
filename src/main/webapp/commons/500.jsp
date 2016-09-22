<%@page import="com.soft.core.util.RequestUtil" isErrorPage="true" pageEncoding="UTF-8"%>
<%
	String basePath=request.getContextPath();
	String errorUrl=RequestUtil.getErrorUrl(request);
	
%>
<html>
	<head>
		<title>页面出错了</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<link  rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/web.css" />
		
		<f:link href="web.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
		<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
		<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
		<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/ksource/displaytag.js" ></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
			<style type="text/css">
			<!--
			.STYLE10 {
				font-family: "黑体";
				font-size: 36px;
			}
			-->  
			</style>
			<script type="text/javascript">
				function sendErrorMsg(){
					var url="<%=basePath%>/platform/mail/outMail/sendError.ht";
					var errorMsg=$("#errorMsg").val();
					var errorUrl=$("#errorUrl").val();
					var param={errorUrl:errorUrl,errorMsg:errorMsg};
					$.ligerDialog.waitting("正在发送,请您耐心等待...");
					$.post(url,param,function(data){
						$.ligerDialog.closeWaitting();
						var obj=new com.ksource.form.ResultMessage(data);
						if(obj.isSuccess()){
							$.ligerMessageBox.success("提示信息",obj.getMessage(),function(rtn){
								
							});
						}else{
							$.ligerDialog.err('提示信息',"页面出错了",obj.getMessage());
						}
					});
				}
			</script>
	</head>
	<body>
	 <table width="510" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
    	<td><img src="<%=basePath%>/commons/image/error/error_top.jpg" width="510" height="80" /></td>
  	  </tr>
	  <tr>
	    <td height="200" align="center" valign="top" background="<%=basePath%>/commons/image/error/error_bg.jpg">
	    	<table width="80%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="34%" align="right"><img src="<%=basePath%>/commons/image/error/error.gif" width="128" height="128"></td>
	          <td width="66%" valign="top" align="center">
	          	<table width="100%">
	          		<tr height="25">
	          			<td>
	          			<span class="STYLE10">页面出错了</span>
	          			</td>
	          		</tr>
	          		
	          		<tr height="25">
		          		<td>
		          		  <a href="#" onclick="javascript:top.location.href='<%=basePath%>/system/authority/logout.do';">重 新 登 录</a> 
			        	  <a href="javascript:history.back();">后 退</a>
			        	  <a href="#" onclick="sendErrorMsg()" style="color: red">发送错误报告</a> 
		          		</td>
	          		</tr>
	          		<input type="hidden" id="errorUrl" value="<%=errorUrl%>" />
			        
	          	</table>
	          	
	     	 </td>
	      </table>
	      </td>
	  </tr>    	 
	  <tr>
    	<td><img src="<%=basePath%>/commons/image/error/error_bootom.jpg" width="510" height="32" /></td>
      </tr>
	</table>
	</body>
</html>