angular.module("ng-admin").controller("SlowWebProcessCtrl", ["$scope", "$stateParams", "WebProcessService", function($scope, $stateParams, WebProcessService) {
    var vm = $scope;
    vm.init = function() {
        LoadData();
    }
    vm.legendName = [];
    vm.options = {
        tooltip : {
            trigger: 'item',
            axisPointer : {
                type : 'shadow'
            }
        },
        legend: {
            data:[]
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
                data : ['耗时(ms)']
            }
        ],
        series : []
    };
    function LoadData() {
        var para = {
            "traceId": $stateParams.traceId
        };
        WebProcessService.getTraceInfo(para).then(function(data) {
            if(data.success == 1) {
                vm.uri = data.result.webTraceInfo.uri;
                vm.elapsedTime = data.result.webTraceInfo.elapsedTime;
                vm.timestamp = data.result.webTraceInfo.timestamp;
                vm.agentId = data.result.webTraceInfo.agentId;
                vm.licenseId = data.result.webTraceInfo.licenseId;
            }
            var para1 = {
                "licenseId": vm.licenseId
            }
            WebProcessService.getAppName(para1).then(function(data) {
                if(data.success == 1) {
                    vm.appName = data.result.appName
                }
            })
            var para2 = {
                "agentId": vm.agentId
            }
            WebProcessService.getAgentName(para2).then(function(data) {
                if(data.success == 1) {
                    vm.agentName = data.result.agentName;
                }
            })
            var param = {
                "agentId": 1003,
                "url": vm.uri,
                "traceTime": vm.timestamp
            }
            WebProcessService.getTraceSummary(param).then(function(data) {
                if(data.success == 1) {
                    vm.webTraceList = data.result.webTraceSummary;
                    for(var i=0; i<vm.webTraceList.length; i++) {
                        vm.legendName[i] = vm.webTraceList[i].className + ':' +vm.webTraceList[i].methodName;
                        vm.options.legend.data.push(vm.legendName[i]);
                        vm.options.series.push({
                            name: vm.legendName[i],
                            type:'bar',
                            stack: '总量',
                            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                            data: [vm.webTraceList[i].invokeTime]
                        })
                    }
                    console.log(JSON.stringify(vm.options));
                }
            });
            WebProcessService.getTraceDetail(param).then(function(data) {
                if(data.success == 1) {

                }
            })
        });
    }


}]);