angular.module("ng-admin").service("SystemMainService", ["HttpService", function(HttpService) {
    return {
        "getEventsList": function(params) {
            return HttpService.post("/event/geteventbyregisterid.htm", params);
        },
        "markStatus": function(params) {
            return HttpService.post("/event/updateeventstatus.htm", params);
        },
        "getPerformanceData": function(params) {
            return HttpService.post("/info/performance.htm", params)
        },
        "getCPUData": function(params) {
            return HttpService.post("/info/cpuUsage.htm", params)
        },
        "getMemData": function(params) {
            return HttpService.post("/info/physicalMemory.htm", params)
        }
    }
}]);