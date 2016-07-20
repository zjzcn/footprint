angular.module("ng-admin").controller('PersonSettingCtrl', ["$rootScope", "LazyLoader", "$state", "$scope", "DialogService", "AlertService", "PersonSettingService", function ($rootScope, LazyLoader, $state, $scope,DialogService, AlertService, PersonSettingService) {
    var vm = $scope;
    vm.pageSize = 10;
    $rootScope.appKeyChange = function(){
        console.log("setting/person---appKeyChange")
    }
    //table重绘时页面需要重新初始化bootstrapswitch
    $('#AlarmTable').on( 'draw.dt', function () {
        $('.switch').bootstrapSwitch();
    } );

    LazyLoader.load(["js/lib/bootstrap-switch.js"])
        .then(function () {
            if($('.switch').length > 0){
                $('.switch').bootstrapSwitch();
            }
        })

    function loadPersonList(pageIndex) {
        var para = {
            "appId": 9,
            "pageNo": pageIndex,
            "pageSize": vm.pageSize
        };
        PersonSettingService.getPersonList(para).then(function(data) {
            if(data.success == 1){
                vm.personList = data.result.warningContacterList;
                vm.totalCount = data.result.totalCount;
            }
        });
    }
    vm.init = function() {
        loadPersonList(1);
    };
    vm.$on('pager:pageIndexChanged', function (evt,page) {
        // 分页操作
        evt.stopPropagation();
        loadPersonList(page + 1);
    });
    vm.isShowPageIndex = function () {
        return (vm.totalCount > vm.pageSize);
    };
    vm.add = function(){
        DialogService.modal({
            key:"Baymax.Alarm.AddUserDialog",
            url:"modules/setting/templates/add-alarm-dialog.html",
            accept:function(data){
                AlertService.alert({
                    title: "提示",
                    content: "新增联系人成功"
                }).then(function(){
                        loadPersonList(1);
                    });
            }
        });
    };
    vm.modify = function(item) {
        DialogService.modal({
            key:"Baymax.Alarm.ModifyDialog",
            url:"modules/setting/templates/modify-dialog.html",
            accept:function(data){
                AlertService.alert({
                    title: "提示",
                    content: "修改成功"
                }).then(function(){
                        loadPersonList(1);
                    });
            }
        },item);
    };
    vm.del = function(item) {
        AlertService.confirm({
            title:"删除",
            content:"确认要删除吗？"
        }).then(function(){
                var para = {
                    appId: 9,
                    warningContacterId: item.warningContacterId
                };
                PersonSettingService.deletePerson(para).then(function(data) {
                    if(data.success == 1) {
                        loadPersonList(1);
                    }else {
                        AlertService.alert({
                            title: "警告",
                            content: "删除失败"
                        })
                    }
                });
            });
    }

}]);