<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>发送消息</title>
</head>

<f:Const var="STATUS_EXAMINE_FALSE" />
<f:Const var="STATUS_BUYING" />
<f:Const var="MESSAGE_DO" />
<f:Const var="MESSAGE_READ" />
<f:Const var="MESSAGE_UNREAD" />
<f:Const var="USER_MESSAGE_BUY" />
<f:Const var="USER_MESSAGE_REPAIR" />
<f:Const var="REPAIRING" />
<f:Const var="REPAIRED" />
<f:Const var="UNABLE_REPAIR" />

<body>
	<fieldset class="fieldset" style="padding: 5px 5px">
		<legend class="legend">${innerMessage.subject }</legend>
		<table cellpadding="0" cellspacing="0" class="table_add">
			<tr>
				<td align="right" class="tabRight" width="20%">发送人：</td>
				<td align="left" class="left" width="30%">${innerMessage.fromUser.name
					}</td>
				<td align="right" class="tabRight" width="20%">发送时间：</td>
				<td align="left" class="left" width="30%">${innerMessage.sendDate
					}</td>
			</tr>
			<tr>
				<td align="right" class="tabRight" width="20%">查阅时间：</td>
				<td align="left" class="left" width="30%"><c:choose>
						<c:when test="${(innerMessage.readStatus != MESSAGE_UNREAD)}">
                		${innerMessage.readDate }
                	</c:when>
						<c:otherwise>
                		未查阅
                	</c:otherwise>
					</c:choose></td>
				<td align="right" class="tabRight" width="20%">办理时间：</td>
				<td align="left" class="left" width="30%"><c:choose>
						<c:when test="${innerMessage.readStatus == MESSAGE_DO}">
                		  ${innerMessage.doDate }
                	</c:when>
						<c:otherwise>
                		未办理
                	</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
		<br />
	</fieldset>

	<!-- 设备申购信息 -->
	<c:if test="${innerMessage.type == USER_MESSAGE_BUY }">
		<form name="form" method="post" action="agree.do" id="form">
			<input type="hidden" name="innerMessageId"
				value="${innerMessage.id }"> <input type="hidden"
				name="returnUrl" value="${returnUrl}">

			<fieldset class="fieldset" style="padding: 5px 5px">
				<legend class="legend">设备申购基本信息</legend>
				<table cellpadding="0" cellspacing="0" class="table_add">
					<tr>
						<td align="right" class="tabRight" width="20%">购入设备：</td>
						<td align="left" class="left" width="80%">${buy.device.name
							}</td>
					</tr>
					<tr>
						<td align="right" class="tabRight" width="20%">购入数量：</td>
						<td align="left" class="left" width="80%">${buy.number }</td>
					</tr>
					<tr>
						<td align="right" class="tabRight" width="20%">购入单格：</td>
						<td align="left" class="left" width="80%">${buy.price }</td>
					</tr>
					<tr>
						<td align="right" class="tabRight" width="20%">购入总格：</td>
						<td align="left" class="left" width="80%">${buy.totalPrice }</td>
					</tr>
					<tr>
						<td align="right" class="tabRight" width="20%">附件：</td>
						<td align="left" class="left" width="80%"><a
							href="${ctx}/download/file.do?id=${file.id}"
							title="点击下载${file.fileName}">${file.fileName} </a></td>
					</tr>
					<tr>
						<td align="right" class="tabRight" width="20%">备注：</td>
						<td align="left" class="left" width="80%">${buy.remark }</td>
					</tr>
					<tr align="center">
						<td colspan="2"><c:if
								test="${!(innerMessage.readStatus == MESSAGE_DO)}">
								<c:choose>
									<c:when test="${buy.status == STATUS_BUYING}">
										<input type="submit" value="已购"
											class="l-button l-button-submit"
											onclick="$('#form').attr({'action':'buy.do'}).submit();" />
									</c:when>
									<c:when test="${buy.status == STATUS_EXAMINE_FALSE}">

									</c:when>
									<c:otherwise>
										<input type="submit" value="同意" id="agree"
											class="l-button l-button-submit" />
										<input type="button" value="不同意"
											class="l-button l-button-submit"
											onclick="$('#form').attr({'action':'unagree.do'}).submit();" />
									</c:otherwise>
								</c:choose>
							</c:if> <input type="button" value="返回" class="l-button l-button-back"
							onclick="back()" /></td>
					</tr>
				</table>
				<br />
			</fieldset>
		</form>
	</c:if>

	<!-- 设备维修信息 -->
	<c:if test="${innerMessage.type == USER_MESSAGE_REPAIR }">
		<form name="form" method="post"
			action="agree.do" id="form">
			<input type="hidden" name="innerMessageId"
				value="${innerMessage.id }"> <input type="hidden"
				name="returnUrl" value="${returnUrl}">

			<fieldset class="fieldset" style="padding: 5px 5px">
				<legend class="legend">设备维修信息</legend>
				<table cellpadding="0" cellspacing="0" class="table_add">
					<tr>
						<td align="right" class="tabRight" width="20%">报修人：</td>
						<td align="left" class="left" width="30%">${repair.sendUser.name
							}</td>

						<td align="right" class="tabRight" width="20%">报修时间：</td>
						<td align="left" class="left" width="30%">${repair.sendDate }</td>
					</tr>
					
					<tr>
						<td align="right" class="tabRight">维修状态：</td>
						<td align="left" class="left" colspan="3">
							<c:if test="${repair.repairStatus == REPAIRING }"> 维修中</c:if>
							<c:if test="${repair.repairStatus == REPAIRED }"> 已维修</c:if>
							<c:if test="${repair.repairStatus == UNABLE_REPAIR }"><span style="color: red"> 无法维修</span></c:if>
						</td>
					</tr>
					<tr>
						<td align="right" class="tabRight">备注：</td>
						<td align="left" class="left" colspan="3">${repair.remark }</td>
					</tr>
					<tr align="center">
						<td colspan="4"><c:if
								test="${(repair.repairStatus == REPAIRING)}">
								<input type="submit" value="已维修" id="canRepair"
									class="l-button l-button-submit" />
								<input type="button" value="无法维修" id="canNotRepair"
									class="l-button l-button-submit"
									onclick="$('#form').attr({'action':'unagree.do'}).submit();" />
							</c:if> <input type="button" value="返回" class="l-button l-button-back"
							onclick="back()" /></td>
					</tr>
				</table>
				<br />
			</fieldset>
			<fieldset class="fieldset" style="padding: 5px 5px">
				<legend class="legend">维修设备信息</legend>
				<display:table name="entitys" uid="f" cellpadding="0" class="blues"
					cellspacing="0">

					<display:column title="设备编号" style="text-align:left;"
						property="number"></display:column>
					<display:column title="设备名称" style="text-align:left;"
						property="device.name"></display:column>
					<display:column title="购入人" property="buy.buyUser.name"
						style="text-align:right;"></display:column>
					<display:column title="购入审核人" property="buy.ratifyUser.name"
						style="text-align:right;"></display:column>
					<display:column title="购入时间" property="buy.buyTime"
						style="text-align:right;"></display:column>
					<display:column title="所在单位" property="post.name"
						style="text-align:right;"></display:column>
					<display:column title="附件情况及存放地点" property="locationName"
						style="text-align:right;"></display:column>
				</display:table>
			</fieldset>


		</form>
	</c:if>
</body>
</html>
