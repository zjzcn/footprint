angular.module("ng-admin", ["ui.router", "sn.controls", "ngCookies"]);

angular.module("ng-admin").value("baseUrl", "");

//将生成的echarts放置到全局变量中，方便触发echarts的全屏事件
angular.module("ng-admin").value("echartsId", 0);
var echartsArr = [];

angular.module("ng-admin").config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/overview/main");

    $stateProvider
        //控制台主界面
        .state("OverView", {
            url: "/overview",
            templateUrl: "modules/overview/templates/overview.html"
        })
        .state("OverView.Main", {
            url: "/main",
            templateUrl: "modules/overview/templates/mysys.html"
        })
        .state("OverView.Dashboard", {
            url: "/dashboard",
            templateUrl: "modules/overview/templates/dashboard.html"
        })
        .state("OverView.Warning", {
            url: "/warning",
            templateUrl: "modules/overview/templates/warning.html"
        })
        .state("CreateApp", {
            url: "/createapp",
            templateUrl: "modules/overview/templates/createapp.html"
        })
        .state("System", {
            url: "/system",
            templateUrl: "modules/system/templates/system.html"
        })
        .state("System.Main", {
            url: "/main",
            templateUrl: 'modules/system/templates/main/main.html'
        })
        .state("System.Dashboard", {
            url: "/dashboard",
            templateUrl: 'modules/system/templates/dashboard/dashboard.html'
        })
        .state("System.Topology", {
            url: "/topology",
            templateUrl: 'modules/system/templates/topology/topology.html'
        })
        .state("System.InfoList", {
            url: "/infolist",
            templateUrl: 'modules/system/templates/infolist/infolist.html'
        })
        .state("System.AppEnv", {
            url: "/appenv",
            templateUrl: 'modules/system/templates/appenv/appenv.html'
        })
        .state("System.AppError", {
            url: "/apperrorlog",
            templateUrl: 'modules/system/templates/apperrorlog/apperrorlog.html'
        })
        .state("ErrorTrace", {
            url: "/errortrace",
            templateUrl: 'modules/system/templates/apperrorlog/errortrace.html'
        })
        .state("System.Database", {
            url: "/database",
            templateUrl: 'modules/system/templates/database/database.html'
        })
        .state("SqlTrace", {
            url: "/sqltrace",
            templateUrl: 'modules/system/templates/database/sqltrace.html'
        })
        .state("System.ExtService", {
            url: "/extservice",
            templateUrl: 'modules/system/templates/extservice/extservice.html'
        })
        .state("System.DataService", {
            url: "/dataservice",
            templateUrl: 'modules/system/templates/dataservice/dataservice.html'
        })
        .state("System.JVMs", {
            url: "/jvms",
            templateUrl: 'modules/system/templates/jvms/jvms.html'
        })
        .state("System.Policy", {
            url: "/policy",
            templateUrl: 'modules/system/templates/policy/policy.html'
        })
        .state("System.ThreadProfiling", {
            url: "/threadprofiling",
            templateUrl: 'modules/system/templates/threadprofiling/threadprofiling.html'
        })
        .state("ProfileResult", {
            url: "/result/:agentId",
            templateUrl: 'modules/system/templates/threadprofiling/result.html'
        })
/*        .state("System.NoSQL", {
            url: "/nosql",
            templateUrl: 'modules/system/templates/nosql/nosql.html'
        })*/
        .state("System.WebEnv", {
            url: "/webenv",
            templateUrl: 'modules/system/templates/webenv/webenv.html'
        })
        .state("System.WebProcess", {
            url: "/webprocess",
            templateUrl: 'modules/system/templates/webprocess/webprocess.html'
        })
        .state("SlowWebProcess", {
            url: "/slowwebprocess/:traceId",
            templateUrl: 'modules/system/templates/webprocess/slowwebprocess.html'
        })
        .state("System.WebError", {
            url: "/weburlerror",
            templateUrl: 'modules/system/templates/weburlerror/weburlerror.html'
        })
        .state("Setting", {
            url: "/setting",
            templateUrl: "modules/setting/templates/setting.html"
        })
        .state("Setting.System", {
            url: "/system",
            templateUrl: "modules/setting/templates/system.html"
        })
        .state("Setting.Person", {
            url: "/person",
            templateUrl: "modules/setting/templates/person.html"
        })
        .state("Setting.Threshold", {
            url: "/threshold",
            templateUrl: "modules/setting/templates/threshold.html"
        })
        .state("Agent", {
            url: "/agent-download",
            templateUrl: "modules/agentdown/agentdownload.html"
        })
});


