<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<%@page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx }/resources/systemtip/system_tip.css" type="text/css" />
<script type="text/javascript" src="${ctx }/resources/systemtip/system_tip.js"></script>
<head>
    <title>乐分商城收银系统</title>
        <script type="text/javascript">
            var tab = null;
            var accordion = null;
         
            $(function ()
            {
            	var date = new Date();
        		var str=date.getFullYear()+"年";
        		str+=(date.getMonth()+1)+"月";     
        		str+=date.getDate()+"日";
        		$("#date").text(str);
            	
                //布局
                $("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });

                var height = $(".l-layout-center").height();

                //Tab
                $("#framecenter").ligerTab({ height: height });

                //面板
                $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

                $(".l-link").hover(function ()
                {
                    $(this).addClass("l-link-over");
                }, function ()
                {
                    $(this).removeClass("l-link-over");
                });

              //获取tab的引用
                tab = $("#framecenter").ligerGetTabManager();
				//加载
				loadMenuTree();
                accordion = $("#accordion1").ligerGetAccordionManager();
                
                $("#pageloading").hide();
                
              //初始化提示窗
        		initSystemTip();

            });
            function f_heightChanged(options)
            {
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
            function f_addTab(tabid,text, url)
            { 
                tab.addTabItem({ tabid : tabid,text: text, url: url });
            } 
            
            //加载菜单
            function loadMenuTree(){
            	<c:forEach var="re" items="${resource }">         	
            	var setting = {
        				data: {
        					key : {			
        						name: "title"
        					},	
        					simpleData: {
        						enable: true,
        						idKey: "id",
        						pIdKey: "parentId"
        					}
        				},
        				async: {
        					enable: true,
        					url:"${ctx}/resource/resource/getMenu.do?rootId=${re.id}",
        					autoParam:["id"]			
        				},
        				callback: {
        					onClick: zTreeOnClick
        				}
        			};
        			resourceTree=$.fn.zTree.init($("#leftTree"+"${re.id}"), setting);
                </c:forEach>           	
            }
            
            //处理点击事件
            function zTreeOnClick(event, treeId, treeNode) {
            	
            	var url= treeNode.uri;
            	
            	if(url!=null && url!='' && url!='null'){
            		if(url.slice(0,4) != 'http')
	            	url="${ctx}" +url;
	            	//扩展了tab方法。	            	
	            	addToTab(url,treeNode.title,treeNode.id,treeNode.iconUri);	        	
            	}
            };
            
            //添加到tab或者刷新
            function addToTab(url,txt,id,icon){
            	
            	if(tab.isTabItemExist(id)){
            		tab.selectTabItem(id);
            		tab.reload(id);
            	}
            	else{
            		tab.addTabItem({ tabid:id,text:txt,url:url,icon:icon});
            	}
            };
         	function  initSystemTip(){
          		system_tip.init('system_tip','${ctx }');
	    		system_tip.add(
	    				[{
	    					id:'messageTip',
	    					title:'站内消息',
	    					tip:'站内消息',
	                        level:"info",
	                        interval:600,
	    					url:'${ctx }/system/systemTip/message.do',
	    					contentUrl:'javascript:showMsgList()',
	    					widgetDefault:{intervalable:true}
	    				}]
	    		);
    		}
          	
          	function showMsgList(){ 		
          		var url = "${ctx }/message/message/list.do";
          		addToTab(url,"收到的消息","MsgTip",null);
          	}
          	
          	function showReadMsgDlg(id) {
        			var url = "${ctx}/message/message/get.do";
        			if(typeof id!='undefined'){
        				url += '?id='+id
        			}
        			win = $.ligerDialog.open({
        				allowClose: true,
        				url : url,
        				height : 600,
        				width : 900,
        				isResize : false
        			});
        	}
          	
        	function logout(){
        		var callback = function(rtn) {
    				if(rtn){
    					this.location.href = "${ctx }/system/authority/logout.do";
    				}
    			};
        		$.ligerMessageBox.confirm('提示信息','确定退出？',callback); 		
          	}
    </script> 
<style type="text/css"> 
    body,html{height:100%;}
    body{ padding:0px; margin:0; overflow:hidden;}  
    .l-link{ display:block; height:26px; line-height:26px; padding-left:10px; text-decoration:underline; color:#333;}
    .l-link2{text-decoration:underline; color:black; margin-left:2px;margin-right:2px;}
    .l-layout-top{background:#102A49; color:White;}
    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
    #pageloading{position:absolute; left:0px; top:0px; background:white url('${ctx}/resources/images/loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
    .l-link{ display:block; line-height:22px; height:22px; padding-left:16px;border:1px solid white; margin:4px;}
    .l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;} 
    .l-winbar{ background:#2B5A76; height:30px; position:absolute; left:0px; bottom:0px; width:100%; z-index:99999;}
    /* 顶部 */ 
    .l-topmenu{ margin:0; padding:0; height:80px; line-height:50; background:url('${ctx}/resources/images/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #1D438B;  }
    .l-topmenu-logo{  padding-left:45px; line-height:46px; font-size: 25px;}
    .l-topmenu-welcome{  position:absolute; height:30px; line-height:24px;  right:30px; top:2px; color:#ffffff;}

 </style>
</head>
<body style="padding:0px;background:#EAEEF5;">  
<div id="pageloading"></div>  
   <div class="top_banner">
				当前日期：<strong id="date"></strong>|		
				用户：<strong>--${user.name}--</strong> |
				单位：<strong>--${user.post.name}--</strong>
	</div>	
<div id="topmenu" class="l-topmenu">
    <div class="l-topmenu-logo" style="color:#ffffff; ">乐分商城智能收银系统</div>
    <div class="l-topmenu-welcome">
        <a href="javascript:;"  onclick="addToTab('${ctx}/user/user/changeInfo.do?id=${user.id}','修改个人信息','','');"  style="color:#ffffff; ">修改用户信息</a> 
        <span class="space">|</span>
         <a href="javascript:;" onclick="logout();" style="color:#ffffff; ">退出登录</a>
    </div> 
    
</div>

  <div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
        <div position="left"  title="功能菜单" id="accordion1" >         
         <c:forEach var="re" items="${resource }">
            <div title="${re.title }" class="l-scroll" >                
             <ul id="leftTree${re.id }" class="ztree" style="height: 100%" ></ul>               
           </div>
         </c:forEach>
        </div>
        <div position="center" id="framecenter"> 
            <div tabid="home" title="首页" style="height:300px" >
                <iframe frameborder="0" name="home" id="home" src ="${ctx }/homePage.jsp"></iframe>
            </div> 
        </div>     
    </div>
  </body>

</html>