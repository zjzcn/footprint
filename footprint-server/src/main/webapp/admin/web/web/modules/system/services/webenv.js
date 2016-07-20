angular.module("ng-admin").service("WebEnvService", ["HttpService", function(HttpService) {
    return {
        "getNodeInfo": function(params) {
            return HttpService.get("/agentinfo/apache_agent_env.htm", params);
        }
    }
}]);