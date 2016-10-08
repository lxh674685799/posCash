<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<script type="text/javascript" src="${ctx }/resources/js/My97DatePicker/WdatePicker.js"></script>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>商品销售统计</title>

<script type="text/javascript">
$(function(){
	 var startTime = document.getElementById('startTime');
	 startTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(dp){loadChart();}});
	  }
	 
	 var endTime = document.getElementById('endTime');
	 endTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(dp){loadChart();}});
	  }
});
</script>


<body>
<fieldset class="fieldset">
	<legend class="legend">商品销售查询</legend>
	<form action="statis.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="10%" align="right">商品名称：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="goodName" id="goodName" value="${goodName }" /></td>
			<td width="10%" align="right">销售时间：</td>
			<td width="30%" align="left">
			<input type="text" class="text" name="startTime" id="startTime" value="${startTime}" />
			至
			<input type="text" class="text" name="endTime" id="endTime" value="${endTime}" /></td>
			
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>

<form id="form" method="post" action="del.do">
	<display:table name="GoodsInfo" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/goods/goodsLog/statis.do">
		
		<!-- 多选框 -->
		<display:column title="商品名称" style="text-align:left;">${f.g_name}
		</display:column>
		<display:column title="商品进价"  style="text-align:right;">${f.g_inPrice}元</display:column>
		<display:column title="商品库存"  style="text-align:right;">${f.g_sum}件</display:column>
		<display:column title="结账方式"  style="text-align:right;">
		<c:if test="${f.payType == 1}">
		现金
		</c:if>
		<c:if test="${f.payType == 2}">
		点券
		</c:if>
		<c:if test="${f.payType == 3}">
		现金点券
		</c:if>
		<c:if test="${f.payType == 4}">
			赠送
		</c:if>
		
		
		</display:column>
		<display:column title="销售数量"  style="text-align:right;">${f.number}件</display:column>
		<display:column title="销售金额"  style="text-align:right;">
		
		<c:if test="${f.payType == 1}">
		${f.number * f.g_money} 元
		</c:if>
		<c:if test="${f.payType == 2}">
		${f.number * f.g_credit} 券
		</c:if>
		<c:if test="${f.payType == 3}">
		${f.number * f.g_moneyCre} 元,	${f.number * f.g_creditMon} 券
		</c:if>
		<c:if test="${f.payType == 4}">
			0元
		</c:if></display:column>
		
	
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>


</body>
</html>