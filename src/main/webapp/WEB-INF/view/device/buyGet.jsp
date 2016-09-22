<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<html>
<head>
<title>设备购入申请详情</title>
</head>
<body>
    <form name="form" method="post" action="save.do" id="form" enctype="multipart/form-data">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">设备购入申请信息</legend>
			
		<input type="hidden" name="returnUrl" value="${returnUrl}">
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight" width="30%">设备名称:</td>
                <td align="left" class="left">
           			${buy.device.name }
				</td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">购入数量:</td>
                <td align="left" class="left">
            			 ${buy.number } </td>
            </tr>  
             <tr>
                <td align="right" class="tabRight">购入单价（元）:</td>
                <td align="left" class="left">
               		 ${buy.price }	</td>            
            </tr> 
              <tr>
                <td align="right" class="tabRight">购入总价（元）:</td>
                <td align="left" class="left">
               		 ${buy.totalPrice }	</td>            
            </tr> 
            <tr>
                <td align="right" class="tabRight">审批用户:</td>
                <td align="left" class="left">
               		 ${buy.ratifyUser.name }
                </td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">申请用户:</td>
                <td align="left" class="left">
               		 ${buy.buyUser.name }
                </td>               
            </tr> 
			 <tr>
                <td align="right" class="tabRight">提交审批时间:</td>
                <td align="left" class="left">
               ${buy.ratifyTime }</td>               
            </tr> 
             <c:if test="${buy.status == 1 || buy.status == 3 }">
			 <tr>
                <td align="right" class="tabRight">审批时间:</td>
                <td align="left" class="left">
               ${buy.doTime }</td>               
            </tr> 
			</c:if>
			 <c:if test="${buy.status == 2 }">
			  <tr>
                <td align="right" class="tabRight">审批时间:</td>
                <td align="left" class="left">
               ${buy.doTime }</td>               
            </tr> 
			 <tr>
                <td align="right" class="tabRight">购买时间:</td>
                <td align="left" class="left">
               ${buy.buyTime }</td>               
            </tr> 
			</c:if>
             <tr>
                <td align="right" class="tabRight">附件:</td>
                <td align="left" class="left">
                  <a
                     href="${ctx}/download/file.do?id=${file.id}"
                     title="点击下载${file.fileName}">${file.fileName}
                   </a>
                   </td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                ${buy.remark }
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br/>	
        </fieldset>

    </form>
</body>
</html>
