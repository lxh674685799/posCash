<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<f:Const var="REPAIRING"/>
<f:Const var="REPAIRED"/>
<f:Const var="UNABLE_REPAIR"/>
<html>
<head>
<title>设备维修列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/device/repair/edit.do";
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">设备维修信息查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">维修人：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="repairUserName" id="repairUserName" value="${param.repairUserName }" /></td>
			<td width="15%" align="right">维修状态：</td>
			<td width="25%" align="left">
			<select name="repairStatus">
			<option value="">--选择状态--</option>
			<option value="${REPAIRING }" <c:if test="${REPAIRING == param.repairStatus }" > selected</c:if> >正在维修</option>
			<option value="${REPAIRED }" <c:if test="${REPAIRED == param.repairStatus }" > selected</c:if>  >已维修</option>
			<option value="${UNABLE_REPAIR }" <c:if test="${UNABLE_REPAIR == param.repairStatus }" > selected</c:if>  >无法维修</option>
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
	<display:table name="repairs" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/device/repair/list.do">	
		<display:caption class="tooltar_btn">
		<input type="button" value="添 加" name="add" class="btn_small" onclick="toAdd()" />
		<!-- 
		<input type="button" value="删除" name="toDel" class="btn_small" onclick="mycheckbox()"/>	 -->
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id"/>
		</display:column>
		<display:column title="报修人" style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.sendUserId}">${f.sendUser.name}</a>
		</display:column> 
		<display:column title="维修人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.repairUserId}">${f.repairUser.name}</a>	
		</display:column> 
		<display:column title="报修时间" property="sendDate" style="text-align:right;"></display:column> 
		<display:column title="维修时间"  style="text-align:right;">
			<c:if test="${f.repairDate != null }"> ${f.repairDate}</c:if>
			<c:if test="${f.repairDate == null }"> 尚未维修</c:if>
		</display:column> 
		<display:column title="维修状态"  style="text-align:right;">
			<c:if test="${f.repairStatus == REPAIRING }"> 维修中</c:if>
			<c:if test="${f.repairStatus == REPAIRED }"> 已维修</c:if>
			<c:if test="${f.repairStatus == UNABLE_REPAIR }"><span style="color: red">无法维修</span> </c:if>
		</display:column> 
		<display:column title="操作">
			<a href="${ctx}/device/repair/get.do?id=${f.id}">详情</a>
			<!-- <a href="${ctx}/device/repair/del.do?id=${f.id}">删除</a> -->			
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>