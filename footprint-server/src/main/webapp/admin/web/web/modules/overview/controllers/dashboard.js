angular.module("ng-admin").controller("OverviewDashboardCtrl", 
    ["$scope","AlertService","OverviewDashboardService","GetChartsService","$rootScope",
    function($scope,AlertService,OverviewDashboardService, GetChartsService, $rootScope) {
    var vm = $scope;

    vm.delDashboard = function(){
        AlertService.confirm({
            title:"删除视图",
            content:"你确定要删除该视图吗？"
        }).then(function(){
            
        });
    };

    vm.data1 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['Web事务','后台任务','数据库','外部服务'],
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
                type : 'category',
                boundaryGap : false,
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} s'
                }
            }
        ],
        series : [
            {
                name:'Web事务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[1250, 125, 120, 126, 750, 100,135, 111]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[720, 123, 110, 120, 450, 86, 112, 90]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            }
        ]
    };
    vm.data2 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['Web事务','后台任务','数据库','外部服务'],
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
                type : 'category',
                boundaryGap : false,
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} s'
                }
            }
        ],
        series : [
            {
                name:'Web事务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[1250, 125, 120, 126, 750, 100,135, 111]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[720, 123, 110, 120, 450, 86, 112, 90]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            }
        ]
    };
    vm.data3 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['Web事务','后台任务','数据库','外部服务'],
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
                type : 'category',
                boundaryGap : false,
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} s'
                }
            }
        ],
        series : [
            {
                name:'Web事务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[1250, 125, 120, 126, 750, 100,135, 111]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[720, 123, 110, 120, 450, 86, 112, 90]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            },
            {
                name:'后台任务',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            }
        ]
    };
    vm.data5 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['ucd-ty-app-demo-1']
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
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
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
        series : [
            {
                name:'ucd-ty-app-demo-1',
                type:'line',
                data:[0, 0, 0, 0, 0, 0, 0, 0]
            }
        ]
    };
    vm.data6 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['ucd-ty-app-demo-1']
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
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                name: '(MB)',
                axisLabel : {
                    formatter: '{value} k '
                }
            }
        ],
        series : [
            {
                name:'ucd-ty-app-demo-1',
                type:'line',
                data:[2, 2, 2, 2, 2, 2, 2, 2]
            }
        ]
    };

/*    vm.datalist = [{
        data: "",
        title: ""
    }]
    function LoadEcharts() {
        var para = {

        };
        OverviewDashboardService.getChartsType(para).then(function(data) {
            if(data.success == 1){
                vm.key = [];
            }
        })
        for(var i=0; i<vm.key.length; i++) {
            var para1 = {

            };
            if(vm.key[i] == ""){
                OverviewDashboardService.getRespTime_all(para1).then(function(data) {
                    if(data.success == 1){
                        vm.datalist[i].data = data.result.xxxList;
                        vm.datalist[i].title = data.result.xxxtitle;
                    }
                })
            }else if(vm.key[i] == ""){
                OverviewDashboardService.getthroughput_all(para1).then(function(data) {
                    if(data.success == 1){
                        vm.datalist[i].data = data.result.xxxList;
                        vm.datalist[i].title = data.result.xxxtitle;
                    }
                })
            }
        }
    }*/

    vm.dashboardDatas = [{
        title: "test1",
        tip: "xxxx1",
        moduleName: "getResponseTime",
        params: {
            appKey: "11111111",
            time:"最近三十分钟"
        }
    },{
        title: "test2",
        tip: "xxxx2",
        moduleName: "throughput",
        params: {
            appKey: "11111112",
            time:"最近三十分钟"
        }
    }];

    //示意代码
    if($rootScope.dashboardData){
        angular.forEach($rootScope.dashboardData, function(v, key){
            vm.dashboardDatas.push(v);
        });
    }

    vm.charts = [];

    angular.forEach(vm.dashboardDatas, function(v, key){
        var chart = {};
        chart.title = v.title;
        chart.tip = v.tip;
        chart.data = eval("GetChartsService." + v.moduleName + "(v.params)");
        vm.charts.push(chart);
    });

}]);