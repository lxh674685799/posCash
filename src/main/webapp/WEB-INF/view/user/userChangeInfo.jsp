<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/vaild.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<html>
<head>
<title>修改用户信息</title>
<script type="text/javascript">

$(function(){
	 //校验
	jqueryUtil.formValidate({
		form:"form",
		rules:{
			name:{required:true},
			phone:{isTel:true},
			email:{email:true},
			postName:{required:true},
			passWord:{required:true,maxlength:18},
			newPwd:{required:true,equalTo:"#passWord"},
			Pwd:{required:true,equalTo:"#oldpw"}
		},
		messages:{
			name:{required:"请输入真实姓名！"},
			phone:{isTel:"请输入正确号码！"},
			email:{email:"请输入正确邮箱！"},
			postName:{required:"请选择所属部门！"},
			passWord:{required:"请输入登录密码！",maxlength:"密码长度小于18位"},
			newPwd:{required:"请输入确认密码！",equalTo:"两次密码填写不一致！"},
			Pwd:{required:"请输入原始密码！",equalTo:"原始密码错误！"}
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
			url:'${ctx}/user/org/getTreeDate.do',
			autoParam:["id"]
		},
		callback:{
			onClick: onClick,
			beforeClick: beforeClick
		}
	};
	zTree = $.fn.zTree.init($("#postTree"), setting);
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
	var cityObj = $("#postName");
	var cityOffset = $("#postName").offset();
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
		$("#postId").attr("value", treeNode.id);
		$("#postName").attr("value", treeNode.name);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('postId').value = '';
	document.getElementById('postName').value = '';
}
</script>

</head>
<body>
    <form name="form" method="post" action="saveChange.do" id="form">
		<fieldset class="fieldset" style="padding: 5px 5px">
		<c:if test="${user.id != null }">
			<legend class="legend">修改【${user.name }】用户信息</legend>
		</c:if>
	
		<!-- 保留字段 -->
         <input type="hidden" name="id" value="${user.id }">
         <input type="hidden" name="returnUrl" value="${returnUrl}">
         
        <table cellpadding="0" cellspacing="0" class="table_add" >
            <tr>
                <td align="right" class="tabRight">登录帐号:</td>
                <td align="left" class="left">
                <input name="account" type="hidden" id="account" value="${user.account }" />
                <input name="account1" type="text" id="account1" value="${user.account }" disabled/></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">所属角色：</td>
                <td align="left" class="left">
                <input name="roleId" type="hidden" id="roleId" value="${user.role.id }"/>
                  <input name="roleName" type="text" id="roleName" value="${user.role.name }" disabled/>          
            </tr>
             <tr>
                <td align="right" class="tabRight">真实姓名:</td>
                <td align="left" class="left">
                <input name="name" type="text" id="name"  value="${user.name}"/></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight" valign="top">性别:</td>
              	 <td align="left" class="left">
                    <input id="sex_man" type="radio" name="sex" value="true" <c:if test="${user.sex}">checked="checked"</c:if> /><label for="rbtnl_0">男</label> &nbsp;&nbsp;&nbsp;&nbsp;
                    <input id="sex_woman" type="radio" name="sex" value="false"  <c:if test="${!user.sex}">checked="checked"</c:if>/><label for="rbtnl_1">女</label>
                </td>
            </tr>
            <tr>
                <td align="right" class="tabRight">联系电话:</td>
                <td align="left" class="left">
                <input name="phone" type="text" id="phone"  value="${user.phone }" /></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">email:</td>
                <td align="left" class="left">
                <input name="email" type="text" id="email"  value="${user.email }" /></td>               
            </tr> 
                <tr>
                <td align="right" class="tabRight">所属部门：</td>
                <td align="left" class="left">
                <input name="postId" type="hidden" id="postId" value="${user.post.id }">
                <input name="postName" type="text" id="postName" readonly
						 onclick="showMenu(); return false;" value="${user.post.name }" /></td>               
            </tr> 
             <tr>
                <td align="right" class="tabRight">登录密码:</td>
                <td align="left" class="left">
                <input name="passWord" type="password" id="passWord"  value="${user.passWord}"/>
                 <input name="oldpw" type="hidden" id="oldpw"  value="${user.passWord}"/>
                </td>               
            </tr>
             <tr>
                <td align="right" class="tabRight">确认登录密码:</td>
                <td align="left" class="left">
                <input name="newPwd" type="password" id="newPwd" /></td>               
            </tr>  
              <tr>
                <td align="right" class="tabRight">确认原始密码:</td>
                <td align="left" class="left">
                <input name="Pwd" type="password" id="Pwd" /></td>               
            </tr> 
              <tr>
                <td align="right" class="tabRight">备注:</td>
                <td align="left" class="left"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:400px">${user.remark }</textarea>
                </td>
            </tr>            
            <tr align="center"><td colspan="2">
            <input type="submit" value="保存" id="Button1" class="l-button l-button-submit"/> 
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
	<ul id="postTree" class="ztree"></ul>
</div>
</body>
</html>
