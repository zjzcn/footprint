angular.module("ng-admin").service("WebInfoService", ["HttpService", function(HttpService) {
    return {
        "getHttpData": function(params) {
            return HttpService.get("/apache/apache_conns_chart.htm", params);
        },
        "getCPUData": function(params) {
            return HttpService.get("/apache/cpu_chart.htm", params);
        },
        "getDiskData": function(params) {
            return HttpService.get("/apache/disk_chart.htm", params);
        },
        "getMemData": function(params) {
            return HttpService.get("/apache/mem_chart.htm", params);
        },
        "getIOList": function(params) {
            return HttpService.get("/apache/net_chart.htm", params);
        }
    }
}]);