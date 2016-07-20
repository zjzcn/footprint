angular.module("ng-admin").controller('OverViewSideMenuCtrl', ["$rootScope", "$state", "$scope", function ($rootScope, $state, $scope) {
    var vm = $scope;

    vm.menus = [];

    var menus = [
        {code: "0", name: "系统一览", icon: "fa fa-list", state: "OverView.Main"},
//        {code: "1", name: "仪表盘", icon: "fa fa-tachometer", state: "OverView.Dashboard"},
        {code: "2", name: "系统报警列表", icon: "fa fa-list-alt", state: "OverView.Warning"}

    ];

    var menuMap = {};

    for (var i=0; i<menus.length; i++) {
        var menuItem = menus[i];
        menuMap[menuItem.code] = menuItem;

        if (!menuItem.parent) {
            vm.menus.push(menuItem);
        }
        else {
            var parent = menuMap[menuItem.parent];
            if (!parent.children) {
                parent.children = [];
            }
            parent.children.push(menuItem);
            menuItem.parentMenu = parent;
        }
    }

    menus.forEach(function(it) {
        if (it.state == $state.current.name) {
            vm.selectedMenu = it;
            return false;
        }
    });

    if(!vm.selectedMenu ){
        vm.selectedMenu = vm.menus[0];
    }

    vm.selectMenu1 = function (menu) {
        if (menu != vm.selectedMenu) {
            vm.selectedMenu = menu;

            if (menu.state) {
                $state.go(menu.state);
            }
        }
        else {
            vm.selectedMenu = menu.parentMenu;
        }
    };

    vm.selectMenu2 = function (menu) {
        if (menu != vm.selectedMenu) {
            vm.selectedMenu = menu;

            if (menu.state) {
                $state.go(menu.state);
            }
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
        menus.forEach(function(it) {
            if (it.state == toState.name) {
                vm.selectedMenu = it;
                return false;
            }
        });
    });

}]);