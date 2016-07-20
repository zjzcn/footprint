angular.module("ng-admin").controller("WebProcessCtrl", ["$scope", "$filter", "$rootScope", "OverviewInfoService", "WebProcessService", function($scope, $filter, $rootScope, OverviewInfoService, WebProcessService) {

    var vm = $scope;
    var formData = vm.formData = {};
    vm.pageSize = 5;
    var myDate = new Date();
    formData.orderType = 'responseTime';
    vm.agentList = [];
    vm.agentType = "app";
    var para = {
        appId: 14,
        agentType: "jvm"
    };
    OverviewInfoService.getAgentSelectList(para).then(function(data){
        if(data.success == 1){
            vm.agentList = data.result.agentShowList;
        }
    })

    vm.appKeyChange = function(){
         var appTd = $.cookie('selectSys')
    }
    vm.agentKeyChange = function(){
         var agentId = $.cookie('selectAgentall')
    }
    vm.dateRangeChange = function() {
        /*         var time = getSelectTime( $.cookie('selectTime') );
         vm.startTime = time.startTime;
         vm.endTime = time.endTime;*/
    }


    function LoadUrlList() {
        var para = {
            "agentId": 1003,
            "endDate": "2015-11-06T08:24:55.599Z",
            "orderType": formData.orderType,
            "queryNum": 20,
            "startDate": "2015-11-06T08:24:55.599Z"
        };
        WebProcessService.getURLsList(para).then(function(data) {
            if(data.success == 1) {
                vm.urlList = data.result.urls;
                vm.treeList = [];
                for(var i=0; i<vm.urlList.length; i++) {
                    vm.treeList.push({
                        "dimensionCode": vm.urlList[i].url,
                        "dimensionName": vm.urlList[i].url,
                        "featureValue": vm.urlList[i].elaspsedTime,
                        "moreDesc":""
                    })
                }
            }
        })
        $("#selectTreeOrder_web").chosen({
            no_results_text : "未查询到结果", // 未检索到结果时显示的文字
            search_contains : true, // 是模糊搜索还是精确搜索
            disable_search_threshold : 10,
            width: "120px"
        });
    }

    vm.Options = [];
    for(var i= 0; i<3; i++){
        vm.Options[i] = {
            tooltip : {
                trigger: 'item',
                formatter:function(){}
            },
            legend: {
                data:[],
                y: 'bottom'
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'time',
                    splitNumber : 5,
                    boundaryGap : [ 0, 0 ]
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} ms'
                    }
                }
            ],
            series : []
        }
    }
    function LoadChartsData() {
        var para = {
            "queryNum": 5,
            "agentId":  1003,
            "startDate": "2015-11-06T08:07:56.912Z",
            "endDate": "2015-11-06T08:14:55.907Z"
        }
        WebProcessService.getRPTData(para).then(function(data) {
            if(data.success == 1) {
                vm.rptLines = data.result.actionResp[0].lines;
                for(var i=0; i<vm.rptLines.length; i++) {
                    vm.Options[0].legend.data.push(vm.rptLines[i].name);
                    var y = [];
                    for(var j=0; j<vm.rptLines[i].points.length; j++) {
                        y.push([vm.rptLines[i].points[j].dtX, vm.rptLines[i].points[j].valY]);
                        /*                var dateFormat = 'yyyy-MM-dd HH:mm:ss';//时间格式
                         var d = $filter('date')(new Date(vm.rptLines[i].points[j].dtX), dateFormat) || '';
                         var r = vm.rptLines[i].points[j].valY;
                         vm.Options[0].tooltip.formatter = function(params) {
                         var res = params.seriesName + '<br/>' + d + '----' + r;
                         return res;
                         }*/
                    }
                    vm.Options[0].series.push({
                        name: vm.rptLines[i].name,
                        type:'line',
                        smooth:true,
                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                        data: y
                    })
                }
            }
        })
        WebProcessService.getTHAData(para).then(function(data) {
            if(data.success == 1) {
                vm.THALines = data.result.actionThroughput[0].lines;
                for(var i=0; i<vm.THALines.length; i++) {
                    vm.Options[1].legend.data.push(vm.THALines[i].name);
                    var y = [];
                    for(var j=0; j<vm.THALines[i].points.length; j++) {
                        y.push([vm.THALines[i].points[j].dtX, vm.THALines[i].points[j].valY]);
                    }
                    vm.Options[1].series.push({
                        name: vm.THALines[i].name,
                        type:'line',
                        smooth:true,
                        data: y
                    })
                }
            }
        })
        WebProcessService.getCCTData(para).then(function(data) {
            if(data.success == 1) {
                vm.CCTLines = data.result.actionConcurrece[0].lines;
                for(var i=0; i<vm.CCTLines.length; i++) {
                    vm.Options[2].legend.data.push(vm.CCTLines[i].name);
                    var y = [];
                    for(var j=0; j<vm.CCTLines[i].points.length; j++) {
                        y.push([vm.CCTLines[i].points[j].dtX, vm.CCTLines[i].points[j].valY]);
                    }
                    vm.Options[2].series.push({
                        name: vm.CCTLines[i].name,
                        type:'line',
                        smooth:true,
                        data: y
                    })
                }
            }
        })
        vm.charts = [{
            name: "TOP5 请求过程响应时间",
            tip: "Web请求过程在图表横坐标粒时间度下的总耗时时间/图表横坐标粒度时间",
            data: vm.Options[0]
        },{
            name: "TOP5 请求过程吞吐率",
            tip: "当前系统的每分钟请求数",
            data: vm.Options[1]
        },{
            name: "TOP5 请求过程并发量",
            tip: "当前系统的每分钟并发量",
            data: vm.Options[2]
        }];
    }

    vm.treeNodeClick = function(){
        vm.selected = vm.selectedNode.dimensionName;

        vm.$apply(function () {
            loadTraceTable(1);
            loadUrlChartsData();
            $(".detail-page").removeClass("animated fadeOutRightBig").addClass("animated fadeInRightBig").css("visibility","visible");
        })
    };

    vm.UrlOption = {
        tooltip : {
            trigger: 'axis'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        legend: {
            data:['响应时间', '吞吐率']
        },
        xAxis : [
            {
                type : 'time',
                splitNumber : 5,
                boundaryGap : [ 0, 0 ]
            }
        ],
        yAxis : [
            {
                type : 'value',
                name : '响应时间',
                axisLabel : {
                    formatter: '{value} cpm'
                }
            },{
                type : 'value',
                name : '吞吐率',
                axisLabel : {
                    formatter: '{value} ms'
                }
            }
        ],
        series : [
            {
                name:'响应时间',
                type:'bar',
                data:[]
            },
            {
                name:'吞吐率',
                type:'line',
                yAxisIndex: 1,
                data:[]
            }
        ]
    };
    function loadUrlChartsData () {
        var para = {
            "url": vm.selected,
            "agentId":  1002,
            "startDate": "2015-10-27T02:43:20.783Z",
            "endDate": "2015-10-27T02:43:20.783Z"
        }
        WebProcessService.geturlChart(para).then(function(data) {
            if(data.success == 1) {
                vm.urlRPTLines = data.result.urlActionChart[0].lines;
                vm.urlTHALines = data.result.urlActionChart[1].lines;
                var x = [], y = [];
                for(var i=0; i<vm.urlRPTLines[0].points.length; i++) {
                    x.push([vm.urlRPTLines[0].points[i].dtX, vm.urlRPTLines[0].points[i].valY]);
                }
                for(var j=0; j<vm.urlTHALines[0].points.length; j++) {
                    y.push([vm.urlTHALines[0].points[j].dtX, vm.urlTHALines[0].points[j].valY]);
                }
                vm.UrlOption.series[0].data = x;
                vm.UrlOption.series[1].data = y;
            }
        })
    }
    function loadTraceTable(pageIndex) {
        var para = {
            "currentPage":pageIndex,
            "agentId": 1003,
            "pageSize": vm.pageSize,
            "url": "/event/uu.htm",
            "startDate": "2015-10-20T10:43:20.783Z",
            "endDate": "2015-10-20T12:43:20.783Z"
        }
        WebProcessService.getTraceTable(para).then(function(data) {
            if(data.success == 1) {
                vm.traceList = data.result.webTraceUrls.list;
                vm.totalCount = data.result.webTraceUrls.totalCount;
            }
        })
    }
    vm.init = function (){
        LoadUrlList();
        LoadChartsData ();
    };

    vm.appKeyChange = function(){
        detailPageHide();
        vm.$apply(function () {
            LoadUrlList();
            LoadChartsData();
        })
    }

    vm.$on('pager:pageIndexChanged', function (evt,page) {
        // 分页操作
        evt.stopPropagation();
        loadTraceTable(page + 1);
    });
    vm.isShowPageIndex = function () {
        return (vm.totalCount > vm.pageSize);
    };

    $("#backBtn").on("click",function(){
        detailPageHide();
    })

    function detailPageHide(){
        $(".detail-page").removeClass("animated fadeInRightBig").addClass("animated fadeOutRightBig");
        $(".selectTree > ul > li > a").removeClass("active");
    }
}]);