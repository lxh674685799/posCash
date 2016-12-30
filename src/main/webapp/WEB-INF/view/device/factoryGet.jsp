<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<html>
<head>
<title>设备厂商信息</title>
</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">	
			<legend class="legend">【${factory.name }】厂商信息</legend>
		
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight" width="30%">名称:</td>
                <td align="left" class="left">
                ${factory.name }</td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">固话:</td>
                <td align="left" class="left">
                ${factory.phone }</td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">联系人姓名:</td>
                <td align="left" class="left">
                ${factory.linkName }</td>               
            </tr> 
            <tr>
                <td align="right" class="tabRight">联系人手机号:</td>
                <td align="left" class="left">
                ${factory.mobile }</td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">地址:</td>
                <td align="left" class="left"> 
                <textarea style="width: 90%;overflow-y:visible"  >
                 ${factory.address }
                </textarea>
              
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>

    </form>
</body>
</html>
