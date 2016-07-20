angular.module("ng-admin").controller('InfoCtrl',["$rootScope", "$state", '$scope', '$filter', 'WebInfoService', "OverviewInfoService",
function($rootScope, $state, $scope, $filter, WebInfoService, OverviewInfoService) {

    var vm = $scope;
    vm.agentList = [];
    var para = {
        appId: 14,
        agentType: "apache"
    };
    OverviewInfoService.getAgentSelectList(para).then(function(data){
        if(data.success == 1){
            vm.agentList = data.result.agentShowList;
        }
    })

    vm.agentType = "web";

    vm.HttpOptions = {
        tooltip : {
            trigger: 'item'
        },
        legend: {
            data:[],
            y: 'bottom'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'time',
                splitNumber : 5,
                boundaryGap : [ 0, 0 ]
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} '
                }
            }
        ],
        series : []
    };
    vm.CPUOptions = {
        tooltip : {
            trigger: 'item'
        },
        legend: {
            data:[],
            y: 'bottom'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'time',
                splitNumber : 5,
                boundaryGap : [ 0, 0 ]
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} % '
                }
            }
        ],
        series : []
    };
    vm.MemOptions = {
        tooltip : {
            trigger: 'item'
        },
        legend: {
            data:[],
            y: 'bottom'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'time',
                splitNumber : 5,
                boundaryGap : [ 0, 0 ]
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} % '
                }
            }
        ],
        series : []
    };
    vm.DiskOptions = {
        tooltip : {
            trigger: 'item'
        },
        legend: {
            data:[],
            y: 'bottom'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'time',
                splitNumber : 5,
                boundaryGap : [ 0, 0 ]
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} % '
                }
            }
        ],
        series : []
    };

    var myDate = new Date();
    var data0 = myDate.getTime()-(30*60*1000);
    var dateFormat = 'yyyy-MM-dd HH:mm:ss';//时间格式
    vm.endTime = $filter('date')(myDate, dateFormat) || '';
    vm.startTime = $filter('date')(data0, dateFormat) || '';
    var para1 = {
        appId: 1,
        agentId: 1,
        startTime: vm.startTime,
        endTime: vm.endTime
    }
    WebInfoService.getHttpData(para1).then(function(data) {
        if(data.success == 1) {
            vm.HTTPLines = data.result.chart.lines;
        }
        for(var i=0; i<vm.HTTPLines.length; i++) {
            vm.HttpOptions.legend.data.push(vm.HTTPLines[i].name);
            var y = [];
            for(var j=0; j<vm.HTTPLines[i].points.length; j++) {
                y.push([vm.HTTPLines[i].points[j].dtX, vm.HTTPLines[i].points[j].valY]);
            }
            vm.HttpOptions.series.push({
                name: vm.HTTPLines[i].name,
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data: y
            })
        }
    })
    WebInfoService.getCPUData(para1).then(function(data) {
        if(data.success == 1) {
            vm.CPULines = data.result.chart.lines;
        }
        for(var i=0; i<vm.CPULines.length; i++) {
            vm.CPUOptions.legend.data.push(vm.CPULines[i].name);
            var y = [];
            for(var j=0; j<vm.CPULines[i].points.length; j++) {
                y.push([vm.CPULines[i].points[j].dtX, vm.CPULines[i].points[j].valY]);
            }
            vm.CPUOptions.series.push({
                name: vm.CPULines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    WebInfoService.getDiskData(para1).then(function(data) {
        if(data.success == 1) {
            vm.DiskLines = data.result.chart.lines;
        }
        for(var i=0; i<vm.DiskLines.length; i++) {
            vm.DiskOptions.legend.data.push(vm.DiskLines[i].name);
            var y = [];
            for(var j=0; j<vm.DiskLines[i].points.length; j++) {
                y.push([vm.DiskLines[i].points[j].dtX, vm.DiskLines[i].points[j].valY]);
            }
            vm.DiskOptions.series.push({
                name: vm.DiskLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    WebInfoService.getMemData(para1).then(function(data) {
        if(data.success == 1) {
            vm.MemLines = data.result.chart.lines;
        }
        for(var i=0; i<vm.MemLines.length; i++) {
            vm.MemOptions.legend.data.push(vm.MemLines[i].name);
            var y = [];
            for(var j=0; j<vm.MemLines[i].points.length; j++) {
                y.push([vm.MemLines[i].points[j].dtX, vm.MemLines[i].points[j].valY]);
            }
            vm.MemOptions.series.push({
                name: vm.MemLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    WebInfoService.getIOList(para1).then(function(data) {
        if(data.success == 1) {
            vm.IOcharts = data.result.chart;
        }
        for(var i=0; i<vm.IOcharts.length; i++){
            vm.IOOptions = [];
            vm.IOOptions[i] = {
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    data:[],
                    y: 'bottom'
                },
                toolbox: {
                    show : true,
                    feature : {
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'time',
                        splitNumber : 5,
                        boundaryGap : [ 0, 0 ]
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value} % '
                        }
                    }
                ],
                series : []
            };
            for(var j=0; j<vm.IOcharts[i].lines.length; j++){
                vm.IOOptions[i].legend.data.push(vm.IOcharts[i].lines[j].name);
                var y = [];
                for(var k=0; k<vm.IOcharts[i].lines[j].points.length; k++){
                    y.push([vm.IOcharts[i].lines[j].points[k].dtX, vm.IOcharts[i].lines[j].points[k].valY]);
                }
                vm.IOOptions[i].series.push({
                    name: vm.IOcharts[i].lines[j].name,
                    type:'line',
                    smooth:true,
                    data: y
                })
            }
            vm.charts.push({
                name:  vm.IOcharts[i].name,
                tip: vm.IOcharts[i].description,
                data: vm.IOOptions[i]
            })
        }
    })

    vm.charts = [{
        name: "HTTP连接数",
        tip: "展示该系统某时间段HTTP连接总数、连接状态（ESTABLISHED,TIME-WAIT）数",
        data: vm.HttpOptions
    },{
        name: "CPU",
        tip: "展示CPU",
        data: vm.CPUOptions
    },{
        name: "物理内存",
        tip: "展示物理内存",
        data: vm.MemOptions
    },{
        name: "磁盘",
        tip: "磁盘",
        data: vm.DiskOptions
    }]

//CPU内存双Y轴
 /*   vm.data3 = {
        tooltip : {
            trigger: 'axis',
            formatter: function(params) {
                return params[0].name + '<br/>'
                    + params[0].seriesName + ' : ' + params[0].value + ' (%)<br/>'
                    + params[1].seriesName + ' : ' + params[1].value + ' (MB)';
            }
        },
        legend: {
            data:['cpu', '内存'],
            x: 'bottom'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                axisLine: {onZero: false},
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                name: 'CPU使用率(%）',
                axisLabel : {
                    formatter: '{value}  '
                }
            },{
                type : 'value',
                name: '内存(MB)',
                axisLabel : {
                    formatter: '{value}  '
                }
            }
        ],
        series : [
            {
                name:'CPU',
                type:'line',
                data:[2, 2, 2, 2, 2, 2, 2, 2]
            },{
                name:'内存',
                type:'line',
  yAxisIndex:1,
                data:[1, 1, 1, 1, 1, 1, 1, 1]
            }
        ]
    };*/

}]);