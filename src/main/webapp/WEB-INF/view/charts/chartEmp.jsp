<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/page.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type="text/javascript" src="${ctx }/resources/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${ctx }/resources/js/echarts/echarts.min.js"></script>
<html>
<head>
<title>销售业绩统计</title>

<script type="text/javascript">
var myChart ;
var option;

$(function(){

	 var yearStr = document.getElementById('yearStr');
	 yearStr.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM',onpicked:function(dp){loadChart();}});
	  }
	 
	// 基于准备好的dom，初始化echarts实例
     myChart = echarts.init(document.getElementById('main'));
	
     myChart.showLoading({  
         text: '正在努力加载中...'  
     });
     
     option = {
    		    title : {
    		        text: '销售业绩',
    		        },
    		    legend: {
    		        data:['现金','点卷']
    		    },
    		    calculable : true,
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : []
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
    		             scale:true,
    		             axisLabel : {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'现金',
    		            type:'bar',
    		            data:[],
    		            markPoint : {
    		                data : [
    		                    {type : 'max', name: '最大值'},
    		                    {type : 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name: '平均值'}
    		                ]
    		            }
    		        },
    		        {
    		            name:'点卷',
    		            type:'bar',
    		            data:[],
    		            markPoint : {
    		              data : [
    		                    {type : 'max', name: '最大值'},
    		                    {type : 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name : '平均值'}
    		                ]
    		            }
    		        }
    		    ]
    		};
	
     loadChart();
});


function loadChart(){
	var yearS = $("#yearStr").val();
	 $.ajax({
			url : "${ctx }/charts/chart/statisticByEmp.do?yearMonth="+yearS,
			dataType : "json",
			success : function(data1) {
				 if(data1 != ''){	
					 option.title.text = yearS+"销售业绩";
				
					 option.xAxis[0].data =data1.userNames;
					 option.series[0].data = data1.totalMoney;
					 option.series[1].data = data1.totalCredit;				 
					
					myChart.setOption(option);
					myChart.hideLoading();
				}else{
					myChart.showLoading({
					    text : '暂无数据',
					    effect : 'bubble',
					    textStyle : {
					        fontSize : 30
					    }
					});		
				}	 
				}
			});
}
</script>

<body>
<fieldset class="fieldset">
	<legend class="legend">销售业绩统计</legend>
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="10%" align="right">日期:</td>
			<td width="25%" align="left">
			<input type="text" class="text" name="yearStr" id="yearStr" value="${yearMonth}" /></td>
			
		</tr>
	</table>
</fieldset>
   <div id="main" style="width:95%;height:430px;"></div>
</body>

</html>