angular.module("ng-admin").controller("WebenvCtrl", ["$scope", "WebEnvService", "AlertService", function($scope, WebEnvService, AlertService) {
    var vm = $scope;
    vm.agentList = [];
    var para = {
        appId: 14
    };
    WebEnvService.getNodeInfo(para).then(function(data) {
        if(data.success == 1) {
            vm.agentList = data.result.agentEnvShowList;
        }else{
            AlertService.alert({
                title: "警告",
                content: "获取数据失败"
            })
        }
        vm.tabs = [];
        vm.details = [];
        for(var i=0; i<vm.agentList.length; i++) {
            vm.detail = [];
            vm.tabs.push({
                name: vm.agentList[i].agentName,
                version: vm.agentList[i].version,
                map: vm.agentList[i].envMap
            })
            for(var key in vm.tabs[i].map) {
                var jkey = key;
                var jvalue = vm.tabs[i].map[key];
                vm.detail.push({
                    keyname: jkey,
                    value: jvalue
                })
            }
            vm.details.push(vm.detail);

        }
        console.log(vm.details);
        vm.webenv = vm.details[0];
        vm.selectedTab = vm.tabs[0];
        vm.selectTab = function(tab){
            vm.selectedTab = tab;
            for(var i=0; i<3; i++){
                if(vm.selectedTab == vm.tabs[i]){
                    vm.webenv = vm.details[i];
                }
            }
        };

    });

    vm.init = function() {
    };
}]);