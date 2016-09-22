<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>

<f:Const var="MESSAGE_READ"/>
<f:Const var="MESSAGE_UNREAD"/>
<f:Const var="MESSAGE_DO"/>
<f:Const var="USER_MESSAGE_INFO"/>


<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/message/message/edit.do";
}

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
<fieldset class="fieldset">
	<legend class="legend">已发消息查询</legend>
<form action="list.do" method="post"  name="searchform" id="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">发送人：</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="fromUserName" id="fromUserName" value="${param.fromUserName }" />
			</td>
			<td width="15%" align="right">消息主题：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="subject" id="subject" value="${param.subject }" />
			<input type="hidden" class="text" name="type" id="type" value="${innerMessage.type }" />
			</td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>	
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="innerMessages" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/message/message/list.do">
		
		<display:caption class="tooltar_btn">
		<!--  
			<input type="button" value="添 加" name="add" class="btn_small"
				onclick="toAdd()" />-->
		<input type="button" value="删除" name="del" class="btn_small" onclick="mycheckbox()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="消息主题" property="subject" style="text-align:left;"></display:column>
		<display:column title="发送时间" property="sendDate" style="text-align:left;"></display:column>
		<display:column title="发送人" style="text-align:left;">
			<a href="${ctx}/user/user/get.do?id=${f.fromUserId}">${f.fromUser.name}</a>
		</display:column>
		<display:column title="状态"  style="text-align:left;">
		<c:if test="${f.readStatus == MESSAGE_READ }">已查阅</c:if>
		<c:if test="${f.readStatus == MESSAGE_UNREAD}">未查阅</c:if>
		</display:column>
		<display:column title="操作">
		<c:if test="${f.type == USER_MESSAGE_INFO }"><a href="javaScript:fullScreen('${ctx}/message/message/show.do?id=${f.id}')"">详情</a>	</c:if>
		<c:if test="${f.type != USER_MESSAGE_INFO }"><a href="${ctx}/message/message/get.do?id=${f.id}">详情</a>	</c:if>
		<a href="${ctx}/message/message/del.do?id=${f.id}">删除</a>
		<a href="${ctx}/message/message/rebark.do?id=${f.id}">回复</a>
		</display:column>
	</display:table>	
	</form>	
<br>
	<div id="paging" class="page"></div>

</body>
</html>