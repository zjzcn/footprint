angular.module("ng-admin").service("AgentDownloadService", ["HttpService", function(HttpService) {
    return {
        "getAgentHistroy": function(params) {
//            return HttpService.get("/agenthistory/agent_history_list.htm", params);
            var data = {
                "errorMessage":"",
                "result":{
                    "agentHistoryList":[
                        {
                            "content":"哈哈哈哈哈",
                            "id":3,
                            "modifyTime":"2015-10-27 12:08:44",
                            "name":"agent",
                            "size":"2.6 MB",
                            "type":"Java",
                            "version":"1.0.0"
                        },
                        {
                            "content":"很好很好很好",
                            "id":2,
                            "modifyTime":"2015-10-22 12:07:48",
                            "name":"agent",
                            "size":"2.7 MB",
                            "type":"Java",
                            "version":"0.0.2"
                        },
                        {
                            "content":"好呀好呀好呀",
                            "id":1,
                            "modifyTime":"2015-10-21 12:02:31",
                            "name":"agent",
                            "size":"2.6 MB",
                            "type":"Java",
                            "version":"0.0.1"
                        }
                    ]
                },
                "success":1
            }
            return data;
        },
        "AgentDownload": function(params) {
            return HttpService.get("/agenthistory/download_agent.htm", params);
        }
    }
}]);

angular.module("ng-admin").controller('AgentDownloadCtrl', ["$state", "$scope", "AgentDownloadService", function ($state, $scope,AgentDownloadService) {
    var vm = $scope;
    var para = {
        agentType: "Java"
    };
    vm.agentHistoryList = AgentDownloadService.getAgentHistroy(para).result.agentHistoryList;
    console.log(vm.agentHistoryList);
    vm.version = vm.agentHistoryList[0].version;
    vm.modifyTime = vm.agentHistoryList[0].modifyTime;
    vm.size = vm.agentHistoryList[0].size;
    vm.download = function() {
        var para = {
            agentName: vm.agentHistoryList[0].name,
            agentVersion: vm.agentHistoryList[0].version
        }
        AgentDownloadService.AgentDownload(para);
    }
}]);