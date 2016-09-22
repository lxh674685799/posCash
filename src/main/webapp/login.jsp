<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<head>
<title>实验中心设备管理平台</title>
<link type="text/css" rel="stylesheet" href="${ctx }/resources/styles/login.css"/>
<script type="text/javascript">
$(function(){
	$('#form').bind('keydown',function(event) {  
		 if(event.keyCode==13){  
			 submitForm();
		}  
	});
	<c:if test="${error!=null}">
	 $.ligerDialog.warn('${error}');
	</c:if>
});

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, '');   
}

//刷新验证码
function flushSecurityCode(){
	$('#securityCodeImg').attr('src','${ctx }/system/authority/securityCode.do?curTTT='+(new Date().getTime()));
	return;
}

function submitForm(){
	var userId=document.getElementById('account').value;
	var pwd=document.getElementById('passWord').value;
	var securityCode = document.getElementById('securityCode').value;
	if(userId.trim()=="" || pwd.trim()=="" || securityCode.trim()==""){
		 $.ligerDialog.warn('未输入帐号、密码、验证码');
		return false;
	}
	document.getElementById('form').submit();
}
</script>
</head>

<body >
<form id="form" action="${ctx}/system/authority/login.do" method="post">
<div class="container">
	<div class="login">
		<div class="login_table">
			<table cellspadding="0" cellspacing="0" width="300" >
				<tr id="tipTr">
					<td>
						<input id="account" name="account" type="text" class="text"/>
					</td>
				</tr>
				<tr>
					<td>
					   <input id="passWord" name="passWord" type="password" class="text"/>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<input name="securityCode" id="securityCode" type="text" class="text" style="width:120px;"/>
								</td>
								<td style="padding-left:8px;">
									<img id="securityCodeImg" src="${ctx }/system/authority/securityCode.do" />
								</td>
								<td style="padding-left:8px;">
									<a href="javascript:flushSecurityCode()">换一张</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td >
						<input onclick="submitForm();" type="button" class="btn_login" value="登录"/>
					</td>
				</tr>
			</table>
			</div>
	
	</div>
	</div>
</form>
</body>
</html>
