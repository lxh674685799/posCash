<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<script type="text/javascript" src="${ctx }/resources/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
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
			<td width="10%" align="left">
			<input type="text" class="text" name="goodName" id="goodName" value="${goodName }" /></td>
			<td width="10%" align="right">结账方式：</td>
			<td width="10%" align="left">
			<select id="payType" name="payType" value="${payType }"  >
				<option value="0" <c:if test="${payType == 0}">selected</c:if> >全部</option>
				<option value="1"  <c:if test="${payType == 1}">selected</c:if>>现金</option>
				<option value="2"  <c:if test="${payType == 2}">selected</c:if>>点券</option>
				<option value="3" <c:if test="${payType == 3}">selected</c:if> >现金点券</option>
				<option value="4" <c:if test="${payType == 4}">selected</c:if> >赠送</option>
				<option value="5" <c:if test="${payType == 5}">selected</c:if> >VIP</option>
				
			</select>
			</td>
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
		<c:if test="${f.payType == 5}">
			VIP
		</c:if>
		
		</display:column>
		<display:column title="销售数量"  style="text-align:right;">${f.number}件</display:column>
		
		<display:column title="退货数量"  style="text-align:right;">${f.number2}件</display:column>
		
		<display:column title="销售金额（现金）"  style="text-align:right;">
		
		<c:if test="${f.payType == 1}">
						<fmt:formatNumber type="number" value="${f.number * f.g_money}" pattern="0.00" maxFractionDigits="2"/> 
		 元
		</c:if>
		<c:if test="${f.payType == 2}">
		0 元
		</c:if>
		<c:if test="${f.payType == 3}">
			<fmt:formatNumber type="number" value="${f.number * f.g_moneyCre}" pattern="0.00" maxFractionDigits="2"/> 
		
		 元
		</c:if>
		<c:if test="${f.payType == 4}">
			0元
		</c:if>
		<c:if test="${f.payType == 5}">
		0 元
		</c:if>
		</display:column>
		
			<display:column title="销售金额（点卷）"  style="text-align:right;">
		
		<c:if test="${f.payType == 1}">
						0
		 卷
		</c:if>
		<c:if test="${f.payType == 2}">
		${f.number * f.g_credit} 券
		</c:if>
		<c:if test="${f.payType == 3}">
			${f.number * f.g_creditMon} 券
		</c:if>
		<c:if test="${f.payType == 4}">
			0券
		</c:if>
		<c:if test="${f.payType == 5}">
		${f.number *f.g_vipCreditMon} 券
		</c:if>
		</display:column>
		
		<display:column title="实际收入（现金）"  style="text-align:right;">
		
		<c:if test="${f.payType == 1}">
		
		<fmt:formatNumber type="number" value="${(f.number -f.number2) * f.g_money}" pattern="0.00" maxFractionDigits="2"/> 
		 
		 元
		</c:if>
		<c:if test="${f.payType == 2}">
		0元
		</c:if>
		<c:if test="${f.payType == 3}">
		
		<fmt:formatNumber type="number" value="${(f.number -f.number2) * f.g_moneyCre}" pattern="0.00" maxFractionDigits="2"/> 
		 
		 元
		</c:if>
		<c:if test="${f.payType == 4}">
			0元
		</c:if>
		
		<c:if test="${f.payType == 5}">
		0元
		</c:if></display:column>
		
			<display:column title="实际收入（点卷）"  style="text-align:right;">
		
		<c:if test="${f.payType == 1}">
		
		0卷
		</c:if>
		<c:if test="${f.payType == 2}">
		${(f.number -f.number2) * f.g_credit} 券
		</c:if>
		<c:if test="${f.payType == 3}">
		
			${(f.number -f.number2) * f.g_creditMon} 券
		</c:if>
		<c:if test="${f.payType == 4}">
			0卷
		</c:if>
		
		<c:if test="${f.payType == 5}">
		${(f.number -f.number2) * f.g_vipCreditMon} 券
		</c:if></display:column>
	
	</display:table>	
	</form>	
<br>
<div id="paging" class="page"></div>

</body>
</html>