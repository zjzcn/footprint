angular.module("ng-admin").service("AppEnvService", ["HttpService", function(HttpService) {
    return {
        "getAgentList": function(params) {
            return HttpService.post("/env/agent.htm", params);
        },
        "getAgentInfo": function(params) {
            return HttpService.post("/env/info.htm", params);
        }
    }
}]);