<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
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
<fieldset class="fieldset">
	<legend class="legend">公告查询</legend>
<form action="list.do" method="post"  name="searchform" id="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">发送人：</td>
			<td width="25%" align="left">
			
			</td>
			<td width="15%" align="right">消息主题：</td>
			<td width="25%" align="left">
		
			</td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>	
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="notices" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/message/notice/list.do">
		
	
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="消息主题" property="subject" style="text-align:left;"></display:column>
		<display:column title="发布时间" property="createDate" style="text-align:left;"></display:column>
		<display:column title="发布人"  property="createUser.name"style="text-align:left;"></display:column>
		<display:column title="操作">
		<a href="javaScript:fullScreen('${ctx}/message/notice/get.do?id=${f.id}')">查看</a>	
		</display:column>
	</display:table>	
	</form>	
<br>
	<div id="paging" class="page"></div>

</body>
</html>