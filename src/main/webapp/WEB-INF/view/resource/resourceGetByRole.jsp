<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<%@page language="java" pageEncoding="UTF-8"%>
<head>
    <title>分配权限分配</title>
        <script type="text/javascript">

    	//树
		var resourceTree;
		$(function()
		{
			loadTree();
			
			$("#save").click(function() {
						
				// 获取发生改变的节点
				var nodes = resourceTree.getCheckedNodes();
				var strRs = JSON.stringify(nodes);
			
				if (nodes!=null && nodes.length>0) {
					$.ajax({
						// 请求使用的动作
				        type: 'post',
				        // 处理请求的URI
				        url: '${ctx}/resource/resource/update.do?roleId='+$("#roleId").val(),
				        // 传递给服务端的数据（参数）
				        data: {'res': strRs},
				     
				        // 服务端返回的数据格式，一般是json,text,xml
				        dataType: 'text',
				   
				        error: function() {
				        	alert("出错了！！");
				        },
				        success: function(data) {
				        	alert("修改成功！！");
				        }
				        
					});
					  
				}
			});
			
		});
		
		//加载树
		function loadTree(){
			var setting = {
					  check: {  
				            enable: true  
				        },  
				data: {
					key : {			
						name: "title"
					},	
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: 100
					}
				},
				async: {
					enable: true,
					url:'${ctx}/resource/resource/getRoleTree.do?roleId='+$("#roleId").val(),
					autoParam:["id"]			
				}
			};		
			resourceTree=$.fn.zTree.init($("#resourceTree"), setting);
		};
		
	</script>
</head>
<body>
<div id="layout">	

	<div id="popupDiv">
		<div class="titleDiv">角色名称：&nbsp;&nbsp;${role.name }
		<input type="hidden" value="${role.id }" id="roleId">
		</div>
	</div>
	<div id="resourceTree" class="ztree" style="overflow:auto;height:100%;clear:both;"></div>
	<input type="button" value="保存" id="save" class="l-button l-button-submit"/>
</div>

</body>
</html>

        