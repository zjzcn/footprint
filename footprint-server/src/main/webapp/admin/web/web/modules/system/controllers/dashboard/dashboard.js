angular.module("ng-admin").controller("SystemDashboardCtrl", ["$scope", function($scope) {
    var vm = $scope;
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
}]);