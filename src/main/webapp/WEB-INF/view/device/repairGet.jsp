<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<f:Const var="REPAIRING"/>
<f:Const var="REPAIRED"/>
<f:Const var="UNABLE_REPAIR"/>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>设备维修信息</title>
</head>

<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<legend class="legend">设备维修信息</legend>

        <table  class="table_add" >    
            <tr>
                <td align="right" class="tabRight" width="20%">报修人:</td>
                <td align="left" class="left">
           			 ${repair.sendUser.name }
                </td>               
          
                <td align="right" class="tabRight">维修人:</td>
                <td align="left" class="left">
           			 ${repair.repairUser.name }
                </td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">报修时间:</td>
                <td align="left" class="left">
                ${repair.sendDate }</td>               
          
                <td align="right" class="tabRight">维修时间:</td>
                <td align="left" class="left">
                	<c:if test="${repair.repairStatus == REPAIRING }"> 维修中</c:if>
					<c:if test="${repair.repairStatus == REPAIRED }"> ${repair.repairDate }</c:if>
                </td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">维修状态:</td>
                <td align="left" class="left" colspan="3"> 
              	 	<c:if test="${repair.repairStatus == REPAIRING }"> 维修中</c:if>
					<c:if test="${repair.repairStatus == REPAIRED }"> 已维修</c:if>
					<c:if test="${repair.repairStatus == UNABLE_REPAIR }"> 无法维修</c:if>
                </td>
            </tr>  
            
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left" colspan="3"> 
               ${repair.remark }
                </td>
            </tr>            
            <tr align="center"><td colspan="4"> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>
         
        <fieldset class="fieldset" style="padding: 5px 5px">
		<legend class="legend">设备信息</legend>
		<display:table name="entitys" uid="f" cellpadding="0" class="blues"
		cellspacing="0" requestURI="${ctx }/device/entity/list.do">
		
			
		<display:column title="设备编号" style="text-align:left;"property="number"></display:column>
		<display:column title="设备名称" style="text-align:left;" property="device.name"></display:column>
		<display:column title="购入人" property="buy.buyUser.name" style="text-align:right;"></display:column> 
		<display:column title="购入审核人" property="buy.ratifyUser.name" style="text-align:right;"></display:column> 
		<display:column title="购入时间" property="buy.buyTime" style="text-align:right;"></display:column> 
		<display:column title="存放位置" property="post.name" style="text-align:right;"></display:column> 
		<display:column title="附件情况及存放地点" property="locationName" style="text-align:right;"></display:column>
		</display:table>	
		</fieldset>
    </form>

</body>
</html>
