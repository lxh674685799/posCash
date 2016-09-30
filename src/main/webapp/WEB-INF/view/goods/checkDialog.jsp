<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<html>
<head>
<title>设备信息</title>
<script type="text/javascript">
var dialog = frameElement.dialog;
$(function(){
	$("#memberPhone").keypress(function(e){
		var memberPhone = $("#memberPhone").val();
		if(memberPhone == "") return;
		
		if(e.keyCode == 13){
			asynGetMember(memberPhone);
		}
	});
	
});
//请求后获取信息
function asynGetMember(memberPhone){
	$.post('${ctx}/goods/check/getMember.do',{memberPhone:memberPhone},function(data){
		if(data){
			window.parent.callBackMember(data);
			dialog.close();
		}else{
			window.parent.dialogWarn(memberPhone+ "不是会员！");
			$("#memberPhone").val('');
		}
	});
}

</script>
</head>
<body>
<div style="padding-top:10px;padding-left:5px"> 
  <input type="text" style="width:93%;height:25px" name="memberPhone" id="memberPhone" value="">
  </div>
</body>
</html>
