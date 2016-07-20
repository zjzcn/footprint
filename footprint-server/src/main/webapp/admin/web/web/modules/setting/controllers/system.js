angular.module("ng-admin").controller('SystemSettingCtrl', ["$rootScope", "$scope", "SystemSettingService", "AlertService", function ($rootScope, $scope, SystemSettingService, AlertService) {
    var vm = $scope,
    formData = vm.formData = {};

    $rootScope.appKeyChange = function(){
        vm.$apply(function () {
        	console.log("setting/system---appKeyChange");
        });
    }

    vm.setting = [];
    var para = {
            appId: 14
    };
    SystemSettingService.getSysSetting(para).then(function(data) {
        if(data.success == 1) {
            vm.setting = data.result.settingShowList;
        }
        for(var i=0; i<vm.setting.length; i++) {
            if(vm.setting[i].appSettingType == "WEBREQUEST") {
                formData.urilist = vm.setting[i].settingMap.webRequestUriList;
            }else if(vm.setting[i].appSettingType == "DB") {
                formData.sqltrace = vm.setting[i].settingMap.isSlowSqlTrace;
            }else if(vm.setting[i].appSettingType == "THREAD") {
                formData.threadvalue = vm.setting[i].settingMap.autoThreadThreshold;
                formData.thread = vm.setting[i].settingMap.isAutoThreadTrace;
            }else if(vm.setting[i].appSettingType == "JVMS") {
                formData.jvm = vm.setting[i].settingMap.isJvmsOpen;
            }
        }
    });


    vm.save_webrequest = function() {
        var para = {
            "appId": 14,
            "appSettingType": "WEBREQUEST",
            "settingMap": {
                "webRequestUriList": formData.urilist
            }
        };
        SystemSettingService.setSystem(para).then(function(data) {
            if(data.success == 1) {
                AlertService.alert({
                    title: "提示",
                    content: "配置保存成功！"
                })
            }
        })
    };
    vm.save_database = function() {
        var para = {
            appId: 14,
            appSettingType: "DB",
            settingMap: {
                "isSlowSqlTrace": formData.sqltrace
            }
        };
        SystemSettingService.setSystem(para).then(function(data) {
            if(data.success == 1) {
                AlertService.alert({
                    title: "提示",
                    content: "配置保存成功！"
                })
            }
        })
    };
    vm.save_thread = function() {
        var para = {
            appId: 14,
            appSettingType: "THREAD",
            settingMap: {
                autoThreadThreshold: formData.threadvalue,
                isAutoThreadTrace: formData.thread
            }
        };
        SystemSettingService.setSystem(para).then(function(data) {
            if(data.success == 1) {
                AlertService.alert({
                    title: "提示",
                    content: "配置保存成功！"
                })
            }
        })
    };
    vm.save_jvms = function() {
        var para = {
            appId: 14,
            appSettingType: "JVMS",
            settingMap: {
                isJvmsOpen: formData.jvm
            }
        };
        SystemSettingService.setSystem(para).then(function(data) {
            if(data.success == 1) {
                AlertService.alert({
                    title: "提示",
                    content: "配置保存成功！"
                })
            }
        })
    }

}]);