angular.module("ng-admin").service("WarningService", ["HttpService", function(HttpService) {
    return {
        "getWarningList": function(params) {
            return HttpService.get("/alarm/alarm_list.htm", params);
        }
    }
}]);