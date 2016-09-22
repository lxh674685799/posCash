<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head></head>
<script type="text/javascript">
	function fullScreen(urlSrc) {
		var c = screen.availHeight - 35;
		var a = screen.availWidth - 5;
		var e = "top=0,left=0,height="
				+ c
				+ ",width="
				+ a
				+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
		window.open(urlSrc, "", e, true);
	}
	</script>
<body>
 
<form id="form" method="post" action="list.do">
	<display:table name="messageList" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/message/messgae/list.do">
		<display:column title="消息主题" property="subject" style="text-align:left;"></display:column>
		<display:column title="发送时间" property="sendDate" style="text-align:left;"></display:column>
		<display:column title="发送人"  property="fromUserName.name"style="text-align:left;"></display:column>
		<display:column title="操作">
		<a href="javaScript:fullScreen('${ctx}/message/message/show.do?id=${f.id}')">详情</a>	
		</display:column>
	</display:table>	
	</form>	
<br>
<!--  	<p align="right"><a href="${ctx}/message/message/list.do?type=1">更多&gt;&gt;</a>
	
	</p>-->
</body>
</html>
