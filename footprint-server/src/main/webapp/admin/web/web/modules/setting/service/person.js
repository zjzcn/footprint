angular.module("ng-admin").service("PersonSettingService", ["HttpService", function(HttpService) {
    return {
        "getPersonList": function(params) {
            return HttpService.get("/warningcontacter/contacter_list.htm", params);
        },
        "addPerson": function(params) {
            return HttpService.post("/warningcontacter/create_contacter.htm", params);
        },
        "modifyPerson": function(params) {
            return HttpService.post("/warningcontacter/modify_contacter.htm", params);
        },
        "deletePerson": function(params) {
            return HttpService.post("/warningcontacter/delete_contacter.htm", params);
        },
        "enableAlarm": function(params) {
            return HttpService.post("/warningcontacter/enable_contacter.htm", params);
        }
    }
}]);