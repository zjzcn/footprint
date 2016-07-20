angular.module("ng-admin").controller('ThresholdSettingCtrl', ["$rootScope", "$scope", "ThresholdService", "AlertService", function ($rootScope, $scope, ThresholdService, AlertService) {
    var vm = $scope;
    var formData = vm.formData = {};
    $rootScope.appKeyChange = function(){
        console.log("setting/threshold---appKeyChange")
    }

    var para = {
        appId: 1
    }
    ThresholdService.getThreshold(para).then(function(data) {
        if(data.success == 1) {
            formData.app_resp1 = data.result.list[0].warnThreshold;
            formData.app_resp2 = data.result.list[0].fatalThreshold;
            formData.error_rate1 = data.result.list[1].warnThreshold;
            formData.error_rate2 = data.result.list[1].fatalThreshold;
        }
    })

    vm.save = function() {
        var para = {
            appId: 1,
            threshold: JSON.stringify([{
                metricCode: "response_time",
                warnThreshold: formData.app_resp1,
                fatalThreshold: formData.app_resp2
            },{
                metricCode: "error_rate",
                warnThreshold: formData.error_rate1,
                fatalThreshold: formData.error_rate2
            }])
        }
        ThresholdService.setThreshold(para).then(function(data) {
            if(data.success == 1) {
                AlertService.alert({
                    title: "提示",
                    content: "保存成功"
                })
            }
        })
    }

}]);