<%--
	time:2011-11-08 12:04:22
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<html>
<head>
	<title>通知公告</title>
	<style type="text/css">
		.cont_show{ width:90%; margin:auto;}
		.cont_show .cont_title{ border-bottom:1px #b7b7b7 solid; height:auto; display:block; text-align:center;}
		.cont_show .cont_title .title{ line-height:45px; font-family:NSimSun; font-weight:bold; font-size:22px; color:#243a5b;}
		.cont_show .cont_desc{ line-height:40px; font-family:"宋体"; color:#7d7d7d; font-size:12px; text-align:center;}
		.cont_show .cont_cont{ line-height:24px; font-family:"宋体"; color:#414141; font-size:12px; }
		.cont_show .cont_page{ line-height:25px; font-family:"宋体"; color:#414141; font-size:12px; margin-top:15px;}
		.cont_show .cont_page a{ line-height:25px; font-family:"宋体"; color:#243a5b; font-size:12px; text-decoration:none;}
		.cont_show .cont_page a:hover{ line-height:25px; font-family:"宋体"; color:#a45100; font-size:12px; text-decoration:underline;}
	</style>
	 <link href="${ctx}/resources/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="panel" id="toppanel">	
		<div class="cont_show">
			<div class="cont_title" style="border-bottom: 1px #b7b7b7 solid;height: auto;display: block;text-align: center;">
				<span class="title" style="line-height: 45px;font-family: NSimSun;font-weight: bold; font-size: 22px;">${notice.subject}</span>
			</div>
			
			<div class="cont_desc" style="line-height: 40px;font-family: '宋体';color: #7d7d7d;font-size: 12px;text-align: center;">
				发布人:${notice.createUser.name} &nbsp;&nbsp;&nbsp;&nbsp;  发布单位:${notice.createUser.post.name}
			    &nbsp;&nbsp;&nbsp;&nbsp;  发布日期:${notice.createDate }
			</div>
			<div class="cont_cont" style="line-height: 24px;font-family: '宋体';color: #414141;font-size: 12px;">
				${notice.content}
			</div>		
		</div>
    </div>
	<div class="panel-toolbar" id="pToolbar">
		<div class="toolBar">
		
			<div class="bottom" style="margin-left: 10px; margin-top: 20px;">
				<input type="button" value="关闭" class="l-button l-button-back" onclick="window.close()" />
			</div>

		</div>	
    </div>
   
</body>
</html>
