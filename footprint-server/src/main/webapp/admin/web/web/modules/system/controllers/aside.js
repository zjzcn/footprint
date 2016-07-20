angular.module("ng-admin").controller('SystemSideMenuCtrl', ["$rootScope", "$state", "$scope", function ($rootScope, $state, $scope) {
    var vm = $scope;

    vm.menus = [];

    var menus = [
        {code: "0", name: "系统拓扑", icon: "fa fa-share-alt", state: "System.Topology"},
        {code: "1", name: "信息汇总", icon: "fa fa-th", state: "System.Main"},
//        {code: "2", name: "仪表盘", icon: "fa fa-tachometer", state: "System.Dashboard"},
        {code: "3", name: "Web服务", icon: "fa fa-th-large"},
        {code: "4", name: "APP服务", icon: "fa fa-server"},
        {code: "5", name: "外部服务", icon: "fa fa-link", state: "System.ExtService"},
        {code: "6", name: "数据服务", icon: "fa fa-database", state: "System.DataService"},

        {parent: "3", code: "3-0", name: "信息一览", icon: "fa fa-list-ul", state: "System.InfoList"},
        {parent: "3", code: "3-1", name: "错误请求", icon: "fa fa-bug", state: "System.WebError"},
        {parent: "3", code: "3-2", name: "环境变量", icon: "fa fa-cog", state: "System.WebEnv"},

        {parent: "4", code: "4-0", name: "请求过程", icon: "fa fa-signal", state: "System.WebProcess"},
        {parent: "4", code: "4-1", name: "数据源", icon: "fa fa-database", state: "System.Database"},
        {parent: "4", code: "4-2", name: "线程剖析", icon: "fa fa-exchange", state: "System.ThreadProfiling"},
        {parent: "4", code: "4-3", name: "JVMs", icon: "fa fa-inbox", state: "System.JVMs"},
//        {parent: "4", code: "4-4", name: "NoSQL", icon: "fa fa-tasks", state: "System.NoSQL"},
        {parent: "4", code: "4-5", name: "异常日志", icon: "fa fa-bug", state: "System.AppError"},
        {parent: "4", code: "4-6", name: "环境变量", icon: "fa fa-cog", state: "System.AppEnv"}

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