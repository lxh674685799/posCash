<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>会员管理列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/user/member/edit.do";
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">会员查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
		
			<td width="10%" align="right">会员编号:</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="memberNo" id="memberNo" value="${param.memberNo }" /></td>
			
			<td width="10%" align="right">联系电话:</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="phone" id="phone" value="${param.phone }" /></td>
			
			
			<td width="10%" align="right">姓名:</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="name" id="name" value="${param.name }" /></td>
			
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
	<input type="hidden" name="totalCount" value="${page.totalCount}">
	<input type="hidden" name="page" value="${page.pageIndex}">
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="members" uid="f" cellpadding="0" class="blues" cellspacing="0" requestURI="${path }/user/member/list.do">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" name="add" class="btn_small"
				onclick="toAdd()" />
		<input type="button" value="删除" name="del" class="btn_small" onclick="mycheckbox()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="会员编号" style="text-align:left;"property="memberNo"/>
		<display:column title="姓名" style="text-align:left;"property="name"></display:column>
		<display:column title="性别" style="text-align:right;">
			 ${f.sex==true?"男":"女"}
		</display:column> 
		<display:column title="联系电话" property="phone" style="text-align:right;"></display:column> 
		<display:column title="操作">
			<a href="${ctx}/user/member/edit.do?id=${f.id}">修改</a>
			<a href="${ctx}/user/member/del.do?id=${f.id}">删除</a>
			<a href="${ctx}/user/member/get.do?id=${f.id}">详情</a>   
			<a href="${ctx}/user/member/addValue.do?id=${f.id}">会员充值</a>
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>