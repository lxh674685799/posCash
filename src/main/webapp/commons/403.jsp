<%@page import="com.soft.core.util.RequestUtil,
				org.springframework.security.access.AccessDeniedException"  pageEncoding="UTF-8" isErrorPage="true" %>
<%
	String basePath=request.getContextPath();
	AccessDeniedException ex=(AccessDeniedException)request.getAttribute("ex");
	
%>
<html>
	<head>
		<title>访问拒绝</title>
			<style type="text/css">
			<!--
			.STYLE10 {
				font-family: "黑体";
				font-size: 36px;
			}
			-->  
			</style>
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
	          			<span class="STYLE10">访问被拒绝</span>
	          			</td>
	          		</tr>
	          		<tr height="70">
	          			<td>
	          			<%=ex.getMessage() %>
	          			</td>
	          		</tr>
	          		
	          		<tr height="25">
		          		<td>
		          		  <a href="#" onclick="javascript:top.location.href='<%=basePath%>/system/authority/logout.do';">重 新 登 录</a> 
			        	  <a href="javascript:history.back();">后 退</a>
		          		
		          		</td>
	          		</tr>
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