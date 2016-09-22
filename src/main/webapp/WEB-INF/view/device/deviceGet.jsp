<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<html>
<head>
<title>设备信息</title>

</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">【${device.name }】信息</legend>
		<!-- 保留字段 -->
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight" width="20%">设备名称:</td>
                <td align="left" class="left">
                ${device.name }</td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">设备类型:</td>
                <td align="left" class="left">
				${device.type.name }</td>             
            </tr>  
             <tr>
                <td align="right" class="tabRight">设备厂家:</td>
                <td align="left" class="left">
              		${device.factory.name}
                </td>        
            </tr> 
            <tr>
                <td align="right" class="tabRight">设备型号:</td>
                <td align="left" class="left">
             		  ${device.model }</td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">设备条码:</td>
                <td align="left" class="left">${device.code }</td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">国别码:</td>
                <td align="left" class="left">
                ${device.countryCode }</td>               
            </tr>
              <tr>
                <td align="right" class="tabRight">规格:</td>
                <td align="left" class="left">
                ${device.spec }</td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                ${device.remark }
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
