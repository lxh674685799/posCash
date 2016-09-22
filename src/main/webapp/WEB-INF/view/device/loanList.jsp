<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<f:Const var="DEVICE_LOAN"/>
<f:Const var="DEVICE_RETURN"/>

<html>
<head>
<title>设备借出信息列表</title>

<script type="text/javascript">
function toAdd(){
   window.location.href="${ctx}/device/loan/edit.do";
}

function returncheckbox() { 
	var falg = 0; 
	$("input[name='id']:checkbox").each(function () { 
	if ($(this).attr("checked")) { 
	falg =1; 
	} 
	}) 
	if (falg > 0) {
		$.ligerDialog.confirm("确定都已归还？", function (yes)
          {if(yes)
			$('#form').submit();
             });
	}
	else {
		$.ligerDialog.warn("请选择归还设备！");
	}
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">设备借出信息查询</legend>
<form action="list.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">借给人：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="loanedUserName" id="loanedUserName" value="${param.loanedUserName }" /></td>
			<td width="15%" align="right">是否归还：</td>
			<td width="25%" align="left">
			<select name="status">
			<option value="">--选择状态--</option>
			<option value="${DEVICE_RETURN }" <c:if test="${DEVICE_RETURN == param.status }" > selected</c:if> >已归还</option>
			<option value="${DEVICE_LOAN }" <c:if test="${DEVICE_LOAN == param.status }" > selected</c:if>  >未归还</option>
			</select>		
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>	
	</table>
</form>
</fieldset>

<form id="form" method="post" action="returnBack.do">
	<display:table name="devices" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/device/loan/list.do">	
		<display:caption class="tooltar_btn">
		<input type="button" value="添 加" name="add" class="btn_small" onclick="toAdd()" />
		<input type="button" value="归还" name="toDel" class="btn_small" onclick="returncheckbox()"/>	
		</display:caption>
		
		<!-- 多选框 -->
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>" >
		<input type="checkbox" name="id" value="${f.id}" id="id" <c:if test="${f.status == DEVICE_RETURN}"> disabled </c:if>/>
		</display:column>
		<display:column title="借出人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.loanUserId}">${f.loanUser.name}</a>
		</display:column> 
		<display:column title="借给人"  style="text-align:right;">
			<a href="${ctx}/user/user/get.do?id=${f.loanedUserId}">${f.loanedUser.name}</a>
		</display:column>
		<display:column title="使用地点" property="loanedUser.post.name" style="text-align:right;"></display:column> 
		<display:column title="借出时间" property="loanTime" style="text-align:right;"></display:column> 
		<display:column title="应归还时间" property="returnTime" style="text-align:right;"></display:column> 
		<display:column title="归还状态" style="text-align:right;">
			<c:if test="${f.status == DEVICE_LOAN}">		
			 <c:choose>
			 	<c:when test="${f.returnTime < nowTime  }">
			 		<span style="color: red"> 未归还  ,已超过归还时间！</span>
			 	 </c:when>
			 	 <c:otherwise>
			 		 未归还
			 	 </c:otherwise>
			 </c:choose>	
			</c:if>
			<c:if test="${f.status == DEVICE_RETURN}">
			 <c:choose>
			 	<c:when test="${f.returnTime < f.trueReturnTime  }">
			 		<span style="color: red"> 已归还  ,超过归还时间！</span>
			 	 </c:when>
			 	 <c:otherwise>
			 		已归还
			 	 </c:otherwise>
			 </c:choose>
			</c:if>
		</display:column> 
		<display:column title="操作">
			<a href="${ctx}/device/loan/returnBack.do?id=${f.id}">归还</a>
			<a href="${ctx}/device/loan/get.do?id=${f.id}">详情</a>
		</display:column>
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>
</body>
</html>