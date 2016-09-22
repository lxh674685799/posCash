<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<f:Const var="DEVICE_LOAN"/>
<f:Const var="DEVICE_RETURN"/>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>设备借出信息</title>
</head>

<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<legend class="legend">设备借出信息</legend>

        <table  class="table_add" >    
            <tr>
                <td align="right" class="tabRight" width="20%">借给人:</td>
                <td align="left" class="left">
           			 ${loan.loanedUser.name }
                </td>               
          
                <td align="right" class="tabRight">借出人:</td>
                <td align="left" class="left">
           			 ${loan.loanUser.name }
                </td>               
            </tr>  
             <tr>
                <td align="right" class="tabRight">借出时间:</td>
                <td align="left" class="left">
                ${loan.loanTime }</td>               
          
                <td align="right" class="tabRight">预期归还时间:</td>
                <td align="left" class="left">
                ${loan.returnTime }</td>               
            </tr> 
            <tr>
                <td align="right" class="tabRight">借到地点:</td>
                <td align="left" class="left">
                ${loan.post.name }</td>               
          
                <td align="right" class="tabRight">状态:</td>
                <td align="left" class="left">
              <c:if test="${loan.status == DEVICE_LOAN}">		
			 <c:choose>
			 	<c:when test="${loan.returnTime < nowTime  }">
			 		<span style="color: red"> 未归还  ,已超过归还时间！</span>
			 	 </c:when>
			 	 <c:otherwise>
			 		 未归还
			 	 </c:otherwise>
			 </c:choose>	
			</c:if>
			<c:if test="${loan.status == DEVICE_RETURN}">
			 <c:choose>
			 	<c:when test="${loan.returnTime < loan.trueReturnTime  }">
			 		<span style="color: red"> 已归还  ,超过归还时间！</span>
			 	 </c:when>
			 	 <c:otherwise>
			 		已归还
			 	 </c:otherwise>
			 </c:choose>
			</c:if>
                </td>               
            </tr>
            
            <tr>
                <td align="right" class="tabRight">借出理由:</td>
                <td align="left" class="left">
                ${loan.forWhat }</td>
                <td align="right" class="tabRight">实际归还时间:</td>
                <td align="left" class="left">
                <c:if test="${loan.status == DEVICE_LOAN}">尚未归还 </c:if>
                <c:if test="${loan.status == DEVICE_RETURN}">${loan.returnTime} </c:if>
                </td>               
            </tr> 
         
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left" colspan="3"> 
               ${loan.remark }
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
		<legend class="legend">借出设备信息</legend>
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
