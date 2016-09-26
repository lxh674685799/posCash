<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<html>
<head>
<title>编辑设备信息</title>

<script type="text/javascript">

$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			name:{required:true},
			typeName:{required:true},
			factoryId:{required:true},
			code:{required:true,number:true},
			inPrice:{required:true,number:true},
			credit:{required:true,number:true},
			money:{required:true,number:true},
			moneyCre:{required:true,number:true},
			creditMon:{required:true,number:true}
		},
		messages:{
			name:{required:"请输入商品名称！"},
			typeName:{required:"请选择商品类别！"},
			factoryId:{required:"请选择供货商！"},
			code:{required:"请输入商品条码！",number:"条码只能为数字！"},
			inPrice:{required:"请输入进货价！",number:"进货价只能为数字！"},
			credit:{required:"请输入销售积分！",number:"积分只能为数字！"},
			money:{required:"请输入销售金额！",number:"金额只能为数字！"},
			moneyCre:{required:"请输入销售金额！",number:"金额只能为数字！"},
			creditMon:{required:"请输入销售积分！",number:"积分只能为数字！"}
		}
	});
});

var zTree;
function loadzTree(){
	var setting = {
		data: {
			key : {			
				name: "name"
			},	
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId"
			}
		},
		async: {
			enable: true,
			url:'${ctx}/device/type/getTreeDate.do',
			autoParam:["id"]
		},
		callback:{
			onClick: onClick,
			beforeClick: beforeClick
		}
	};
	zTree = $.fn.zTree.init($("#typeTree"), setting);

};
$(function(){

$("html").bind("mousedown", 
		function(event){
			if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
				hideMenu();
			}
		});	
});

function showMenu() {
	var cityObj = $("#typeName");
	var cityOffset = $("#typeName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	loadzTree();
}
function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function beforeClick(treeId, treeNode) {
	zTree.expandNode(treeNode);
	if (treeNode.isParent) {		
		return (treeNode.click = false);
	}
}

function onClick(event, treeId, treeNode) {
	if (treeNode) {		
		$("#typeId").attr("value", treeNode.id);
		$("#typeName").attr("value", treeNode.name);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('typeId').value = '';
	document.getElementById('typeName').value = '';
}

</script>
</head>
<body>
    <form name="form" method="post" action="save.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${goods.id == null }">
			<legend class="legend">添加商品信息</legend>
		</c:if>
		<c:if test="${goods.id != null }">
			<legend class="legend">修改【${goods.name }】信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${goods.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
          <input type="hidden" name="createTime" value="${goods.createTime}">
           <input type="hidden" name="userId" value="${goods.userId}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">商品名称:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name" value="${goods.name }" /></td>               
            </tr>       
            <tr>
                <td align="right" class="tabRight">商品类型:</td>
                <td align="left" class="left">
				<input id="typeName" type="text" name="typeName" readonly value="${goods.type.name }"  
							 onclick="showMenu(); return false;" /> 
				 <input type="hidden" name="typeId" id="typeId" value="${goods.type.id }"></td>             
            </tr>  
             <tr>
                <td align="right" class="tabRight">供货商:</td>
                <td align="left" class="left">
               <select id="factoryId" name="factoryId">
				<option value="">--请选择供货商--</option>
				<c:forEach var="f" items="${factorys }">
				<option value="${f.id }" <c:if test="${goods.factory.id==f.id  }">selected</c:if> >${f.name }</option>
				</c:forEach>
				</select>
                </td>        
            </tr> 
             <tr>
                <td align="right" class="tabRight">商品条码:</td>
                <td align="left" class="left">
                <input name="code" type="text" id="code"  <c:if test="${not empty goods.code}">readonly = "readonly" </c:if> value="${goods.code }" /></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">进货价:</td>
                <td align="left" class="left">
                <input name="inPrice" type="text" id="inPrice" value="${goods.inPrice}" /></td>               
            </tr> 
            <tr>
                <td align="center"  colspan="2"  bgcolor="#AEDCE6">销售价（现金）</td>
              </tr> 
            <tr>
                <td align="right" class="tabRight">现金(元):</td>
                <td align="left" class="left">
                <input name="money" type="text" id="money" value="${goods.money}" /></td>               
            </tr> 
            <tr>
                <td align="center"  colspan="2" bgcolor="#AEDCE6">销售价（积分）</td>
              </tr> 
            <tr>
            	 <td align="center" class="tabRight" >积分:</td>
                <td align="left" class="left" colspan="2">
                <input name="credit" type="text" id="credit" value="${goods.credit }" /></td>               
            </tr> 
            <!-- #d1ddf1 -->
             <tr>
                <td align="center"  colspan="2" bgcolor="#AEDCE6">销售价（现金+积分）</td>
              </tr>
            <tr >
                <td align="right" class="tabRight" >现金(元):</td>
                <td align="left" class="left">
                <input name="moneyCre" type="text" id="moneyCre" value="${goods.moneyCre }" />
                </td> 
            </tr> 
            <tr >
                <td align="right" class="tabRight" >积分:</td>
                <td align="left" class="left">
                <input name="creditMon" type="text" id="creditMon" value="${goods.creditMon }" />
                </td> 
            </tr> 
            <%-- <tr>
                <td align="right" class="tabRight">库存（件）:</td>
                <td align="left" class="left">
                <input name="inventory" type="text" id="inventory" value="${goods.inventory }" /></td>               
            </tr>  --%>
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${goods.remark }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="保存" id="Button1" class="l-button l-button-submit"/> 
			<input type="button" value="返回" class="l-button l-button-back" onclick="back()" />
			</td>
            </tr>      
        </table>
         <br />	
        </fieldset>

    </form>
    <div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="typeTree" class="ztree"></ul>
</div>
    
</body>
</html>
