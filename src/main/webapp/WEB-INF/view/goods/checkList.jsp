<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>结账商品列表</title>

<script type="text/javascript">
var index = 0;//增加的行数

var totalMoney = 0;//1总价钱
var totalCredit = 0;//2总积分

var sumMoney = 0;//结账现金列总值 
var sumCredit = 0;//结账积分卷总值

var table;

var checkType= 1;//结账方式   默认现金

var goodsStr = [];//商品信息数组

var receiveMoney = 0;//收入金额

var changeMoney = 0;//找零金额

var receiveCredit = 0;//收入积分

var isUserMember = 1; //1 不使用会员 2 使用会员

$(function(){
	//初始化table值
	table  = $("#checkGoods");

	window.onhelp = function(){return false};
	
	//绑定键盘事件
	$('#body').keydown(function(event){
		var e = event || window.event;
		var curKey = e.keyCode || e.which || e.charCode;
		 if(curKey == 27){
			window.location.href = "${ctx}/goods/check/list.do";
			//return false;
			event.preventDefault();
		}else if(curKey== 112){//F1值112 在ie下回打开微软帮助界面 所有改为F2登录会员 
			$.ligerDialog.close();//先关闭弹出框  放置多开
			var urlSrc =  "${ctx}/goods/check/dialog.do";
			 $.ligerDialog.open({
				 id:'memberDialog',
			    height:120,
				width: 300,
				title : '会员登录(手机号登录)',
				url: urlSrc, 
				isResize: true,
				allowClose:true,
			 }); 
			
		} 
	});
	
	//查询商品信息
	$("#code").keypress(function(event){
		var codeVal = $("#code").val();
		if(codeVal == "") return;
		var e = event || window.event;
		var curKey = e.keyCode || e.which || e.charCode;
		if(curKey == 13){
			asynQueryGoods();
		}
	});
	//计算收入和找零
	 $("#calculateInput").keydown(function(event) {
		    var e = event || window.event;
			var curKey = e.keyCode || e.which || e.charCode;
			if(curKey == 13){
			 	var inputVal = $("#calculateInput").val();
			 	if(sumMoney == 0) return;
			 	if(sumMoney > inputVal){
					$.ligerDialog.warn("金额数小于结账总金额数！");
				}else{
					$("#calculateOutput").text(inputVal*1 - sumMoney*1);
				} 
			} 
		});
	
	 //设置table的margin-top和margin-bottom
	 var topHeight = $("#top").height();
	 var bottomHeight = $("#countTypeDiv").height()
	 $("#checkGoods").css({'margin-top':topHeight +2+ "px",'margin-bottom':bottomHeight + 20 + 'px'});
	 
});


function asynQueryGoods(){
	var goodsCode = $("#code").val();
	var url = "${ctx}/goods/check/getByCode.do?code="+goodsCode;
	$.ajax({
		async : false,
		dateType : "json",
		type : "POST",
		url : url,
		success : function(data) {
			if(!data){
				$.ligerDialog.warn("请输入正确的商品条码！");
				return;
			}
			//获取商品信息
			var name = data.name;//商品名称
			var code = data.code;//商品编码
			var money = data.money;//商品价格
			var credit = data.credit;//商品积分
			var moneyCre = data.moneyCre;//商品现金部分
			var creditMon = data.creditMon;//商品积分部分
			//开始增加商品信息
			
			var isExist = false;
			table.find("tr").each(function(index,g){
			    var tdCode = $(this).children("[name='code']");
			    var tdCodeValue = tdCode.text().trim() 
				if(tdCodeValue == code){
			     	var numberHis = $(this).children("[name='numberTd']").children("[name='number']");
			     	 var numberVal = numberHis.val();
			     	numberHis.val(numberVal*1 + 1*1);
			     	isExist = true;
			     	return;
				}		     
			  });
			//得到总额
			totalMoney = totalMoney*1 + money*1;
			totalCredit =  totalMoney*1 +  credit*1;
			
			//设置总值
			if(sumMoney != totalMoney){
				sumMoney = totalMoney;
				//sumCredit = totalCredit;
			}
			
			
			if(isExist == true){
				//结算行
				addCountRow(totalMoney,0);
				//商品全部以现金结账
	            cashPayfor();
				
				return;
			}
				
				
			 var row = $("<tr id='"+code+"'></tr>");
			//td 最后隐藏了 商品的价格信息可取
			 var td = $("<td style='text-align: center;' name='code'> "+code+"</td><td name='goodsName'>"+ name+"</td><td name='money'>"+ money+"</td><td name='credit'>"+0+"</td><td name='numberTd'> <img src='${ctx}/resources/images/down.png' onclick='reduceNumber(this)' title='减少' width='14px'/>&nbsp;<input name='number' readonly='true' style='width:20px' value='1' />&nbsp;<img src='${ctx}/resources/images/add.png' onclick='addNumber(this)' title='增加'  width='13px'/></td><td name='operate' style='width:20%'><img src='${ctx}/resources/images/remove.png' onclick='removeSelf(this)' title='移除' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select onchange='payTypeChange(this)' name='payType'><option value='1'>现金付款</option><option value='2'>积分付款</option><option value='3'>现金和积分付款</option></select></td><td class='moneyCre' name='moneyCre' style='display:none;'>"+moneyCre+"</td><td class='creditMon' name='creditMon' style='display:none;'>"+creditMon+"</td><td name='moneyHid' style='display:none;'>"+money+"</td><td  name='creditHid' style='display:none;'>"+credit+"</td>");
			 row.append(td);
			 table.append(row);
			//增加商品信息结束
			
			//增加结算行
			 addCountRow(totalMoney,0);
             index++;
             //把table拉到底部
             var h = $("#checkGoods").height();
             var hh = $("body").height() -  $("#countTypeDiv").height() - $("#top").height()-20;
             if(h >= hh){
            	 var hei = $(document).height()-$(window).height();
           	    $(document).scrollTop(hei);
             }
             
             //商品全部以现金结账
             cashPayfor();
             
		},

	});
};


//减少数量
 function reduceNumber(a){
	 var b = $(a);
	 var number = b.parent().children("[name='number']");
	 var numberVal = number.val();
	 if(numberVal == 1){
		 b.parent().parent().remove();
		 index--;
	 }else{
		 number.val(numberVal*1 - 1*1);
	 }
	 //计算总账和更改总账
	 sumRowAccounts();
	 addCountRow(sumMoney,sumCredit);
	 
	 //重新设置总额和积分
	 totalMoney = sumMoney;
	 totalCredit = sumCredit;
	 
	//移除时候判断是否还有商品信息并清除信息
	 cleanAccounts();
	 
 }
 //增加数量
 function addNumber(a){
	 var b = $(a);
	 var number = b.parent().children("[name='number']");
	 var numberVal = number.val();
	 number.val(numberVal*1 + 1*1);
	 
	//计算总账和更改总账
	 sumRowAccounts();
	 addCountRow(sumMoney,sumCredit);
	 
	 //重新设置总额和积分
	 totalMoney = sumMoney;
	 totalCredit = sumCredit;
	 
 }
 //移除
 function removeSelf(a){
	 var b = $(a);
	 var moneyHid = b.parent().parent().children("[name='moneyHid']").text().trim();
	 var creditHid = b.parent().parent().children("[name='creditHid']").text().trim();
	 var moneyCre = b.parent().parent().children("[name='moneyCre']").text().trim();
	 var creditMon = b.parent().parent().children("[name='creditMon']").text().trim();
	 
	 totalMoney = totalMoney*1 - moneyHid*1;
     totalCredit =  totalCredit*1 -  creditHid*1;
	 
	 b.parent().parent().remove();
	 index--;
	//计算总账和更改总账
	 sumRowAccounts();
	 addCountRow(sumMoney,sumCredit);
	 
	 //重新设置总额和积分
	 totalMoney = sumMoney;
	 totalCredit = sumCredit;
	 
	 //移除时候判断是否还有商品信息并清除信息
	 cleanAccounts();
 }
 //付款方式改变
 function  payTypeChange(a){
	 var b = $(a);
	 var type = b.val();
	var parentTr =  b.parent().parent();
	var moneyHid = b.parent().parent().children("[name='moneyHid']").text().trim();
	var creditHid = b.parent().parent().children("[name='creditHid']").text().trim();
	var moneyCre = b.parent().parent().children("[name='moneyCre']").text().trim();
	var creditMon = b.parent().parent().children("[name='creditMon']").text().trim();
	
	if(type == 1){//现金
		b.parent().parent().children("[name='money']").text(moneyHid);
		b.parent().parent().children("[name='credit']").text(0);
		
		//计算总账和更改总账
		sumRowAccounts();
		addCountRow(sumMoney,sumCredit);
		
		//设置结账方式
		checkType = 1;
		
	}else if(type == 2){//积分
		b.parent().parent().children("[name='money']").text(0);
		b.parent().parent().children("[name='credit']").text(creditHid);
		
		//计算总账和更改总账
		sumRowAccounts();
		addCountRow(sumMoney,sumCredit);
		
		//设置结账方式
		checkType = 2;
		
	}else if(type == 3){//积分和现金
		b.parent().parent().children("[name='money']").text(moneyCre);
		b.parent().parent().children("[name='credit']").text(creditMon);
		
		//计算总账和更改总账
		sumRowAccounts();
		addCountRow(sumMoney,sumCredit);
		
		//设置结账方式
		checkType = 3;
	}
	
	//结账运算
	checkOutput();
 	
 }
 
 //计算总账
 function  addCountRow(countMoney,countCredit){
	  $("#body").children("#countDiv").children("#totalDiv").html(countMoney+"&nbsp;元&nbsp;"+countCredit+ "&nbsp;卷");
 }
 
 
 //计算结账总现金和总积分卷 同时计算其他隐藏四列
 function sumRowAccounts(){
	 //每次先清0
	 sumMoney = 0;
	 sumCredit = 0;
	 //遍历所有tr获取值
	 table.find("tr").each(function(index,g){
		    var tdMoney = $(this).children("[name='money']");//得到money列
		    var tdCredit = $(this).children("[name='credit']");//得到积分列
		    var numberHis = $(this).children("[name='numberTd']").children("[name='number']"); //得到数量列
	     	var numberVal = numberHis.val();
		    if(!numberVal)//如果不存在number
		    	numberVal = 1;
		    var tdMoneyValue = tdMoney.text().trim()*1;
		    var tdCreditValue = tdCredit.text().trim()*1;
		    sumMoney = sumMoney*1 + tdMoneyValue*numberVal;
		    sumCredit = sumCredit*1 + tdCreditValue*numberVal;
		  });
 }
 
 //移除时候判断是否还有商品信息并清除信息
 function  cleanAccounts(){
	 //遍历所有tr获取值
	 var rows=0;
	 table.find("tr").each(function(index,g){
		    var tdMoney = $(this).children("[name='money']");//得到money列
		    if(tdMoney){
		    	rows++;
		    }
		  });
	 //rows在最初的时候为1 添加商品信息后加总价 成2  rows为2时表示列表无商品信息清空所有信息
	if(rows == 2){
		 index = 0;//增加的行数

		 totalMoney = 0;//1总价钱
		 totalCredit = 0;//2总积分

		 totalMoneyCre = 0;//3总积分的现金价
		 totalCreditMon = 0;//3总积分的积分卷

		 sumMoney = 0;//结账总现金
		 sumCredit = 0;//结账总积分卷
	}
 }
 
 //现金结账
 function cashPayfor(){
	 //遍历所有tr获取值
	 table.find("tr").each(function(index,g){
		    var tdMoney = $(this).children("[name='money']");//得到money列
		    var tdCredit = $(this).children("[name='credit']");//得到积分列
		    var tdMoneyHit = $(this).children("[name='moneyHid']");//得到隐藏money列
		    var tdPayType = $(this).children("[name='operate']").children("[name='payType']");//得到select
		    tdPayType.val(1);//设置为现金付款
		    var tdMoneyHitValue = tdMoneyHit.text().trim()*1;
		    tdMoney.text(tdMoneyHitValue);
		    tdCredit.text(0);
		  });
	 //计算总价同时增加结算行
	 sumRowAccounts();
	 addCountRow(sumMoney,sumCredit);
	 
	//设置结账方式
	checkType = 1;
	
	//结账运算
	checkOutput();
 	
 }
 //积分结账
 function numericalPayfor(){
	 //遍历所有tr获取值
	 table.find("tr").each(function(index,g){
		    var tdMoney = $(this).children("[name='money']");//得到money列
		    var tdCredit = $(this).children("[name='credit']");//得到积分列
		    var tdCreditHit = $(this).children("[name='creditHid']");//得到隐藏积分列
		    var tdPayType = $(this).children("[name='operate']").children("[name='payType']");//得到select
		    tdPayType.val(2);//设置为积分付款
		    var tdCreditHitValue = tdCreditHit.text().trim()*1;
		    tdCredit.text(tdCreditHitValue);
		    tdMoney.text(0);
		  });
	//计算总价同时增加结算行
	 sumRowAccounts();
	 addCountRow(sumMoney,sumCredit);
	 
	//设置结账方式
	checkType = 2;
	//结账运算
	checkOutput();
 	
 }
 //混合结账 现金和积分
 function admixPayfor(){
	//遍历所有tr获取值
	 table.find("tr").each(function(index,g){
		    var tdMoney = $(this).children("[name='money']");//得到money列
		    var tdCredit = $(this).children("[name='credit']");//得到积分列
		    var tdMoneyCre = $(this).children("[name='moneyCre']");//得到积分的现金隐藏列
		    var tdCreditMon = $(this).children("[name='creditMon']");//得到积分的积分隐藏列
		    var tdPayType = $(this).children("[name='operate']").children("[name='payType']");//得到select
		    tdPayType.val(3);//设置为现金和积分
		    var tdMoneyCreValue = tdMoneyCre.text().trim()*1;
		    var tdCreditMonValue = tdCreditMon.text().trim()*1;
		    tdMoney.text(tdMoneyCreValue);
		    tdCredit.text(tdCreditMonValue);
		  });
	//计算总价同时增加结算行
	 sumRowAccounts();
	 addCountRow(sumMoney,sumCredit);
	 
	//设置结账方式
	checkType = 3;
	
	//结账运算
	checkOutput();
 	
 }
 //进行运算收入金额和找零
 function checkOutput(){
		var inputVal = $("#calculateInput").val();
		if(inputVal == ""){
			$("#calculateOutput").text("");
			return;
		}
			
	 	if(sumMoney > inputVal){
			$.ligerDialog.warn("收入金额数小于结账总金额数！");
			$("#calculateOutput").text("");
		}else{
			$("#calculateOutput").text(inputVal*1 - sumMoney*1);
		} 
 }
 //获取商品信息放在数组中 同时获取收入金额和找零
 function  addToGoodsStr(){
	 //获取商品信息
	 table.find("tr").each(function(index,g){
		    var tdCode = $(this).children("[name='code']");//得到商品编码
		    var tdName = $(this).children("[name='goodsName']");//得到商品名称
		    var tdMoney = $(this).children("[name='money']");//得到money列
		    var tdCredit = $(this).children("[name='credit']");//得到积分列
		    var numberHis = $(this).children("[name='numberTd']").children("[name='number']"); //得到数量列
		    var tdPayType = $(this).children("[name='operate']").children("[name='payType']");//得到select
		    if("" != tdCode.text().trim() && 'undefined' != tdPayType.val()){
		    	 var str = tdCode.text().trim() +"#"+ tdName.text().trim() +"#"+tdMoney.text().trim() +"#"+tdCredit.text().trim()+"#"+ numberHis.val()+"#"+tdPayType.val();
				 goodsStr.push(str);
		    }
	});
	//获取结账的收入金额，积分和找零
	 receiveMoney = $("#calculateInput").val();
	 changeMoney =  $("#calculateOutput").text();
	 receiveCredit = $("#calculateCreditInput").val();	 
 }
 //向后台发送日志请求
 function addGoodsLog(){
	 var memberNo = $("#memberNo").text();
	$.post("${ctx}/goods/goodsLog/save.do",{goodsInfo:goodsStr.toString(),checkType:checkType,countMoney:sumMoney,countCredit:sumCredit,receiveMoney:receiveMoney,changeMoney:changeMoney,receiveCredit:receiveCredit,memberNo:memberNo,isUserMember:isUserMember},function(result){
		//还原数组为空
		goodsStr = [];
	});
 }
 //确认按钮操作  
 function checkOutGoods(){
	 	
	 
	
	 
	   //进行商品信息后台保存操作 并还原数组
		addToGoodsStr();
		 
		 
	   //判断有无商品商品
	   if(goodsStr.length==0){
		  return;
	   } 
	   //判断收入金额
	   var inputVal = $("#calculateInput").val();
	   if(inputVal == ""){
		   inputVal = 0;
		}
	   if(sumMoney > inputVal){
			$.ligerDialog.warn("收入金额数小于结账总金额数！");
			return;
		}
	   
	   //判断是否使用会员
	   if($("#useMember").attr("checked")){//使用
		   if($("#memberNo").text() == ''){
			   $.ligerDialog.warn("没有会员登录！");
			   return
		   }
		   //判断会员积分
		  var  memberCredit = $("#memberCredit").text(); 
		   if(memberCredit < (sumCredit - receiveCredit)){
			   $.ligerDialog.warn("会员积分不足！");
			   return;
		   }
		   //设置使用会员
		   isUserMember = 2;
		   
		}else{//不使用
			//判断收入积分
			   if(receiveCredit < sumCredit){
				   $.ligerDialog.warn("收入积分小于总积分！");
				   return;
			   }
		}
	   //判断结束
	   
	   //alert(goodsStr);
	   
	   //增加商品到日志记录
		addGoodsLog();
		
		//延迟执行函数
		setTimeout("reloadInfor()",200);

 }
 
 //刷新页面信息
 function reloadInfor(){
	 window.location.href = "${ctx}/goods/check/list.do";
		//return false;
	event.preventDefault();
 }
 
 //子页面弹出框
 function dialogWarn(infor){
	 $.ligerDialog.warn(infor);
 }
 //子页面回调获取会员信息
 function callBackMember(member){
	$("#memberName").text(member.name);
	$("#memberCredit").text(member.valueMnu);
	$("#memberNo").text(member.memberNo);
	$("#useMember").attr("checked",'checked');//登录后会自动选择使用积分
 }
 
</script>
<style type="text/css">
.btn_payfor {
  /*  background: rgba(0, 0, 0, 0) url("${ctx}/resources/images/blue-big.jpg") no-repeat scroll 0 0; */
  background: #3873F2  no-repeat scroll 0 0;
    border: medium none;
    color: #ffffff;
    font-size: 14px;
    font-weight: bold;
    height: 24px;
    line-height: 24px;
    margin-right: 15px;
    text-align: center;
    vertical-align: middle;
    width: 82px;
}

.btn_payforMix {
   background: #3873F2  no-repeat scroll 0 0;
    border: medium none;
    color: #ffffff;
    font-size: 14px;
    font-weight: bold;
    height: 24px;
    line-height: 24px;
    margin-right: 15px;
    text-align: center;
    vertical-align: middle;
    width: 100px;
}
#calculateDiv{
 margin-right:150px; 
*margin-right:150px; 
background-color:#D1D1D1;
width:350px;
height:66px;
position:fixed;
bottom:70px;
right:0;
font-size: 16px;
 padding-top:15px; 
 padding-left: 80px;


}
.credit{
margin-right:150px; 
*margin-right:150px; 
background-color:#D1D1D1;
width:350px;
height:80px;
position:fixed;
bottom:0;
right:0;
font-size: 16px;
padding-left: 80px;
}
.queren{
background-color:#D1D1D1;
width:150px;
height:151px;
position:fixed;
bottom:0;
right:0;
}
.btn_queren{
 background: #3873F2  no-repeat scroll 0 0;
  border: medium none;
  color: #ffffff;
  font-size: 18px;
  font-weight: bold;
  height: 20px;
  line-height: 24px;
  margin-right: 30px;
  margin-top: 15px;
  text-align: center; 
  width: 85px; 
  padding: 20px;
}
#countDiv{
/* background-color:#EDEDED; */
background-color:#D1D1D1;
width:400px;
height:70px;
position:fixed;
bottom:0;
left:0;
font-size: 25px;
padding-left:30px;
}

#countTypeDiv{
background-color:#D1D1D1;
width:50%;
height:135px;
position:fixed;
bottom:0;
left:0;
font-size: 16px;
padding-top:16px;
padding-right:180px;
}
body, html {
    height: 100%;
}
.top{
position: fixed;
top: -5px;
width: 100%;
z-index: 100;
background: #ffffff none repeat scroll 0 0;
border: 1px solid #ccc;
display: block;
margin-top: 0;
overflow: visible;
} 
/*  #checkGoods{
margin-top: 62px;
margin-bottom: 135px;
}  */

</style>
<body id="body">
<div class='top' id="top">
<fieldset class="fieldset">
	<legend class="legend">商品查询</legend>
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
	<tr>	
		<td align="center"><div style='font-weight:bold;display:inline;'>会员姓名：</div><div id="memberName" style='font-weight:bold;display:inline;'></div></td>
		<td align="center"><div style='font-weight:bold;display:inline;'>会员积分：</div><div id="memberCredit" style='font-weight:bold;display:inline;'></div></td>
		<td align="center"><div style='font-weight:bold;display:inline;'>会员号：</div><div id="memberNo" style='font-weight:bold;display:inline;'></div></td>
	</tr>
		<tr>
			<td width="15%" align="right">商品条码：</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="code" id="code" value="${goods.code}" /></td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" onclick="asynQueryGoods()" /> 
			</td>
		</tr>
	</table>
</fieldset>
</div>
	<table id="checkGoods" cellpadding="0" class="blues" cellspacing="0">
	<thead>
		<tr>
			<th >商品编码</th>
			<th>商品名称</th>
			<th>价格(元)</th>
			<th>积分(卷)</th>
			<th>数量</th>
			<th>操作 </th>
		</tr>
	</thead>
	</table>
   <div id="countTypeDiv">
 	<table id='payForTable' border='0' cellpadding='2' cellspacing='1' width='100%' ><tr><td align='center'><input type='button' value='现金结账' class='btn_payfor'  onclick='cashPayfor()'/></td> <td  align='center'><input type='button' value='积分结账' class='btn_payfor' onclick='numericalPayfor()' /></td> <td align='center'><input type='button' value='现金和积分结账' class='btn_payforMix' onclick='admixPayfor()' /></td> </tr></table>
 </div>	 
  <div id='countDiv'>
  		<div style='float:left;font-weight:bold'>总价:&nbsp;</div>
  		<div style='float:left;' id="totalDiv">0&nbsp;元&nbsp;0&nbsp;卷</div>
  </div> 
   <div class="queren"><div class='btn_queren'onclick="checkOutGoods()">确认</div></div>
   <div id='calculateDiv'>
 	<div ><div style='font-weight:bold;float:left;'>收入金额:&nbsp;</div><input style="width:20%;float:left;" type='text' value='' id='calculateInput' /></div><div style='float:left;'><div style='font-weight:bold;float:left;'>&nbsp;&nbsp;&nbsp;找零:&nbsp;&nbsp;</div><div id='calculateOutput' style="float:left;font-size: 20px;margin-top: -2px;"></div>  
 	</div>
 </div>  
<div class="credit"><div style='font-weight:bold;float:left;'>收入积分:&nbsp;</div><input style="width:20%;float:left;" type='text' value='0' id='calculateCreditInput' />
	<div style='font-weight:bold;float:left;'>&nbsp;&nbsp;使用会员积分:&nbsp;&nbsp;</div><div style="float:left;"><input style="width:10px;height:20px;" type="checkbox" name="useMember" id="useMember" /></div>  
</div>
 
    <div style="height:33px;width:80%;background-color: #D1D1D1;position:fixed;bottom:0;text-align:center;font-size: 16px;">
            'F1'会员登录，'Esc'清空页面所有数据，'Enter'确认输入
   </div> 

</body>
</html>
