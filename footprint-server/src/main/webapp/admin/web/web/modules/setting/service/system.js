angular.module("ng-admin").service("SystemSettingService", ["HttpService", function(HttpService) {
    return {
        "getSysSetting": function(params) {
            return HttpService.get("/appsettings/read_appsettings.htm", params);
        },
        "setSystem": function(params) {
            return HttpService.post("/appsettings/modify_appsettings.htm", params);
        }
    }
}]);