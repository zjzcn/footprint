angular.module("ng-admin").controller("AppErrorCtrl", ["$scope", "OverviewInfoService", function($scope, OverviewInfoService) {
    var vm = $scope;
    var para = {};
    vm.agentList = [];
    vm.agentList = OverviewInfoService.getAgentSelectList(para).result.agentShowList;
    vm.data1 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['other','127.1.1.1-SpringController','127.0.0.1-HTTP'],
            y:'bottom'
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['10:20','10:25','10:30','10:35','10:40','10:45','10:50']
            }
        ],
        yAxis : [
            {
                name: '执行时间(ms)',
                type : 'value'
            }
        ],
        series : [
            {
                name:'other',
                type:'line',
                data:[120, 132, 101, 134, 90, 230, 210]
            },
            {
                name:'127.1.1.1-SpringController',
                type:'line',
                data:[220, 182, 191, 234, 290, 330, 310]
            },
            {
                name:'127.0.0.1-HTTP',
                type:'line',
                data:[150, 232, 201, 154, 190, 330, 410]
            }
        ]
    };

    vm.data2 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['SpringController/split.do(GET)'],
            y:'bottom'
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['10:20','10:25','10:30','10:35','10:40','10:45','10:50']
            }
        ],
        yAxis : [
            {
                name: '吞吐量(cpm)',
                type : 'value'
            }
        ],
        series : [
            {
                name:'SpringController/split.do(GET)',
                type:'line',
                data:[10, 50, 101, 134, 90, 298, 210]
            }
        ]
    };
}]);