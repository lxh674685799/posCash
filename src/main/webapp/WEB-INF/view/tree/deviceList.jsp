<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>设备列表</title>
	<%@include file="/commons/include/get.jsp" %>
	<%@include file="/commons/include/page.jsp" %>
	<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
	<script type="text/javascript">
	var isSingle='${isSingle}';
	$(function(){
		$("#deviceItem").find("tr").bind('click', function() {
			if(isSingle=='true'){
				var rad=$(this).find('input[name=deviceData]:radio');
				rad.attr("checked","checked");
			}else{
				var ch=$(this).find(":checkbox[name='deviceData']");
				window.parent.selectMulti(ch);
			}
		});
	});	 
	</script>
	<style type="text/css">
	html { overflow-x: hidden; }
	</style>
</head>
<body>
<form action="getDeviceByOrgId.do" method="post" id="searchform" name="searchform">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="25%" align="left">
			<input type="hidden" name="isSingle" value="${isSingle}" >
			<input type="hidden" name="orgId" value="${orgId}" >
			</td>
		</tr>	
	</table>
</form>
	<div class="panel">
	    <c:if test="${isSingle==false}">
			<c:set var="checkAll">
					<input onclick="window.parent.selectAll(this);" type="checkbox" />
			</c:set>
		</c:if>
		 <display:table name="deviceList" id="deviceItem" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
			<display:column title="${checkAll}" media="html" style="width:30px;">
				<c:choose>
					<c:when test="${isSingle==false}">
						<input onchange="window.parent.selectMulti(this);"  type="checkbox" class="pk" name="deviceData" value="${deviceItem.id}#${deviceItem.number}">
					</c:when>
					<c:otherwise>
						<input type="radio" class="pk" name="deviceData" value="${deviceItem.id}#${deviceItem.number}">
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column property="number" title="设备编号"   style="text-align:left;"></display:column>
			<display:column property="device.name" title="设备型号"   style="text-align:left;"></display:column>
		</display:table>
	</div>
	<div id="paging" class="page"></div>
</body>
</html>