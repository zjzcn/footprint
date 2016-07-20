angular.module("ng-admin").controller("AddAlarmDialogCtrl", ["$scope", "DialogService", "AlertService", "PersonSettingService", function($scope, DialogService, AlertService, PersonSettingService) {

    var vm = $scope;
    var formData = vm.formData = {};
    vm.ok = function() {
        var para = {
            appId: 9,
            mail: formData.email,
            name: formData.name,
            tel: formData.phone
        };
        PersonSettingService.addPerson(para).then(function(data) {
            if(data.success == 1) {
                DialogService.accept("Baymax.Alarm.AddUserDialog", vm.formData);
            }else {
                AlertService.alert({
                    title: "警告",
                    content: "新增联系人失败"
                })
            }
        })
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.Alarm.AddUserDialog");
    };

    vm.cancel = function() {
        DialogService.refuse("Baymax.Alarm.AddUserDialog");
    };

}]);