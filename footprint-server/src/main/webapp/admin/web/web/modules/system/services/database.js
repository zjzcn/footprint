angular.module("ng-admin").service("DatabaseService", ["HttpService", function(HttpService) {
    return {
        "getURLsList": function(params) {
            return HttpService.post("/databases/sql.htm", params);
        },
        "getRPTData": function(params) {
            return HttpService.post("/databases/resp.htm", params);
        },
        "getTHAData": function(params) {
            return HttpService.post("/databases/throughput.htm", params);
        },
        "getCCTData": function(params) {
            return HttpService.post("/databases/concurrency.htm", params);
        },
        "geturlChart": function(params) {
            return HttpService.post("/databases/sql/data.htm", params);
        },
        "getTraceTable": function(params) {
            return HttpService.post("", params);
        },
        "getTraceInfo": function(params) {
            return HttpService.post("", params);
        }
    }
}]);