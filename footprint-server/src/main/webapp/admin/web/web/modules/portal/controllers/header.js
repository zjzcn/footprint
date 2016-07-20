angular.module("ng-admin").controller('HeaderController', ["$rootScope", "$state", '$scope', function($rootScope, $state, $scope) {
    $scope.$on('$includeContentLoaded', function() {
        Metronic.init();
        Layout.init();
        QuickSidebar.init();
    });
    var vm = $scope;

    vm.menus = [
        {code: "0", name: "概览", icon: "fa fa-globe", state: "OverView", url: "OverView.Main"},
        {code: "1", name: "系统", icon: "fa fa-line-chart", state: "System", url: "System.Topology"},
        {code: "2", name: "设置", icon: "fa fa-cogs", state: "Setting", url: "Setting.System"},
        {code: "3", name: "Agent下载", icon: "fa fa-cloud-download", state: "Agent", url: "Agent"}

    ];

 /*   vm.menus.forEach(function(it) {
        if (it.state == $state.current.name) {
            vm.selectedMenu = it;
            return false;
        }
    });*/

    if(!vm.selectedMenu ){
        vm.selectedMenu = vm.menus[0];
    }

    vm.selectMenu = function (menu) {
        if (menu != vm.selectedMenu) {
            vm.selectedMenu = menu;
        }
    };

    vm.isMenuSelected = function(menuItem) {
        if (vm.selectedMenu) {
            if (vm.selectedMenu.code.indexOf(menuItem.code) == 0) {
                return true;
            }
        }
        else {
            return false;
        }
    };

    $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {
        var str = toState.name.split(".",1);
        vm.menus.forEach(function(it) {
            if (it.state == str) {
                vm.selectedMenu = it;
                return false;
            }
        });
    });

}]);