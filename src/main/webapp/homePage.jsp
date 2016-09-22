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
                    width: 580,
                    panels: [{
                        title: '内部消息',
                        width: '100%',
                        height: 300,
                        url: '${ctx}/home/portal/messageList.do'
                    },
                    {
                        title: '通知公告',
                        width: 1170,
                        height: 350,
                        url: '${ctx}/home/portal/noticeList.do'
                    }
                    ]
                }, {
                    width: 580,
                    panels: [{
                        title: '任务列表',
                        width: '100%',
                        height: 300,
                        url: '${ctx}/home/portal/taskList.do'
                    }
                    ]
                }]
            }); 
        }); 
    </script>

</head>
<body style="padding:10px">
        <div style="width:100%;" id="portalMain"> 
        </div> 
</body>
</html>
