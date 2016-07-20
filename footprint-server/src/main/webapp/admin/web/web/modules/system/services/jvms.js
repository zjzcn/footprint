angular.module("ng-admin").service("JVMsService", ["HttpService", function(HttpService) {
    return {
        "getJVMInfo": function(params) {
            return HttpService.post("/jvm/info.htm", params);
        },
        "getHMUData": function(params) {
            return HttpService.post("/jvm/memory/heap.htm", params);
        },
        "getNHMData": function(params) {
            return HttpService.post("/jvm/memory/nonheap.htm", params);
        },
        "getPMUData": function(params) {
            return HttpService.post("/jvm/memory/physical.htm", params);
        },
        "getRMUData": function(params) {
            return HttpService.post("/jvm/memory/runtime.htm", params);
        },
        "getThreadData": function(params) {
            return HttpService.post("/jvm/thread.htm", params);
        },
        "getTop4Thread": function(params) {
            return HttpService.post("/jvm/thread/classification.htm", params);
        },
        "getClassData": function(params) {
            return HttpService.post("/jvm/class.htm", params);
        },
        "getGCYData": function(params) {
            return HttpService.post("/jvm/gc/yung.htm", params);
        },
        "getGCFData": function(params) {
            return HttpService.post("/jvm/gc/full.htm", params);
        },
        "getPSSData": function(params) {
            return HttpService.post("/jvm/gc/survivor.htm", params);
        },
        "getPESData": function(params) {
            return HttpService.post("/jvm/gc/eden.htm", params);
        },
        "getCOGData": function(params) {
            return HttpService.post("/jvm/gc/old.htm", params);
        },
        "getCPGData": function(params) {
            return HttpService.post("/jvm/gc/perm.htm", params);
        }
    }
}]);