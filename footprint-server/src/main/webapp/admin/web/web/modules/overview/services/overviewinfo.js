angular.module("ng-admin").service("OverviewInfoService", ["HttpService", function(HttpService) {
    return {
        "getSystemList": function(params) {
            return HttpService.get("/appinfo/visible_app_page.htm", params);
        },
        "getUnvisibleSysList": function() {
            return HttpService.get("/appinfo/unvisible_app_list.htm");
        },
        "getSysSelectList": function(params) {
            return HttpService.get("/appinfo/appname_list.htm", params);
        },
        "getAllAgentSelectList": function(params) {
            return HttpService.get("/agentinfo/agentname_list.htm", params);
        },
        "getAgentSelectList": function(params) {
            return HttpService.get("/agentinfo/agentname_list_type.htm", params);
        },
        "createApp": function(params) {
            return HttpService.get("/appinfo/generate_app.htm", params);
        },
        "modifyApp": function(params) {
            return HttpService.get("/appinfo/modify_app.htm", params);
        },
        "deleteApp": function(params) {
            return HttpService.get("/appinfo/delete_app.htm", params);
        },
        "visibleApp": function(params) {
            return HttpService.get("/appinfo/visible_app.htm", params);
        },
        "unvisibleApp": function(params) {
            return HttpService.get("/appinfo/unvisible_app.htm", params);
        },
        "getTodayEvents": function(params) {
            return HttpService.post("/event/today.htm", params);
        },
        "getYesterdayEvents": function(params) {
            return HttpService.post("/event/yesterday.htm", params);
        },
        "getEarlyEvents": function(params) {
            return HttpService.post("/event/early.htm", params);
        }
    }
}]);