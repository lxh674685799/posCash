<%@include file="/commons/include/get.jsp" %>
<%@page language="java" pageEncoding="UTF-8"%>
<head>
    <title></title> 
    <script src="${ctx}/resources/js/lg/plugins/ligerPanel.js" type="text/javascript"></script>
      <script src="${ctx}/resources/js/lg/plugins/ligerPortal.js" type="text/javascript"></script>
    <script type="text/javascript">
        var manager;
        $(function ()
        {
            $("#portalMain").ligerPortal({
                columns: [{
                    panels: [
                    {
                        title: '通知公告',
                        width: 1000,
                        height: 320,
                        url: '${ctx}/home/portal/noticeList.do'
                    }
                    ]
                }]
            }); 
        }); 
    </script>

</head>
<body style="padding:10px">

 	<div style="width:100%;"> 上的发送到发送到双方都放大f  //创建两个大的按钮  进行 充值和收银
 	
 	发的发的是
 	</div> 

        <div style="width:100%;" id="portalMain"> </div> 
</body>
</html>
