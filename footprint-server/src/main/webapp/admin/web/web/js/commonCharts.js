angular.module("ng-admin").service("GetChartsService", ["HttpService", function(HttpService) {
    return {
    	"getResponseTime": function(params) {
        	var option = {};
        	option.legendData = ['web服务响应时间','app服务响应时间'];
        	option.xAxisData = ['18:10','18:15','18:20','18:25'];
        	option.seriesData = [];
        	option.yAxisUnit = "ms";
        	option.toolTipFormatter = function (params,ticket,callback) {
                var res = params.seriesName + '<br/>' +params[1];
                res += '<br/>当前值: ' + params.value + "ms";
                var maxV = Math.max.apply(Math,params.series.data);
                res += '<br/>最大值: ' + maxV + "ms";
                return res;
            }
        	var seriesData = [[120, 125, 120, 126],[45, 86, 112, 90]];

        	angular.forEach(seriesData, function(d, k){
        		
				option.seriesData.push({
					name : option.legendData[k],
					type : 'line',
					smooth : true,
					itemStyle: {normal: {areaStyle: {type: 'default'}}},
					data : d
				});

        	});	
        	return setChartsOption(option);
            //return HttpService.get("", params);
        },
        "throughput": function(params) {
        	var option = {};
        	option.legendData = ['web服务吞吐率','app服务吞吐率','SQL吞吐率'];
        	option.xAxisData = ['18:10','18:15','18:20','18:25'];
        	option.seriesData = [];
        	option.yAxisUnit = "rmp";
        	option.toolTipFormatter = function (params,ticket,callback) {
                var res = params.seriesName + '<br/>' +params[1];
                res += '<br/>当前值: ' + params.value + "ms";
                var maxV = Math.max.apply(Math,params.series.data);
                res += '<br/>最大值: ' + maxV + "ms";
                return res;
            }

        	var seriesData = [[ 125, 120, 126, 100],[ 110, 120, 86, 112],[ 125, 112, 153, 146]];

        	angular.forEach(seriesData, function(d, k){
				option.seriesData.push({
					name : option.legendData[k],
					type : 'line',
					smooth : true,
					data : d
				});

        	});	

        	return setChartsOption(option);
            //return HttpService.get("", params);
        }
    }
}]);


/**
* 设置折线图option params 
*    toolTipFormatter:formatter tooltip的回调函数
*	 legendData	     :legendData数组
*    xAxisData	     :X坐标数组
*	 seriesData	     :series数组
*    yAxisUnit       :y轴坐标
**/
var setChartsOption = function(params){

	var option = {
		tooltip : {
			trigger : 'item',
			borderWidth : 1,
			formatter : params.toolTipFormatter
		},
		toolbox : {
			y : 15,
			show : true,
			feature : {
				mark : {
					show : false
				},
				dataView : {
					show : true,
					readOnly : true
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : false
				},
				saveAsImage : {
					show : true
				}
			}
		},
		legend : {
			x : 'center',
			y : 'bottom',
			data : params.legendData
		},
		grid : {
			x : 60,
			x2 : 40,
			y2 : 80
		},
		xAxis : [ {
			type : 'category',
            boundaryGap : false,
            data : params.xAxisData
		} ],
		yAxis : [ {
			type : 'value',
			axisLabel :  {
				formatter : function(value) {
					return value + params.yAxisUnit;
				}
			}
		} ],
		series : params.seriesData
	};

	return option;
}