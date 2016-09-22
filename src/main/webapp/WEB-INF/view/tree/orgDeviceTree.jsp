<%@page pageEncoding="UTF-8"%>
<html>
<head>
<title>组织选择</title>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
	<script type="text/javascript">
		var isSingle=${isSingle};
		var districtTree=null; 
		var orgTree=null; 
		var accordion = null;
		$(function() {
			$("#defLayout").ligerLayout({
				leftWidth: 190,
				allowRightResize:false,
				allowLeftResize:false,
				allowTopResize:false,
				allowBottomResize:false,
				height: '90%',
				minLeftWidth:170,
				rightWidth:170
			});
			
			var height = $(".l-layout-center").height();
			$("#leftMemu").ligerAccordion({ height: height-28, speed: null });
		    accordion = $("#leftMemu").ligerGetAccordionManager();
		    
		    load_org_tree();
		    
 		    heightChanged();
 		   	handleSelects();
		});
		function heightChanged(options){
			if(options){   
			    if (accordion && options.middleHeight - 28 > 0){
				    $("#SEARCH_BY_ORG").height(options.middleHeight - 163);
			        accordion.setHeight(options.middleHeight - 28);
			    }
			}else{
			    var height = $(".l-layout-center").height();
			    $("#SEARCH_BY_ORG").height(height - 163);
		    }
		}
		
		
		//组织机构树
		function load_org_tree(){
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
					url:"${ctx}/user/org/getTreeDate.do",
					autoParam:["id"]
				},
				callback:{
					onClick: orgTreeOnClick
				}
			};	
			orgTree=$.fn.zTree.init($("#SEARCH_BY_ORG"), setting);
			
		};
		
		function orgTreeOnClick(event, treeId, treeNode){
			if (treeNode) {
			var url="${ctx}/device/entity/getDeviceByOrgId.do?orgId=" + treeNode.path+"&isSingle="+${param.isSingle}+"&status="+${param.status};
			$("#deviceListFrame").attr("src",url);
			}
		}
		
		function add(data) {
			var aryTmp=data.split("#");
			var orgCode=aryTmp[0];
			var len= $("#org_" + orgCode).length;
			if(len>0) return;
			
			var aryData=['<tr id="org_'+orgCode+'">',
				'<td>',
				'<input type="hidden" class="pk" name="deviceData" value="'+data +'"> ',
				aryTmp[1],
				'</td>',
				'<td><a class="link del" onclick="javascript:del(this); " ></a> </td>',
				'</tr>'];
			$("#deviceList").append(aryData.join(""));
		};
		
		function selectMulti(obj) {
			if ($(obj).attr("checked") == "checked"){
				var data = $(obj).val();
				add(data);
			}	
		};
	
		function selectAll(obj) {
			var state = $(obj).attr("checked");
			var rtn=state == undefined?false:true;
			checkAll(rtn);
		};
		
		function checkAll(checked) {
			$("#deviceListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
				$(this).attr("checked", checked);
				if (checked) {
					var data = $(this).val();
					add(data);
				}
			});
		};	
		
		function dellAll() {
			$("#deviceList").empty();
		};
		function del(obj) {
			var tr = $(obj).parents("tr");
			$(tr).remove();
		};		
		function selectUser(){
			var chIds;
			if(isSingle==true){
				chIds = $('#deviceListFrame').contents().find(":input[name='deviceData'][checked]");
			
			}else{
				chIds = $("#deviceList").find(":input[name='deviceData']");
			}
			
			var userIds=new Array();
			var userNames=new Array();
			
			$.each(chIds,function(i,ch){
				var aryTmp=$(ch).val().split("#");
				if(aryTmp[0] != '') {
					userIds.push(aryTmp[0]);
					userNames.push(aryTmp[1]);
				}
			});
			
			var obj={orgId:userIds.join(","),orgName:userNames.join(",")};
			window.returnValue=obj;
			window.close();
		}
		
		var handleSelects=function(){
			var obj = window.dialogArguments;
			if(obj&&obj.length>0){
				for(var i=0,c;c=obj[i++];){
					var data = c.id+'#'+c.name;
					if(c.name!=undefined&&c.name!="undefined"&&c.name!=null&&c.name!=""){
						add(data);
					}
				}
			}
		}	
	</script>
</head>
<body>
	<div id="defLayout">
		<div id="leftMemu" position="left" title="查询维度" style="overflow: auto; float: left;width: 100%;">	
			<div title="组织机构维度" style="overflow-y:auto;">
				<div id="SEARCH_BY_ORG" class="ztree" ></div>
			</div>		
		</div>
		<div position="center">
			<div class="l-layout-header">设备列表</div>
			<iframe id="deviceListFrame" name="deviceListFrame" height="95%" width="100%"	frameborder="0" src=""></iframe>
		</div>
		<c:if test="${param.isSingle==false}">
			   <div position="right" title="<span><a onclick='javascript:dellAll();' class='link del'>清空 </a></span>" style="overflow: auto;height:95%;width:170px;">
          			<table width="145"   class="table-grid table-list"  cellpadding="1" cellspacing="1">
          				<tbody id="deviceList">
          					<tr class="hidden"></tr>
          				</tbody>
					</table>
			  </div>
		</c:if>
	</div>
	 <div position="bottom"  class="bottom" style='margin-top:10px;'>
		<a href='#' class='button' onclick="selectUser()" style="margin-right:10px;" ><span class="icon ok"></span><span >选择</span></a>
		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a> 
	 </div>
</body>
</html>