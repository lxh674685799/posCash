<%@include file="/commons/include/get.jsp" %>
<%@page language="java" pageEncoding="UTF-8"%>
<head>
    <title></title> 
    <script src="${ctx}/resources/js/lg/plugins/ligerPanel.js" type="text/javascript"></script>
      <script src="${ctx}/resources/js/lg/plugins/ligerPortal.js" type="text/javascript"></script>
    <script type="text/javascript">
        var manager;
 
        
        function memberTopUp(){
        	var c = screen.availHeight - 35;
        	var a = screen.availWidth - 5;
        	var e = "top=0,left=0,height="
        			+ c
        			+ ",width="
        			+ a
        			+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
        	window.open("${ctx}/user/member/list.do?forUp=true", "", e, true);
        	};
        	
       	function checkList(){
       		var c = screen.availHeight - 35;
        	var a = screen.availWidth - 5;
        	var e = "top=0,left=0,height="
        			+ c
        			+ ",width="
        			+ a
        			+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
        	window.open("${ctx}/goods/check/list.do", "", e, true);
        	};
        	
          	function checkList1(){
           		var c = screen.availHeight - 35;
            	var a = screen.availWidth - 5;
            	var e = "top=0,left=0,height="
            			+ c
            			+ ",width="
            			+ a
            			+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
            	window.open("${ctx}/goods/check/list1.do", "", e, true);
            	};
        	
               	function checkList2(){
               		var c = screen.availHeight - 35;
                	var a = screen.availWidth - 5;
                	var e = "top=0,left=0,height="
                			+ c
                			+ ",width="
                			+ a
                			+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
                	window.open("${ctx}/goods/check/list2.do", "", e, true);
                	};
    </script>
<style>

#nav {
    line-height:30px;
        float:left;
    background-color:#3873F2;
    width:120px;
    padding:40px;
 color: #ffffff;
 text-align: center; 
 font-size: 45px;
  font-weight: bold;
left:20px;      
}
#section {
    line-height:30px;
 background-color:#3873F2;
    width:120px;
    float:left;
    padding:40px;
	color: #ffffff;
	 text-align: center; 
	font-size: 45px;
  font-weight: bold;	
  position:relative;
left:20px; 	
}

#section1 {
    line-height:30px;
 background-color:#3873F2;
    width:120px;
    float:left;
    padding:40px;
	color: #ffffff;
	 text-align: center; 
	font-size: 45px;
  font-weight: bold;	
  position:relative;
left: 40px;

}

#section2 {
    line-height:30px;
 background-color:#3873F2;
    width:120px;
    float:left;
    padding:40px;
	color: #ffffff;
	 text-align: center; 
	font-size: 45px;
  font-weight: bold;	
  position:relative;
left:60px; 	 
}
</style>

</head>
<body style="padding:10px">

 <div id="nav" onclick="memberTopUp()">
  会员</br></br>充值
</div>

<div id="section" onclick="checkList()">
 收银</br></br>结账
</div>


<div id="section1" onclick="checkList2()">
 VIP</br></br>结账
</div>

<div id="section2" onclick="checkList1()">
 赠送</br></br>退货
</div>

</body>
</html>
