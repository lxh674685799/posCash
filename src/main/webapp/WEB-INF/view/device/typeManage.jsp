<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<%@page language="java" pageEncoding="UTF-8"%>

<%@page import="com.soft.core.web.ResultMessage"%>

<head>
    <title>商品类型列表</title>
        <script type="text/javascript">
		//菜单
		var menu;
		//树节点是否可点击
		var treeNodelickAble=true; 
		//当前访问
		
		var treeNodes = [{"id":-1,"name":"商品类型",isParent:true,"path":"1."}];
		var returnUrl="";
		$(function()
		{
			loadTree();
			layout();
			menu();
		});
		//菜单
		function menu(){
			menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
	        [
	        { text: '增加', click: addNode },
	        { text: '删除', click: delNode }
	        ]
	        });
		};
		
		//布局
		function layout(){
			$("#layout").ligerLayout( {
				leftWidth : 210,
				height:"98%",
				onHeightChanged: heightChanged,
				allowLeftResize:true
			});
			//取得layout的高度
	        var height = $(".l-layout-center").height();
	        $("#typeTree").height(height-60);
		};
		//布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	$("#typeTree").height(options.middleHeight - 60);
	    };

		//树
		var typeTree;
		//加载树
		function loadTree(){
			var setting = {
				data: {
					key : {			
						name: "name"
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
					url:"${ctx}/device/type/getTreeDate.do",
					autoParam:["id"]			
				},
				callback:{
					onClick: zTreeOnLeftClick,
					onRightClick: zTreeOnRightClick,
					beforeClick:zTreeBeforeClick
				}
			};
			
			typeTree=$.fn.zTree.init($("#typeTree"), setting, treeNodes);

		};
		
		//左击前
		function zTreeBeforeClick(treeId, treeNode, clickFlag){
			return treeNodelickAble;
		};
	
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){		
			$("#listFrame").attr("src","edit.do?id="+treeNode.id);
		};
		/**
		 * 树右击事件
		 */
		function zTreeOnRightClick(e, treeId, treeNode) {
			if (treeNode&&!treeNode.notRight) {
				typeTree.selectNode(treeNode);
				 menu.show({ top: e.pageY, left: e.pageX });
			}
		};

		function addNode(){
			var superId =typeTree.getSelectedNodes()[0].id;
			var superPath =typeTree.getSelectedNodes()[0].path;	
			$("#listFrame").attr("src","edit.do?superId="+superId+"&superPath="+superPath);	
		};

		function delNode(){
			var path =typeTree.getSelectedNodes()[0].path;	
			var isParent =typeTree.getSelectedNodes()[0].isParent;	
		 	
			var callback = function(rtn) {
				if(rtn){
					$("#listFrame").attr("src","del.do?path="+path);
					typeTree.removeNode(typeTree.getSelectedNodes()[0]);
					top.CloseWindows(); 
				}
			};

			if(isParent){
				$.ligerMessageBox.confirm('提示信息','确定删除该类型下所有类型？',callback);
			}else{
				$.ligerMessageBox.confirm('提示信息','确认删除吗？',callback);
			}	
		};
	</script>

</head>
<body>
<div id="layout">
	<div position="left" title="商品类型">
		<div id="typeTree" class="ztree" style="overflow:auto;height:100%;clear:both;"></div>
	</div>
	<div position="center">
	<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
	</div>
</div>
</body>
</html>

        