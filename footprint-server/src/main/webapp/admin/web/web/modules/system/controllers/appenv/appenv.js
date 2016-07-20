angular.module("ng-admin").controller("AppenvCtrl", ["$scope", "AppEnvService", function($scope, AppEnvService) {
    var vm = $scope;
    var para = {
        registerId: 9
    }
    AppEnvService.getAgentList(para).then(function(data) {
        if(data.success == 1) {
            vm.agents = data.result.agentInfo;
        }
        var para1 = {
            agentId: vm.agents[0].id
        }
        AppEnvService.getAgentInfo(para1).then(function(data) {
            vm.detail = data.result.envInfo;
        });
    })

    vm.selectItem = function(item) {
        vm.agents.forEach(function(it) {
            delete it.checked;
        });
        item.checked = !item.checked;

        var para1 = {
            agentId: item.id
        }
        AppEnvService.getAgentInfo(para1).then(function(data) {
            vm.detail = data.result.envInfo;
        });

    };
}]);