angular.module("ng-admin").service("ProfilingService", ["HttpService", function(HttpService) {
    return {
        "getAgentInfo": function(params) {
            return HttpService.post("/thread/agent.htm", params);
        },
        "getStackInfo": function(params) {
            return HttpService.post("/thread/stack.htm", params);
        }
    }
}]);