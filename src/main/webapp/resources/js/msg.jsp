<%@page import="com.soft.core.web.ResultMessage"%>
<%@page import="com.soft.core.syscontext.Const"%>
<%
ResultMessage _obj_=(ResultMessage)session.getAttribute("message");
if(_obj_!=null){
	session.removeAttribute("message");
%>
<script type="text/javascript">
$(function(){
	<%
	 if(_obj_.getResult()==Const.MESSAGE_SUCCESS){
	%>
	$.ligerDialog.success('<%=_obj_.getMessage()%>');
	<%}%>
	<%
	 if(_obj_.getResult()==Const.MESSAGE_WARN){
	%>
	$.ligerDialog.warn('<%=_obj_.getMessage()%>');
	<%}%>
	<%
	 if(_obj_.getResult()==Const.MESSAGE_QUESTION){
	%>
	$.ligerDialog.question('<%=_obj_.getMessage()%>');
	<%}%>
	<%
	 if(_obj_.getResult()==Const.MESSAGE_ERROR){
	%>
	$.ligerDialog.error('<%=_obj_.getMessage()%>');
	<%}%>
});
</script>
<%
} %>
<script type="text/javascript">
function back(){
   window.location.href="${returnUrl}";
}
</script>




