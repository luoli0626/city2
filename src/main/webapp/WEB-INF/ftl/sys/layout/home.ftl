<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<script src="${static}/js/echarts.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/jquery1.js" charset="UTF-8" type="text/javascript"></script>
<link rel="stylesheet" href="${static}/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="${static}/bootstrap/css/tjcss.css" />
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<#include "/inc/easyui-portal.ftl"/>
<script type="text/javascript" charset="UTF-8">
var portal;
var option;
var option1;
var option2;
var option3;
var option4;
function getOption3(data1,data2,data3,data4){
 var myChart = echarts.init(document.getElementById('main3'));
   option3 = {
  title: {
        text: '当月完成率'
    },
    color:['#27a9e3','#e7191b'],
    tooltip: {
        trigger: 'axis'
    },
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    legend: {
        data:['完成量','未完成','完成率']
    },
    xAxis: [
        {
            type: 'category',
            
            data: data1
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '数量',
            minInterval:50,
            axisLabel: {
                formatter: '{value} 件'
            }
        },
        {
            type: 'value',
            name: '百分比',
            max:100,
            interval: 10,
            axisLabel: {
                formatter: '{value} %'
            }
        }
    ],
    series: [
        {
            name:'完成量',
            type:'bar',
            stack: '总量',
            barWidth : 40,
            data:data2
        },
        {
            name:'未完成',
            type:'bar',
            stack: '总量',
            barWidth : 40,
            data:data3
        },
        {
            name:'完成率',
            type:'line',
            yAxisIndex: 1,
            data:data4
        }
    ]
};

myChart.setOption(option3);
}

function getOption4(data1,data2){
 var myChart = echarts.init(document.getElementById('main4'));
  option4 = {
    title: {
        text: '年度走势'
    },
    color:['#ffb848','#27a9e3'],
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['关键区域','关键点']
    },
    toolbox: {
        show: true,
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
    },
    yAxis: {
        type: 'value',
        minInterval:50,
        axisLabel: {
            formatter: '{value} 件'
        }
    },
    series: [
        {
            name:'关键区域',
            type:'line',
            data:data1,
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'关键点',
            type:'line',
            data:data2,
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        }
    ]
};

myChart.setOption(option4);
}

function getOption1(data1,data2,data3,data4){

 var myChart = echarts.init(document.getElementById('main'));
option1 = {
title: {
        text: data4
    },
     toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    color:['#e7191b','#ffb848'],
    legend: {
        data:['发现量','整改量']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type: 'value',
            minInterval:50
        }
    ],
   yAxis: {
        type: 'category',
        data: data1
    },
    series : [
        {
            name:'发现量',
            type:'bar',
            barWidth : 10,
            data:data2
        },
        {
            name:'整改量',
            type:'bar',
            barWidth : 10,
            data:data3
        }
    ]
};
 myChart.setOption(option1);
}

function getOption2(data){
var myChart3 = echarts.init(document.getElementById('main1'));
option2 = {
    title: {
        text: '该时间段总体情况'
    },
     toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    tooltip: {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    color:['#e7191b','#ffb848','#27a9e3','#28b779'],
    legend: {
        data:['问题发现量','问题整改量','问题审核量','问题审核/抽查率']
    },
    xAxis: [
        {
		  　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　 　
            type: 'category',
             data: [data.dateTime],
					axisLabel: {
					    interval:0,  
                        //rotate:45,//倾斜度 -90 至 90 默认为0  
                        margin:2, 
                        show: true
                    }
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '数量',
            minInterval:50,
            axisLabel: {
                formatter: '{value} 件'
            }
        },
        {
            type : 'value',
            name : '审核/抽查率',
            minInterval:20,
            axisLabel : {
                formatter: '{value} %'
            }
        
        }
    ],
    series: [
        {
            name:'问题发现量',
            type:'bar',
             barWidth : 40,
            data:[data.totalNumber]
        },
        {
            name:'问题整改量',
            type:'bar',
             barWidth : 40,
            data:[data.rectificationNum]
        },
        {
            name:'问题审核量',
            type:'bar',
             barWidth : 40,
            data:[data.checkNum]
        }
		,
        {
            name:'问题审核/抽查率',
            type:'bar',
             barWidth : 40,
			yAxisIndex: 1,
            data:[data.checkAvg]
        }
		
    ]
};

 myChart3.setOption(option2);

}


function getOption(data1,data2,data3){
 var myChart = echarts.init(document.getElementById('main2'));
option = {
 title: {
        text: '个人任务情况'
    },
     toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    color:['#e7191b','#ffb848'],
    legend: {
        data:['应发现问题数量','实际发现问题数量']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : data1,
					axisLabel: {
					    interval:0,  
                        rotate:45,//倾斜度 -90 至 90 默认为0  
                        margin:2, 
                        show: true
                    }
        }
    ],
    yAxis : [
        {
            type : 'value',
            minInterval:5
        }
    ],
    series : [
        {
            name:'应发现问题数量',
            type:'bar',
            barWidth : 20,
            data:data2
        },
        {
            name:'实际发现问题数量',
            type:'bar',
            barWidth : 20,
            data:data3,
            markLine : {
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        }
    ]
};
 myChart.setOption(option);
}
function loadCount(){
   if($('#startTime').datebox('getValue')==''){
      alert("请选择开始时间！");
      return;
   }
   if($('#endTime').datebox('getValue')==''){
      alert("请选择结束时间！");
      return;
   }
   $.ajax({
			type: "POST",
			url: "${ctx}/manual/userTotalCount?startTime="+$('#startTime').datebox('getValue')+"&endTime="+$('#endTime').datebox('getValue'),
			dataType : "json",
			success: function(data){
                if(data.userType=="2"){
                   getOption2(data);
                   $('#dateTime').css("display","block");
                }
            }             
		 });
    $.ajax({
			type: "POST",
			url: "${ctx}/manual/userCount?startTime="+$('#startTime').datebox('getValue')+"&endTime="+$('#endTime').datebox('getValue'),
			dataType : "json",
			success: function(data){
                var data1 = [];
                var data2 = [];
                var data3 = [];
                var data4 = [];
                var data5 = [];
                var data6 = [];
                var data7 = [];
                var userType="";
                for (var i = 0; i < data.length; i++) {
                var $data = data[i];
                    userType=$data.userType;
                    if(userType=="1"){
                        data1.push($data.dateTime);
	                    data2.push($data.totalNumber);
	                    data3.push($data.shouldTotalNumber);
                    }else if(userType=="2"){
	                    data1.push($data.userName);
	                    data2.push($data.totalNumber);
	                    data3.push($data.rectificationNum);
                    }else if(userType=="3"){
                     if(i<5){
	                    data1.push($data.userName);
	                    data2.push($data.totalNumber);
	                    data3.push($data.rectificationNum);
	                    }
                    }else if(userType=="4"){
	                    data1.push($data.userName);
	                    data2.push($data.totalNumber);
	                    data3.push($data.rectificationNum);
                    }
                }
                if(userType=="1"){
                getOption(data1,data3,data2);
                $('#count1').css("display","none");
                $('#count2').css("display","none");
                }else if(userType=="2"){
                 $('#count2').css("display","none");
                 getOption1(data1,data2,data3,'该时段项目人员统计');
                
                }else if(userType=="3"){
                   $('#count2').css("display","none");
                   getOption1(data1,data2,data3,'该时段项目统计(Top5)');
                }else if(userType=="4"){
                   getOption1(data1,data2,data3,'该时段分公司统计(Top5)');
                   //getOption3();
                   //getOption4();
                }
                
                
            }             
		 });
   

}
function callback(data) {
	     alert(data);
     }
$(function() {

    /* $.ajax({
            url:"http://183.230.100.132:8080/BaseInfoService/ReturnJsonDateController/getArrearageListDataGrid?page=1&rows=10&buildingId=abf8a576-9b4c-4eaa-aa30-6a13a3b43719%2Cf4f86e33-2e5a-4b45-8121-52288a487437&month=&key=潘",
            dataType:'jsonp',
            //processData: false,
            jsonp: "callbackparam",
            jsonpCallback:"callback",
            //jsonpCallback:"callback",
            crossDomain:true,
            success : function(json){
               alert(json);
            }
        });

        $.ajax({
			type: "POST",
			url: "${ctx}/manual/plateCount",
			dataType : "json",
			success: function(data){
			    var data1 = [];
                var data2 = [];
                var data3 = [];
                var data4 = [];
                for(var i = 0; i < data.length; i++) {
                 var $data = data[i];
                   data1.push($data.orgNae);
                   data2.push($data.hangNum);
                   data3.push($data.referenceValue);
                   data4.push($data.score);
                }
                getOption3(data1,data2,data3,data4);
            }             
		 });
		 
		$.ajax({
			type: "POST",
			url: "${ctx}/manual/trendCount",
			dataType : "json",
			success: function(data){
			    var data1 = [];
                var data2 = [];
                var data3 = [];
                
                for(var i = 0; i < data.length; i++) {
                 var $data = data[i].obj;
                   if(data[i].name=="关键区域"){
                      for(var j=0;j<$data.length;j++){
                       var $dataobj = $data[j];
                        data1.push($dataobj.totalNumber);
                      }
                   }
                   if(data[i].name=="关键点"){
                      for(var j=0;j<$data.length;j++){
                       var $dataobj = $data[j];
                        data2.push($dataobj.totalNumber);
                      }
                   }
                }
                getOption4(data1,data2);
            }             
		 });

$.ajax({
			type: "POST",
			url: "${ctx}/manual/userTotalCount",
			dataType : "json",
			success: function(data){
                if(data.userType=="2"){
                  getOption2(data);
                  $('#dateTime').css("display","block");
                }
            }             
		 });
     $.ajax({
			type: "POST",
			url: "${ctx}/manual/userCount",
			dataType : "json",
			success: function(data){
                var data1 = [];
                var data2 = [];
                var data3 = [];
                var data4 = [];
                var data5 = [];
                var data6 = [];
                var data7 = [];
                var userType="";
                for (var i = 0; i < data.length; i++) {
                var $data = data[i];
                    userType=$data.userType;
                    if(userType=="1"){
                        data1.push($data.dateTime);
	                    data2.push($data.totalNumber);
	                    data3.push($data.shouldTotalNumber);
                    }else if(userType=="2"){
	                    data1.push($data.userName);
	                    data2.push($data.totalNumber);
	                    data3.push($data.rectificationNum);
                    }else if(userType=="3"){
                     if(i<5){
	                    data1.push($data.userName);
	                    data2.push($data.totalNumber);
	                    data3.push($data.rectificationNum);
	                    }
                    }else if(userType=="4"){
                     
	                    data1.push($data.userName);
	                    data2.push($data.totalNumber);
	                    data3.push($data.rectificationNum);
                    }
                }
                
                if(userType=="1"){
                getOption(data1,data3,data2);
                 $('#count2').css("display","none");
                $('#count1').css("display","none");
                }else if(userType=="2"){
                   $('#count2').css("display","none");
                   getOption1(data1,data2,data3,'该时段项目人员统计');
                }else if(userType=="3"){
                   $('#count2').css("display","none");
                   $('#count3').css("display","none");
                   getOption1(data1,data2,data3,'该时段项目统计(Top5)');
                }else if(userType=="4"){
                   $('#count3').css("display","none");
                   getOption1(data1,data2,data3,'该时段分公司统计');
                   //getOption3();
                   //getOption4();
                }
                
                
            }             
		 });*/
    



 
	/*var div12=$("#div12").panel({    
	    title: '历史任务',
	    height:250,
	    closable: true,    
	    collapsible: true   
	});*/
	var div11=$("#div11").panel({    
	    title: '待办任务',
	    height:250,
	    closable: true,    
	    collapsible: true,
	    href:'${ctx}/main/taskList' 
	});
	var div13=$("#div13").panel({    
	    title: '${(org3)!''}${(org2)!''}${(org1)!''}',
	    height:450,
	    closable: true,    
	    collapsible: true,
	    href:'${ctx}/main/noticeListInfo'
	});
	var div21=$("#div21").panel({    
	    title: '工作任务及统计',
	    height:250,
	    closable: true,    
	    collapsible: true,
	     href:'${ctx}/main/reportFrom' 
	});
});
function aaa(path,text){ 
	if ( null != path  && path != '') {
		var href;
		if (/^\//.test(path)) {/*以"/"符号开头的,说明是本项目地址*/
			href = path.substr(1);
		} else {
			href = path;
		}
		top.addTabFun({
			src : href,
			title :text
		});
	} 
}
</script>
<style>
.panel{
border: 1px solid #cbcbcb;
border-top: 0px;
}
.panel-header{
    width: 100% !important;
	padding:5px;
	line-height:20px;
	color:#535353;
	font-weight:bold;
	border-right: 0px !important;
    border-left: 0px !important;
	font-size:12px;
	position:relative;
	border:1px solid #cdcdcd;
	background:url('../static/js/jquery-easyui-1.2.6/themes/default/images/title_bk_001.jpg') no-repeat;
	
}

.aa{

}
.panel-tool{
	position:absolute;
	right:5px;
	top:3px;
}

.panel-title{
	padding-left:13px;	
}

.panel-body{
	overflow:auto;
	border:1px solid #cbcbcb;
	border-top-width:0px;
	border-top:none;
	color:#535353;
	font-size:12px;
	border: none;
	
	
}
.table_sy{
    width:98%;
	border-collapse:separate;
	table-layout:fixed;
	word-spacing:normal;
	border-spacing:0;
	margin:5px 1%;
	border-spacing:2px 5px;
}

.table_sy tr td{
}
</style>
</head>
<body class="easyui-layout" >
	 <div region="center" border="false" style="border:1px solid #FFF;" >
	 	<table style="width:98%;border-collapse:separate;border-spacing:5px;">
	 		<tr>
	 			<!--
	 			<td style="width:33%;vertical-align:top"><div id="div12"></div></td>
	 			<td style="width:33%;vertical-align:top"><div id="div13"></div></td>-->
	 			<td colspan="3" style="width:70%;"><div id="div11" class="aa"></div></td>
	 		</tr>
	 		<#if (Session.currentUser)??>
	 		<#if Session.currentUser.id!=0>
	 		<tr>
	 			<td  colspan="3" style="width:70%;">
	 				<div  id="div13"></div>
	 			</td>
	 		</tr>
	 		</#if>
	 		</#if>
	 		<!--<tr id="dateTime" style="display:none;">
	 			<td colspan="3" style="width:70%;">
	 				<div style="width: 100%;height:50px;text-align:center;">日期：<input name="startTime" id="startTime" style="width: 120px;height:20px;" class="easyui-datebox" />-<input name="endTime" id="endTime" style="width: 120px;height:20px;"  class="easyui-datebox" />&nbsp;<a href="javascript:loadCount();" class="easyui-linkbutton" plain="true" iconCls="icon-search" >统计</a></div>
	 			</td>
	 		</tr>
	 		
	 		<tr id="count1">
	 			<td colspan="3" style="width:70%;">
	 				<div id="main" style="width: 49%;height:500px;float:left;"></div><div id="main1" style="width: 49%;height:500px;float:left;"></div>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="3" style="width:70%;" id="count3">
	 				<div id="main2" style="width: 98%;height:500px;"></div>
	 			</td>
	 		</tr>
	 		<tr id="count2">
	 			<td colspan="3" style="width:70%;">
	 				<div id="main3" style="width: 49%;height:500px;float:left;"></div><div id="main4" style="width: 49%;height:500px;float:left;"></div>
	 			</td>
	 		</tr>-->
	 		
	 	</table>
	</div>
</body>
</html>