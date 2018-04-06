$(function(){
//图表
  // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        var colors = ['#f00'];
        var colors1 = ['#ff7575'];
        var option = {
            color: colors,
            title: {
//              text: '当日数据统计'
            },
            tooltip: {},
            legend: {
                data:['当日应发现','当日已发现']
            },
           xAxis: {
                data : ['10.1号','10.2号','10.3号','10.4号','10.5号','10.6号','10.7号','10.8号','10.9号','10.10号','10.11号','10.12号','10.13号','10.14号','10.15号','10.16号','10.17号','10.8号','10.19号','10.20号','10.21号','10.22号','10.23号','10.24号','10.25号','10.26号','10.27号','10.28号','10.29号','10.30号']
            },
            yAxis: {},
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 30
                }, {
                 start: 0,
                 end: 30,
                 handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                 handleSize: '80%',
                 handleStyle: {
                  color: '#fff',
                   shadowBlur: 3,
                   shadowColor: 'rgba(0, 0, 0, 0.6)',
                   shadowOffsetX: 2,
                   shadowOffsetY: 2
                  }
                }],
            series: [
                    {
                        color: colors,
                     name:'当日应发现',
                     type: 'bar',
                     data: [800,100,500,400,300,200,100,500,400,300,200,100,800,100,500,400,300,200,100,500,400,300,200,100,800,100,500,400,300,200],
                  },
                  {
                        color: colors1,
                     name:'当日已发现',
                     type: 'bar',
                     data: [800,100,500,400,300,200,100,500,400,300,200,100,800,100,500,400,300,200,100,500,400,300,200,100,800,100,500,400,300,200],
                  }
            ],
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        var myChart = echarts.init(document.getElementById('inan'));
        // 指定图表的配置项和数据
        var colors = ['#f00'];
        var colors1 = ['#ff7575'];
        var option = {
            color: colors,
            title: {
//              text: '当月数据统计'
            },
            tooltip: {},
            legend: {
                data:['当月应发现','当月已发现']
            },
            xAxis: {
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
//              "type":"category",  
//				        "axisLabel":{  
//				            interval: 0  
//				        },
//隔行显示
            },
            yAxis: {},
//          dataZoom: [{
//              type: 'inside',
//              start: 30,
//              end:100
//              }, {
//               start: 30,
//               end:100,
//               handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
//               handleSize: '80%',
//               handleStyle: {
//                 color: '#fff',
//                 shadowBlur: 3,
//                 shadowColor: 'rgba(0, 0, 0, 0.6)',
//                 shadowOffsetX: 2,
//                 shadowOffsetY: 2
//                }
//              }],
            series: [
                    {
                    color: colors,
                     name:'当月应发现',
                     type: 'bar',
                     data: [800,100,500,400,300,200,100,500,400,300,400,300],
                  },
                  {
                    color: colors1,
                     name:'当月已发现',
                     type: 'bar',
                     data: [800,100,500,400,300,200,100,500,400,300,400,300],
                  }
            ],
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);  
				//滨图 -------------------------------------------
				var myChart = echarts.init(document.getElementById('bintu'));
				        // 指定图表的配置项和数据
				var colors = ['#f00'];
        var colors1 = ['#ff7575'];
				var colors2 = ['#ffcc00'];        
	       option = {
	        tooltip: {
	            trigger: 'axis'
	        },
	        toolbox: {
	            feature: {
	//              dataView: {show: true, readOnly: false},
	//              magicType: {show: true, type: ['line', 'bar']},
	//              restore: {show: true},
	//              saveAsImage: {show: true}
	            }
	        },
	        legend: {
	            data:['蒸发量','降水量','平均温度']
	        },
	        xAxis: [
	            {
	                type: 'category',
	                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	            }
	        ],
	        yAxis: [
	            {
	                type: 'value',
	                name: '水量',
	                min: 0,
	                max: 250,
	                interval: 50,
	                axisLabel: {
	//                formatter: '{value} ml'
	                }
	            },
	            {
	                type: 'value',
	//              name: '温度',
	//              min: 0,
	//              max: 25,
	//              interval: 5,
	                axisLabel: {
	//              formatter: '{value} °C'
	                }
	            }
	        ],
	        series: [
	            {
	              	color: colors,
	                name:'蒸发量',
	                type:'bar',
	                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	            },
	            {
	              	color: colors1,
	                name:'降水量',
	                type:'bar',
	                data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
	            },
	            {
	            	 color: colors2,
	                name:'平均温度',
	                type:'line',
	                yAxisIndex: 1,
	                data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	            }
	        ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);   
        var myChart = echarts.init(document.getElementById('inan-2'));
        // 指定图表的配置项和数据
        var colors = ['#f00'];
        var colors1 = ['#ff7575'];
			  option = {
	        tooltip: {
	            trigger: 'axis'
	        },
	        toolbox: {
	            feature: {
	//              dataView: {show: true, readOnly: false},
	//              magicType: {show: true, type: ['line', 'bar']},
	//              restore: {show: true},
	//              saveAsImage: {show: true}
	            }
	        },
	        legend: {
	            data:['蒸发量','平均温度']
	        },
	        xAxis: [
	            {
	                type: 'category',
	                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	            }
	        ],
	        yAxis: [
	            {
	                type: 'value',
	                name: '水量',
	                min: 0,
	                max: 250,
	                interval: 50,
	                axisLabel: {
	//                formatter: '{value} ml'
	                }
	            },
	            {
	                type: 'value',
	//              name: '温度',
	//              min: 0,
	//              max: 25,
	//              interval: 5,
	                axisLabel: {
	//              formatter: '{value} °C'
	                }
	            }
	        ],
	        series: [
	            {
	              	color: colors,
	                name:'蒸发量',
	                type:'bar',
	                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	            },
	            {
	            	 color: colors2,
	                name:'平均温度',
	                type:'line',
	                yAxisIndex: 1,
	                data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	            }
	        ]
        };
       // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);  
       //折线图 
        var myChart = echarts.init(document.getElementById('broken'));
        // 指定图表的配置项和数据
          var colors = ['#ff7575'];
        	var colors1 = ['#f00'];
      	  var option = {
            color: colors,
            title: {
//              text: '当月数据统计'
            },
             tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['邮件营销','半夜传销']
				    },
				    toolbox: {
				        feature: {
				//          saveAsImage: {} //保存下载为图片格式
				        }
				    },
				    grid: {
			        left: '3%',
//				    right: '10%',
			        bottom: '15%',
				      containLabel: true,
				    },
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
//				             "type":"category",  
//							        "axisLabel":{  
//							            interval: 0  
//							        },
//隔行显示
				        }
				    ],
				    yAxis : [{
           	 type : 'value'
     			  }],
    series : [
        {
            name:'邮件营销',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
              data: [100,200,150,250,200,300,100,400,200,300,200,260],
        },
         {
    			 	color: colors1,
            name:'半夜传销',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [100,200,150,250,200,300,100,400,200,300,200,260],
        },       
    ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);  
        
});
