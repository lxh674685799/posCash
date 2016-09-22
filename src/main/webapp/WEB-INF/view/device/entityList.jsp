<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<f:Const var="DEVICE_STATUS_NORMAL"/>
<f:Const var="DEVICE_STATUS_LOAN"/>
<f:Const var="DEVICE_STATUS_GET"/>
<f:Const var="DEVICE_STATUS_REPAIR"/>
<f:Const var="DEVICE_STATUS_SCRAP"/>

<html>
<head>
<title>设备列表</title>

<script type="text/javascript">
function toRepir() { 
	var falg = 0; 
	$("input[name='id']:checkbox").each(function () { 
	if ($(this).attr("checked")) { 
	falg =1; 
	} 
	}) 
	if (falg > 0) {
		$.ligerDialog.confirm("确定要报修？", function (yes)
          {if(yes)
			$('#form').submit();
             });
	}
	else {
		$.ligerDialog.warn("请选择要报修的设备！");
	}
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">设备查询</legend>
<form action="list.do" method="post"  name="searchform" id="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">设备编号：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="number" id="number" value="${param.number }" /></td>
			<td width="15%" align="right">设备状态：</td>
			<td width="25%" align="left">
			<select name="status">
			<option value="">--选择状态--</option>
			<option value="${DEVICE_STATUS_NORMAL }" <c:if test="${DEVICE_STATUS_NORMAL == param.status }" > selected</c:if> >正常</option>
			<option value="${DEVICE_STATUS_LOAN }" <c:if test="${DEVICE_STATUS_LOAN == param.status }" > selected</c:if>  >借出</option>
			<option value="${DEVICE_STATUS_GET }" <c:if test="${DEVICE_STATUS_GET == param.status }" > selected</c:if>  >发放</option>
			<option value="${DEVICE_STATUS_REPAIR }" <c:if test="${DEVICE_STATUS_REPAIR == param.status }" > selected</c:if>  >维修</option>
			<option value="${DEVICE_STATUS_SCRAP }" <c:if test="${DEVICE_STATUS_SCRAP == param.status }" > selected</c:if>  >报废</option>
			</select>
			<td width="25%" align="left">
			<input type="hidden" class="text" name="deviceId" id="deviceId" value="${deviceId }" />
			<input name="returnUrl" id="returnUrl"  type="hidden" value="${returnUrl }"/>
			</td>
		
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>

<form id="form" method="post" action="${ctx }/device/entity/toRepair.do">
	<display:table name="devices" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/device/entity/list.do">
		
		<display:caption class="tooltar_btn">
			<input type="button" value="返回" name="del" class="btn_small" onclick="back()"/>	
			<input type="button" value="报修" name="del" class="btn_small" onclick="toRepir()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column title="<input type='checkbox' onclick='checkAll(this)'/>" >
		 <c:if test="${DEVICE_STATUS_REPAIR != f.status }" >
		 	<input type="checkbox" name="id" value="${f.id}" id="id"/>
		 </c:if>
		</display:column>
		
		<display:column title="设备编号" style="text-align:left;"property="number"></display:column>
		<display:column title="购入人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.buy.buyUserId}">${f.buy.buyUser.name}</a>
		</display:column> 
		<display:column title="购入审核人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.buy.ratifyUserId}">${f.buy.ratifyUser.name}</a>
		</display:column> 
		<display:column title="购入时间" property="buy.buyTime" style="text-align:right;"></display:column> 
		<display:column title="单位名称" property="post.name" style="text-align:right;"></display:column> 
		<display:column title="附件情况及存放地点" property="locationName" style="text-align:right;"></display:column> 
		<display:column title="状态"  style="text-align:right;">
			 <c:if test="${DEVICE_STATUS_NORMAL == f.status }" > 正常</c:if>
			 <c:if test="${DEVICE_STATUS_LOAN == f.status }" > 借出</c:if> 
			 <c:if test="${DEVICE_STATUS_GET == f.status }" > 发放</c:if>
			 <c:if test="${DEVICE_STATUS_REPAIR == f.status }" >维修</c:if>
			 <c:if test="${DEVICE_STATUS_SCRAP == f.status }" >报废</c:if>
		</display:column>
	
		<display:column title="操作">
		 <c:if test="${DEVICE_STATUS_REPAIR != f.status }" >
		 	<a href="${ctx}/device/entity/toRepair.do?id=${f.id}">报修 </a>
		 </c:if>			
		</display:column>
		
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>

</body>
</html>