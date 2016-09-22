<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.lxh.cn/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/resources/js/lg/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/page/jquery.js"  ></script>
<script type="text/javascript" src="${ctx}/resources/js/lg/base.js" ></script>
<script type="text/javascript" src="${ctx}/resources/js/lg/ligerui.min.js"></script>

<f:link href="lg/skins/Aqua/css/ligerui-all.css"></f:link>
<f:link href="lg/skins/ligerui-icons.css" />
<f:link href="form.css"></f:link>
<f:link href="web.css"></f:link>
<f:link href="displaytagall.css"></f:link>

<!-- 提示框 -->
<script src="${ctx}/resources/js/lg/plugins/ligerDrag.js" type="text/javascript"></script> 
<script src="${ctx}/resources/js/lg/plugins/ligerDialog.js" type="text/javascript"></script>

<%@include file="/resources/js/msg.jsp"%>