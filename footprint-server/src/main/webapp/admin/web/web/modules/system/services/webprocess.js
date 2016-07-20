angular.module("ng-admin").service("WebProcessService", ["HttpService", function(HttpService) {
    return {
        "getURLsList": function(params) {
            return HttpService.post("/web/url.htm", params);
        },
        "getRPTData": function(params) {
            return HttpService.post("/web/actions/resp.htm", params);
        },
        "getTHAData": function(params) {
            return HttpService.post("/web/actions/throughput.htm", params);
        },
        "getCCTData": function(params) {
            return HttpService.post("/web/actions/concurrence.htm", params);
        },
        "geturlChart": function(params) {
            return HttpService.post("/web/url/action.htm", params);
        },
        "getTraceTable": function(params) {
            return HttpService.post("/webtrace/urls.htm", params);
        },
        "getTraceInfo": function(params) {
            return HttpService.post("/webtrace/info.htm", params);
        },
        "getTraceSummary": function(params) {
            return HttpService.post("/webtrace/summary.htm", params);
        },
        "getTraceDetail": function(params) {
            return HttpService.post("/webtrace/detail.htm", params);
        },
        "getTraceSql": function(params) {
            return HttpService.post("", params);
        },
        "getTraceParams": function(params) {
            return HttpService.post("", params);
        },
        "getAppName": function(params) {
            return HttpService.get("/appinfo/get_app_name.htm", params);
        },
        "getAgentName": function(params) {
            return HttpService.get("/agentinfo/get_agent_name.htm", params);
        }
    }
}]);