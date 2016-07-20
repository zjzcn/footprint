angular.module("ng-admin").controller("DatabaseCtrl", ["$scope", "$rootScope", "$cookieStore", "OverviewInfoService", "DatabaseService", function($scope, $rootScope, $cookieStore, OverviewInfoService, DatabaseService) {
    var vm = $scope;
    vm.pageSize = 5;
    vm.agentType = "app";
    vm.agentList = [];
    var formData = vm.formData = {};
    formData.orderType = 'throughput';
    var myDate = new Date();
    var para = {
        appId: 14,
        agentType: "jvm"
    };
    OverviewInfoService.getAgentSelectList(para).then(function(data){
        if(data.success == 1){
            vm.agentList = data.result.agentShowList;
        }
    })

    function LoadUrlList() {
        var para = {
            queryNum: 20,
            orderType: formData.orderType,
            agentId: 1003,
            startDate: "2015-10-20T10:43:20.783Z",
            endDate: "2015-10-20T12:43:20.783Z"
        }
        DatabaseService.getURLsList(para).then(function(data) {
            if(data.success == 1) {
                vm.urlList = data.result.sql;
                vm.treeList = [];
                for(var i=0; i<vm.urlList.length; i++) {
                    vm.treeList.push({
                        "dimensionCode": vm.urlList[i].sql,
                        "dimensionName": vm.urlList[i].sql,
                        "featureValue": vm.urlList[i].elaspsedTime,
                        "moreDesc":""
                    })
                }
            }
        });
        $("#selectTreeOrder_database").chosen({
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
            "startDate": "2015-10-20T10:43:20.783Z",
            "endDate": "2015-10-20T12:43:20.783Z"
        }
        DatabaseService.getRPTData(para).then(function(data) {
            if(data.success == 1) {
                vm.rptLines = data.result.sqlResp[0].lines;
                for(var i=0; i<vm.rptLines.length; i++) {
                    vm.Options[0].legend.data.push(vm.rptLines[i].name);
                    var y = [];
                    for(var j=0; j<vm.rptLines[i].points.length; j++) {
                        y.push([vm.rptLines[i].points[j].dtX, vm.rptLines[i].points[j].valY]);
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
        DatabaseService.getTHAData(para).then(function(data) {
            if(data.success == 1) {
                vm.THALines = data.result.sqlResp[0].lines;
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
        DatabaseService.getCCTData(para).then(function(data) {
            if(data.success == 1) {
                vm.CCTLines = data.result.sqlConcurrent[0].lines;
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
            name: "TOP5 SQL响应时间",
            tip: "基本的四种SQM DML操作（INSERT,UPDATE,SELECT,DELETE）的平均响应时间",
            data: vm.Options[0]
        },{
            name: "TOP5 SQL吞吐率",
            tip: "基本的四种SQM DML操作（INSERT,UPDATE,SELECT,DELETE）的吞吐率",
            data: vm.Options[1]
        },{
            name: "TOP5 SQL并发量",
            tip: "基本的四种SQM DML操作（INSERT,UPDATE,SELECT,DELETE）的并发量",
            data: vm.Options[2]
        }]
    }

    vm.treeNodeClick = function(){
        vm.selected = vm.selectedNode.dimensionName;
        vm.$apply(function () {
            loadTraceTable(1);
            loadUrlChartsData();
            $(".detail-page").removeClass("animated fadeOutRightBig").addClass("animated fadeInRightBig").css("visibility","visible");
        })
    };

    function loadUrlChartsData () {
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
        var para = {
            "sql": vm.selected,
            "agentId":  1003,
            "startDate": "2015-10-20T10:43:20.783Z",
            "endDate": "2015-10-20T12:43:20.783Z"
        }
        DatabaseService.geturlChart(para).then(function(data) {
            if(data.success == 1) {
                vm.urlRPTLines = data.result.sqlData[0].lines;
                vm.urlTHALines = data.result.sqlData[1].lines;
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
            console.log(JSON.stringify(vm.UrlOption));
        })
    }
    function loadTraceTable(pageIndex) {
        var para = {
            "url": vm.selected,
            "agentId":  1003,
            "pageNum": pageIndex,
            "startDate": "2015-10-20T10:43:20.783Z",
            "endDate": "2015-10-20T12:43:20.783Z"
        }
        DatabaseService.getTraceTable(para).then(function(data) {
            if(data.success == 1) {
                vm.TraceList = data.result.xxx;
            }
        })
    }
    vm.init = function() {
        LoadUrlList ();
        LoadChartsData();
    };

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

    vm.appKeyChange = function(){
        detailPageHide();
        vm.$apply(function () {
            LoadUrlList();
            LoadChartsData();
        })
    }

    function detailPageHide(){
        $(".detail-page").removeClass("animated fadeInRightBig").addClass("animated fadeOutRightBig");
        $(".selectTree > ul > li > a").removeClass("active");
    }

}]);