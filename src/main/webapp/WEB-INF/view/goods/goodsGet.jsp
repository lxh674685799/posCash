<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>

<html>
<head>
<title>设备信息</title>

</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">【${goods.name }】信息</legend>
		<!-- 保留字段 -->
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight" width="20%">商品名称:</td>
                <td align="left" class="left">
                ${goods.name }</td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">商品类型:</td>
                <td align="left" class="left">
				${goods.type.name }</td>             
            </tr>  
             <tr>
                <td align="right" class="tabRight">供货商:</td>
                <td align="left" class="left">
              		${goods.factory.name}
                </td>        
            </tr> 
             <tr>
                <td align="right" class="tabRight">进货价:</td>
                <td align="left" class="left">${goods.inPrice }</td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">销售价（现金）:</td>
                <td align="left" class="left">
                ${goods.money}元</td>               
            </tr>
              <tr>
                <td align="right" class="tabRight">销售价（积分）:</td>
                <td align="left" class="left">
                ${goods.credit }积分</td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">销售价（现金+积分）:</td>
                <td align="left" class="left"> 
                ${device.moneyCre}元+${device.creditMon}积分
                </td>
            </tr>            
            <tr>
                <td align="right" class="tabRight">库存:</td>
                <td align="left" class="left">
              		${goods.inventory}
                </td>        
            </tr>
            <tr>
                <td align="right" class="tabRight">创建时间:</td>
                <td align="left" class="left">
              		${goods.createTime}
                </td>        
            </tr> 
            <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left">
              		${goods.remark}
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
