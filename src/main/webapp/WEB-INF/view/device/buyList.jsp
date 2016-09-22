<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<f:Const var="STATUS_EXAMINE"/>
<f:Const var="STATUS_EXAMINE_FALSE"/>
<f:Const var="STATUS_BUYING"/>
<f:Const var="STATUS_BUYED"/>

<html>
<head>
<title>设备购入列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/device/buy/edit.do";
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">设备购入查询</legend>
<form action="list.do" method="post"  name="searchform" id="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">设备名称:</td>
			<td width="25%" align="left">
			 <select id="deviceId" name="deviceId">
				<option value="">--请选择购入设备--</option>
				<c:forEach var="d" items="${ds}">
				<option value="${ d.id }"
					<c:if test="${d.id==param.deviceId }">selected</c:if>>${d.name}</option>
					</c:forEach>
				</select>
			<td width="15%" align="right">购入状态:</td>
			<td width="25%" align="left">
			<select id="status" name="status">
			<option value="">---请选择状态---</option>
			<option value="0" <c:if test="${param.status== STATUS_EXAMINE }">selected</c:if>>审批中</option>
			<option value="1" <c:if test="${param.status== STATUS_BUYING }">selected</c:if>>购买中</option>
			<option value="2" <c:if test="${param.status== STATUS_BUYED }">selected</c:if>>已购买</option>
			<option value="3" <c:if test="${param.status== STATUS_EXAMINE_FALSE }">selected</c:if>>未同意</option>
			</select>
			</td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>	
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="devices" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${path }/device/buy/list.do">
		
		<display:caption class="tooltar_btn">
			<input type="button" value="申请" name="add" class="btn_small"
				onclick="toAdd()" />
		<!--<input type="button" value="删除" name="del" class="btn_small" onclick="mycheckbox()"/>	  -->
		
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		
		<display:column title="设备名称"  style="text-align:left;">
			<a href="${ctx}/device/device/get.do?id=${f.deviceId}">${f.device.name}</a>
		</display:column>
		<display:column title="购入数量" property="number" style="text-align:right;"></display:column> 
		<display:column title="购入单价（元）" property="price" style="text-align:right;"></display:column> 
		<display:column title="购入人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.buyUserId}">${f.buyUser.name}</a>
		</display:column>
		<display:column title="提交审批时间" property="ratifyTime" style="text-align:right;"></display:column> 	 		
		<display:column title="审批人" style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.ratifyUserId}">${f.ratifyUser.name}</a>
		</display:column> 
		<display:column title="状态"style="text-align:right;">
		<c:if test="${f.status == STATUS_EXAMINE}">审批中</c:if>
		<c:if test="${f.status == STATUS_BUYING}">审批同意，可以购买</c:if>
		<c:if test="${f.status == STATUS_BUYED}">已购买</c:if>
		<c:if test="${f.status == STATUS_EXAMINE_FALSE}">审批未通过</c:if>
		</display:column> 
		<display:column title="操作">
			<a href="${ctx}/device/buy/get.do?id=${f.id}">详情</a>	
			<!--
		<c:if test="${f.status == STATUS_EXAMINE}">
			<a href="${ctx}/device/buy/edit.do?id=${f.id}">修改</a>
		</c:if>  
			<a href="${ctx}/device/buy/del.do?id=${f.id}">删除</a>-->
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>