angular.module('ng-admin').filter('StatusFilter', function(){
    var typeEnum = {
        "timeout": "操作超时"

    };
    return function(type){
        return typeEnum[type];
    }
});

// 告警等级
angular.module('ng-admin').filter('AlarmLevelFilter', function(){
    var typeEnum = {
        "warning": "警告",
        "fatal": "严重"
    };
    return function(type){
        return typeEnum[type];
    }
});

// 告警状态
angular.module('ng-admin').filter('AlarmStateFilter', function(){
    var typeEnum = {
        "0": "持续中",
        "1": "告警解除"
    };
    return function(type){
        return typeEnum[type];
    }
});

