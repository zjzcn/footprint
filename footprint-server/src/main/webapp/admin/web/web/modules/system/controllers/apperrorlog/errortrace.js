angular.module("ng-admin").controller("ErrorTraceCtrl", ["$scope", function($scope) {
    var vm = $scope;
    vm.data1 = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {
                type : 'shadow'
            }
        },
        legend: {
            data:['aaa', 'bbb','ccc','ddd','qqq']
        },
        calculable : true,
        xAxis : [
            {
                type : 'value'
            }
        ],
        yAxis : [
            {
                type : 'category',
                data : ['耗时']
            }
        ],
        series : [
            {
                name:'aaa',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:[12]
            },
            {
                name:'bbb',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:[20]
            },
            {
                name:'ccc',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:[25]
            },
            {
                name:'ddd',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:[29]
            },
            {
                name:'qqq',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:[31]
            }
        ]
    };

}]);