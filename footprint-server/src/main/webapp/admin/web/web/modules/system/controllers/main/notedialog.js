angular.module("ng-admin").controller("NoteDialogCtrl", ["$scope", "DialogService", "AlertService", "SystemMainService", function($scope, DialogService, AlertService, SystemMainService) {

    var vm = $scope;
    var formData = vm.formData = {};
    formData.remark = $scope.$parent.remark;
    vm.ok = function() {
        var para = {
            id: 7,
            remark: formData.remark
        };
        SystemMainService.markStatus(para).then(function(data) {
                if(data.success == 1) {
                    AlertService.alert({
                        title: "提示",
                        content: "标注成功"
                    });
                }
        })
        vm.close();
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.System.NoteDialog");
    };

    vm.cancel = function() {
        DialogService.refuse("Baymax.System.NoteDialog");
    };

}]);