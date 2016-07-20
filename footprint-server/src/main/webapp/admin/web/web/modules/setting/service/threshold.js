angular.module("ng-admin").service("ThresholdService", ["HttpService", function(HttpService) {
    return {
        "getThreshold": function(params) {
            return HttpService.get("/alarm/config_list.htm", params);
        },
        "setThreshold": function(params) {
            return HttpService.get("/alarm/config_save.htm", params);
        }
    }
}]);