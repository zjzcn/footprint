angular.module("ng-admin").controller("AddDashboardDialogCtrl", 
	["$scope", "DialogService", "$cookieStore", "$rootScope",
	function($scope, DialogService, $cookieStore, $rootScope) {

    $rootScope.dashboardData = [];

    var vm = $scope;

    var sysName = "",
    	sysTime = "";

    sysName = $("#appKeySelect option:selected").text();
    sysTime = $("#dateRangePicker span").html();

    console.log(getSelectTime(sysTime))

    vm.name = sysName + "-" + sysTime +"-" + vm.title;

    vm.ok = function() {
        //TODO 示例
        $rootScope.dashboardData.push({
            title: vm.name,
            tip: vm.tip,
            moduleName: "getResponseTime",
            params: {
                appKey: sysName,
                time:sysTime
            }
        });
        vm.close();
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.Alarm.AddDashboardDialog");
    };

    vm.cancel = function() {
        DialogService.refuse("Baymax.Alarm.AddDashboardDialog");
    };

}]);