angular.module("ng-admin").controller("ModifyDialogCtrl", ["$scope", "DialogService", "AlertService", "PersonSettingService", function($scope, DialogService, AlertService, PersonSettingService) {

    var vm = $scope,
    formData = vm.formData = {};

    vm.ok = function() {
        var para = {
            appId: 9,
            mail: formData.email,
            name: formData.name,
            tel: formData.phone,
            warningContacterId: $scope.$parent.warningContacterId
        };
        PersonSettingService.modifyPerson(para).then(function(data) {
            if(data.success == 1) {
                DialogService.accept("Baymax.Alarm.ModifyDialog", vm.formData);
            }else {
                AlertService.alert({
                    title: "警告",
                    content: "修改信息失败"
                })
            }
        })
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.Alarm.ModifyDialog");
    };

    vm.cancel = function() {
        DialogService.refuse("Baymax.Alarm.ModifyDialog");
    };

}]);