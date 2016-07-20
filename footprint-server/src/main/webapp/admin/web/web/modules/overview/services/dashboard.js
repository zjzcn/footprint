angular.module("ng-admin").service("OverviewDashboardService", ["HttpService", function(HttpService) {
    return {
        "getChartsType": function(params) {
            return HttpService.get("", params);
        },
        "getRespTime_all": function(params) {
            return HttpService.get("", params);
        },
        "getthroughput_all": function(params) {
            return HttpService.get("", params);
        },
        "getErrorRate_all": function(params) {
            return HttpService.get("", params);
        },
        "getWebprocess_all": function(params) {
            return HttpService.get("", params);
        },
        "getCPU_all": function(params) {
            return HttpService.get("", params);
        },
        "getMemory_all": function(params) {
            return HttpService.get("", params);
        },
        "deleteDashboard": function(params) {
            return HttpService.get("", params);
        }
    }
}]);