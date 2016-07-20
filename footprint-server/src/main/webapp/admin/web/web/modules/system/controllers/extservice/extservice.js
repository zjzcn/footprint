angular.module("ng-admin").controller("ExtServiceCtrl", ["$scope", function($scope) {

    $("#selectTreeOrder_ext").chosen({
        no_results_text : "未查询到结果", // 未检索到结果时显示的文字
        search_contains : true, // 是模糊搜索还是精确搜索
        disable_search_threshold : 10,
        width: "120px"
    });

    Tree.init({
        "container" : $("#select-tree-id"),
        "stopLevel" : 2
    }, function(obj) {
    });

    var vm = $scope;
    vm.data1 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['function01','function02','function03','function04'],
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
                    formatter: '{value} ms'
                }
            }
        ],
        series : [
            {
                name:'function01',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[12, 12.5, 1.2, 1.26, 7.5, 1.00,1.35, 1.11]
            },
            {
                name:'function02',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[7.20, 1.23, 1.10, 1.20, 4.50, 8.6, 1.12, 0.9]
            },
            {
                name:'function03',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[]
            },
            {
                name:'function04',
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
            data:['update111'],
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
                    formatter: '{value} cpm '
                }
            }
        ],
        series : [
            {
                name:'update111',
                type:'line',
                data:[1.2,2.3, 1.6, 3.5, 7.5, 5.3, 5.8, 1.6]
            }
        ]
    };
}]);