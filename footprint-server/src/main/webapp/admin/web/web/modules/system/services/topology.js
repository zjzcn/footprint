angular.module("ng-admin").service("TopologyService", ["HttpService", function(HttpService) {
    return {
        "getXXX": function(params) {
            return HttpService.get("", params);
        }
    }
}]);