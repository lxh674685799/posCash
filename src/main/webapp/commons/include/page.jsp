<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://www.lxh.cn/functions" %>
<!-- 分页-->
<script src="${ctx}/resources/js/page/jquery.simplePagination.js" type="text/javascript" ></script>

<f:link href="simplePagination.css"></f:link>
<script type="text/javascript">
$(function(){
    $("#paging").pagination({
        items: ${page.totalCount},
        itemsOnPage: ${page.pageSize},
        cssStyle: 'light-theme',
        currentPage: ${page.pageIndex},
        onPageClick: function(pageNumber, event) {
        	var form = document.getElementById("searchform");
        	form.action=form.action+"?page="+pageNumber+"&totalCount="+${page.totalCount};
        	form.submit();
			}
    });
});

function checkAll(obj){
    jQuery("[name='id']").attr("checked",obj.checked) ; 			
}

function mycheckbox() { 
	var falg = 0; 
	$("input[name='id']:checkbox").each(function () { 
	if ($(this).attr("checked")) { 
	falg =1; 
	} 
	}) 
	if (falg > 0) {
		$.ligerDialog.confirm("确定要删除数据？", function (yes)
          {if(yes)
			$('#form').submit();
             });
	}
	else {
		$.ligerDialog.warn("请选择要删除的数据！");
	}
}
</script>