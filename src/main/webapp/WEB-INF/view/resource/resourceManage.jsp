<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<%@page language="java" pageEncoding="UTF-8"%>
<head>
    <title>系统资源管理</title>
        <script type="text/javascript">
		//菜单
		var menu;
		//树节点是否可点击
		var treeNodelickAble=true; 
		
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
				allowLeftResize: true
			});
			//取得layout的高度
	        var height = $(".l-layout-center").height();
	        $("#resourceTree").height(height-60);
		};
		//布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	$("#resourceTree").height(options.middleHeight - 60);
	    };

		//树
		var resourceTree;
		//加载树
		function loadTree(){
			var setting = {
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
					url:"${ctx}/resource/resource/getTreeDate.do",
					autoParam:["id"]			
				},
				callback:{
					onClick: zTreeOnLeftClick,
					onRightClick: zTreeOnRightClick,
					beforeClick:zTreeBeforeClick
				}
			};
			
			resourceTree=$.fn.zTree.init($("#resourceTree"), setting);

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
				resourceTree.selectNode(treeNode);
				 menu.show({ top: e.pageY, left: e.pageX });
			}
		};

		function addNode(){
			var superId =resourceTree.getSelectedNodes()[0].id;	
			$("#listFrame").attr("src","edit.do?superId="+superId);	
		};
		
		function delNode(){
			var isParent =resourceTree.getSelectedNodes()[0].isParent;	
			var parentId =resourceTree.getSelectedNodes()[0].parentId;	
		 	var callback = function(rtn) {
				if(rtn){
					var resourceId =resourceTree.getSelectedNodes()[0].id;	
					$("#listFrame").attr("src","del.do?resourceId="+resourceId+"&isParent="+isParent+"&parentId="+parentId);
					resourceTree.removeNode(resourceTree.getSelectedNodes()[0]);
				}
			};
			if(isParent){
				$.ligerMessageBox.confirm('提示信息','确定删除该资源下所有资源？',callback);
			}else{
				$.ligerMessageBox.confirm('提示信息','确认删除吗？',callback);
			}	
		};
	</script>

</head>
<body>
<div id="layout">
	<div position="left" title="系统资源">
		<div id="resourceTree" class="ztree" style="overflow:auto;height:100%;clear:both;"></div>
	</div>
	<div position="center">
	<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
	</div>
</div>
</body>
</html>

        