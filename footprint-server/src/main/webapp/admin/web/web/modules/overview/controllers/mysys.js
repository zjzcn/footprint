angular.module("ng-admin").controller('OverviewMainController', 
	["$rootScope", "$state", '$scope', '$filter', '$cookieStore', 'OverviewInfoService', "DialogService", "AlertService",
	function($rootScope, $state, $scope, $filter, $cookieStore, OverviewInfoService, DialogService, AlertService) {

        var vm = $scope;
        vm.pageSize = 5;
        function LoadSysList(pageIndex) {
            var para = {
                pageNo: pageIndex,
                pageSize: 5
            };
            OverviewInfoService.getSystemList(para).then(function(data) {
                if(data.success == 1){
                    vm.sysList = data.result.appShowList;
                    vm.totalDataCount = data.result.totalCount;
                }
            });
            OverviewInfoService.getUnvisibleSysList().then(function(data) {
                if(data.success == 1){
                    vm.unvisibleSys = data.result.appShowList;
                }

            });
        }

        //分页相关
        vm.$on('pager:pageIndexChanged', function (evt,page) {
            // 分页操作
            evt.stopPropagation();
            LoadSysList(page + 1);
        });
        vm.isShowPageIndex = function () {
            return (vm.totalDataCount > vm.pageSize);
        };
        
        //跳转到拓扑图，并将选择写入cookie
        vm.goTopology = function(item){
            $.cookie('selectSys', item.appId);
            $state.go("System.Topology");
        };

        vm.modify = function(item){
            DialogService.modal({
                key:"Baymax.Overview.ModifyDialog",
                url:"modules/overview/templates/modify-sys-dialog.html",
                accept:function(data){
                    AlertService.alert({
                        title: "提示",
                        content: "修改成功"
                    }).then(function(){
                        LoadSysList(1);
                    });
                }
            }, item);
        };
        
        vm.disabled = function(item){
            var para = {
                appId: item.appId
            }
            OverviewInfoService.unvisibleApp(para).then(function(data) {
                if(data.success == 1) {
                    AlertService.alert({
                        title: "提示",
                        content: "修改成功"
                    });
                    LoadSysList(1);
                }
            });
        };

        vm.showTable = false;
        vm.showSys = function(item){
            var para = {
                appId: item.appId
            };
            OverviewInfoService.visibleApp(para).then(function(data) {
                if(data.success == 1) {
                    AlertService.alert({
                        title: "提示",
                        content: "修改成功"
                    });
                    vm.showTable = true;
                    LoadSysList(1);
                }
            });
        };

        vm.remove = function(item){
            AlertService.confirm({
                title:"删除",
                content:"确认要删除这个应用吗？"
            }).then(function(){
                var para = {
                    appId: item.appId
                };
                OverviewInfoService.deleteApp(para);
            });
        };

        vm.selectedMenu = null;
        vm.selectArrow = function(item){
            if (item != vm.selectedMenu) {
                vm.selectedMenu = item;
            }else{
                vm.selectedMenu = null;
            }
        };

        vm.isSelected = function(menuItem) {
            if (vm.selectedMenu) {
                if (vm.selectedMenu.appId == menuItem.appId) {
                    return true;
                }
            }
            else {
                return false;
            }
        };

        //Tab所有（今天，昨天，更早）
        var para_today1 = {
            "pageNum": 1,
            "pageSize": 10
        };
        OverviewInfoService.getTodayEvents(para_today1).then(function(data) {
            if(data.success == 1) {
                vm.todayAll = data.result.pageObject.list;
                vm.count_today1 = data.result.pageObject.totalCount;
            }

        });
        var para_yesterday1 = {
            pageNum: 1,
            pageSize: 10
        };
        OverviewInfoService.getYesterdayEvents(para_yesterday1).then(function(data) {
            if(data.success == 1) {
                vm.yesterdayAll = data.result.pageObject.list;
                vm.count_yesterday1 = data.result.pageObject.totalCount;
            }
        });
        var para_early1 = {
            pageNum: 1,
            pageSize: 10
        };
        OverviewInfoService.getEarlyEvents(para_early1).then(function(data) {
            if(data.success == 1) {
                vm.earlyAll = data.result.pageObject.list;
                vm.count_early1 = data.result.pageObject.totalCount;
            }
        });
        //Tab警告（今天，昨天，更早）
        var para_today2 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "WARNING"
        };
        OverviewInfoService.getTodayEvents(para_today2).then(function(data) {
            if(data.success == 1) {
                vm.todayWarn = data.result.pageObject.list;
                vm.count_today2 = data.result.pageObject.totalCount;
            }
        });
        var para_yesterday2 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "WARNING"
        };
        OverviewInfoService.getYesterdayEvents(para_yesterday2).then(function(data) {
            if(data.success == 1) {
                vm.yesterdayWarn = data.result.pageObject.list;
                vm.count_yesterday2 = data.result.pageObject.totalCount;
            }
        });
        var para_early2  = {
            pageNum: 1,
            pageSize: 10,
            eventType: "WARNING"
        };
        OverviewInfoService.getEarlyEvents(para_early2).then(function(data) {
            if(data.success == 1) {
                vm.earlyWarn = data.result.pageObject.list;
                vm.count_early2 = data.result.pageObject.totalCount;
            }
        });
        //Tab严重（今天，昨天，更早）
        var para_today3 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "FATAL"
        };
        OverviewInfoService.getTodayEvents(para_today3).then(function(data) {
            if(data.success == 1) {
                vm.todayFatal = data.result.pageObject.list;
                vm.count_today3 = data.result.pageObject.totalCount;
            }
        });
        var para_yesterday3 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "FATAL"
        };
        OverviewInfoService.getYesterdayEvents(para_yesterday3).then(function(data) {
            if(data.success == 1) {
                vm.yesterdayFatal = data.result.pageObject.list;
                vm.count_yesterday3 = data.result.pageObject.totalCount;
            }
        });
        var para_early3  = {
            pageNum: 1,
            pageSize: 10,
            eventType: "FATAL"
        };
        OverviewInfoService.getEarlyEvents(para_early3).then(function(data) {
            if(data.success == 1) {
                vm.earlyFatal = data.result.pageObject.list;
                vm.count_early3 = data.result.pageObject.totalCount;
            }
        });
        //Tab配置（今天，昨天，更早）
        var para_today4 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "COMPOUND"
        };
        OverviewInfoService.getTodayEvents(para_today4).then(function(data) {
            if(data.success == 1) {
                vm.todayConf = data.result.pageObject.list;
                vm.count_today4 = data.result.pageObject.totalCount;
            }
        });
        var para_yesterday4 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "COMPOUND"
        };
        OverviewInfoService.getYesterdayEvents(para_yesterday4).then(function(data) {
            if(data.success == 1) {
                vm.yesterdayConf = data.result.pageObject.list;
                vm.count_yesterday4 = data.result.pageObject.totalCount;
            }
        });
        var para_early4 = {
            pageNum: 1,
            pageSize: 10,
            eventType: "COMPOUND"
        };
        OverviewInfoService.getEarlyEvents(para_early4).then(function(data) {
            if(data.success == 1) {
                vm.earlyConf = data.result.pageObject.list;
                vm.count_early4 = data.result.pageObject.totalCount;
            }
        });

        //已阅未阅状态
        vm.remark = function(item) {
            DialogService.modal({
                key:"Baymax.System.NoteDialog",
                url:"modules/system/templates/main/note-dialog.html",
                accept:function(data){
                }
            },item);
        }
        $(function() {
            //展开折叠
            $('li.parent').click(function(){
                $(this).siblings('.child_'+this.id).toggle();
            });
            //加载更多
            $("#All_EventsTable").on("click", "#loadMoreY", function() {

            });

        });

/*
        vm.events = [{
            desc: '苏宁易购 错误率超过严重阈值10%',
            type: 'warning',
            status: 'unknow',
            time: '2015-10-15 20:45:22'
        }];

        var myDate = new Date(); //当前时间
        var myDate1 = new Date(myDate.getTime()-(24*60*60*1000)); //当前时间前一天
        vm.today_events = [];
        vm.yesterday_events = [];
        vm.earlier_events = [];
        vm.count_all = vm.events.length;
        vm.count_warning = 0;
        vm.count_serious = 0;
        vm.count_conf = 0;
        for(var i=0; i< vm.events.length; i++) {
            var d = new Date(vm.events[i].time); //获取到的时间
            if(d.getDate() == myDate.getDate()) {
                vm.today_events.push(vm.events[i]); //今天
            }else if(d.getDate() == myDate1.getDate()) {
                vm.yesterday_events.push(vm.events[i]); //昨天
            }else {
                vm.earlier_events.push(vm.events[i]);
            }

            if(vm.events[i].type == 'warning') {
                vm.count_warning += 1;
            }else if(vm.events[i].type == 'serious') {
                vm.count_serious += 1;
            }else {
                vm.count_conf += 1;
            }
        }
*/

        vm.create = function() {
            DialogService.modal({
                key:"Baymax.Overview.CreateDialog",
                url:"modules/overview/templates/create-dialog.html",
                accept:function(data){
                        LoadSysList();
                }
            });
        };
        
        vm.init = function(){
            LoadSysList(1);
        };

}]);

angular.module("ng-admin").controller("ModifySysDialogCtrl", ["$scope", "DialogService","AlertService", "OverviewInfoService", function($scope, DialogService, AlertService, OverviewInfoService) {
    var vm = $scope,
    formData = vm.formData = {};
    vm.ok = function() {
        var para = {
            appId: $scope.$parent.appId,
            newAppName: formData.name
        };
        OverviewInfoService.modifyApp(para).then(function(data) {
            if(data.success == 1) {
                DialogService.accept("Baymax.Overview.ModifyDialog", vm.formData);
            }else {
                AlertService.alert({
                    title: "警告",
                    content: "修改信息失败"
                })
            }
        })
        vm.close();
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.Overview.ModifyDialog");
    };

    vm.cancel = function() {
        DialogService.refuse("Baymax.Overview.ModifyDialog");
    };

}]);

angular.module("ng-admin").controller("CreateDialogCtrl", ["$scope", "DialogService", "AlertService", "OverviewInfoService", function($scope, DialogService, AlertService, OverviewInfoService) {

    var vm = $scope,
    formData = vm.formData = {};
    vm.ok = function() {
        var para = {
            appName: formData.name
        };
        OverviewInfoService.createApp(para).then(function(data) {
            if(data.success == 1) {
                vm.app = data.result.appShowModel;
                DialogService.modal({
                    key:"Baymax.Overview.GuideDialog",
                    url:"modules/overview/templates/guide-dialog.html",
                    accept:function(data){
                    }
                },vm.app);
            }
        });
        vm.close();
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.Overview.CreateDialog");
    };

    vm.cancel = function() {
        DialogService.refuse("Baymax.Overview.CreateDialog");
    };

}]);

angular.module("ng-admin").controller("GuideDialogCtrl", ["$scope", "DialogService", "$state", function($scope, DialogService, $state) {

    var vm = $scope;
    vm.license_Key = $scope.$parent.licenseKey;
    vm.ok = function() {
        $state.go("Agent");
        vm.close();
    };

    vm.close = function() {
        DialogService.dismiss("Baymax.Overview.GuideDialog");
    };

}]);