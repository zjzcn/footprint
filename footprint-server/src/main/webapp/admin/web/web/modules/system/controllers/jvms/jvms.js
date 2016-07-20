angular.module("ng-admin").controller("JVMsCtrl", ["$scope", "$filter", "JVMsService", "OverviewInfoService", "GetChartsService", function($scope, $filter, JVMsService, OverviewInfoService, GetChartsService) {
    var vm = $scope;
    vm.agentType = "app";
    vm.agentList = [];
    var para = {
        appId: 9,
        agentType: "jvm"
    };
    OverviewInfoService.getAgentSelectList(para).then(function(data){
        if(data.success == 1){
            vm.agentList = data.result.agentShowList;
        }
    })
    var myDate = new Date();
    var date0 = myDate.getTime()-(2*60*60*1000);
    var date1 = new Date(date0);
    var param = {
        "agentId": 1006,
        "startDate": date1,
        "endDate": myDate
    };
    JVMsService.getJVMInfo(param).then(function(data) {
        if(data.success == 1) {
            vm.cpuUseRate = data.result.jvmInfo.cpuUseRate;
            vm.errorRate = data.result.jvmInfo.errorRate;
            vm.memUse = data.result.jvmInfo.memUse;
            vm.nodeSign = data.result.jvmInfo.nodeSign;
            vm.responseTime = data.result.jvmInfo.responseTime;
            vm.rpm = data.result.jvmInfo.rpm;
        }
    })

    vm.tabs = [{
        title: "内存使用量",
        charts:[{
            data: [],
            moduleName: "HMU",
            tip: "展示Used Heap,Max Heap,Committed Heap三个内存指标的变化",
            name: "Heap Memory Usage(MB)"
        },{
            data: [],
            moduleName: "NHM",
            tip: "xxx",
            name: "Non Heap memory usage (MB)"
        },{
            data: [],
            moduleName: "PMU",
            tip: "xxx",
            name: "Physical memory usage (MB)"
        },{
            data: [],
            moduleName: "PMU",
            tip: "xxx",
            name: "Runtime memory usage (MB)"
        }]
    },{
        title: "线程",
        charts:[{
            data: [],
            moduleName: "Thread",
            tip: "xxx",
            name: "Thread Count"
        },{
            data: [],
            moduleName: "Class",
            tip: "xxx",
            name: "Class count"
        }]
    },{
        title: "GC",
        charts:[{
            data: [],
            moduleName: "CPG",
            tip: "xxx",
            name: "perm Gen (MB)"
        },{
            data: [],
            moduleName: "COG",
            tip: "xxx",
            name: "old Gen (MB)"
        },{
            data: [],
            moduleName: "PSS",
            tip: "xxx",
            name: "Par Survivor Space (MB)"
        },{
            data: [],
            moduleName: "PES",
            tip: "xxx",
            name: "Eden Space (MB)"
        },{
            data: [],
            moduleName: "GCY",
            tip: "xxx",
            name: "youngGC"
        },{
            data: [],
            moduleName: "GCF",
            tip: "xxx",
            name: "fullGC"
        }]
    }];

    vm.selectedTab = vm.tabs[0];
    vm.selectTab = function(tab){
        vm.selectedTab = tab;
    };
    vm.MemOptions = [];
    for(var i=0; i<4; i++){
        vm.MemOptions[i] = {
            tooltip : {
                trigger: 'item'
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
                        formatter: '{value} '
                    }
                }
            ],
            series : []
        };
        vm.tabs[0].charts[i].data = vm.MemOptions[i];
    }
    vm.ThreadOptions = [];
    for(var j=0; j<2; j++){
        vm.ThreadOptions[j] = {
            tooltip : {
                trigger: 'item'
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
                        formatter: '{value}'
                    }
                }
            ],
            series : []
        };
        vm.tabs[1].charts[j].data = vm.ThreadOptions[j];
    }
    vm.GCOptions = [];
    for(var k=0; k<6; k++){
        vm.GCOptions[k] = {
            tooltip : {
                trigger: 'item'
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
                        formatter: '{value}'
                    }
                }
            ],
            series : []
        };
        vm.tabs[2].charts[k].data = vm.GCOptions[k];
    }

    JVMsService.getHMUData(param).then(function(data) {
        if(data.success == 1) {
            vm.HMULines = data.result.heapMemory[0].lines;
        }
        for(var i=0; i<vm.HMULines.length; i++) {
            vm.MemOptions[0].legend.data.push(vm.HMULines[i].name)
            var y = [];
            for(var j=0; j<vm.HMULines[i].points.length; j++) {
                y.push([vm.HMULines[i].points[j].dtX, (vm.HMULines[i].points[j].valY)/(1024*1024)]);
            }
            vm.MemOptions[0].series.push({
                name: vm.HMULines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getNHMData(param).then(function(data) {
        if(data.success == 1) {
            vm.NHMLines = data.result.nonheapMemory[0].lines;
        }
        for(var i=0; i<vm.NHMLines.length; i++) {
            vm.MemOptions[1].legend.data.push(vm.NHMLines[i].name)
            var y = [];
            for(var j=0; j<vm.NHMLines[i].points.length; j++) {
                y.push([vm.NHMLines[i].points[j].dtX, (vm.NHMLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.MemOptions[1].series.push({
                name: vm.NHMLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getPMUData(param).then(function(data) {
        if(data.success == 1) {
            vm.PMULines = data.result.physicalMemory[0].lines;
        }
        for(var i=0; i<vm.PMULines.length; i++) {
            vm.MemOptions[2].legend.data.push(vm.PMULines[i].name)
            var y = [];
            for(var j=0; j<vm.PMULines[i].points.length; j++) {
                y.push([vm.PMULines[i].points[j].dtX, (vm.PMULines[i].points[j].valY)/(1024*1024)]);
            }
            vm.MemOptions[2].series.push({
                name: vm.PMULines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getRMUData(param).then(function(data) {
        if(data.success == 1) {
            vm.RMULines = data.result.runtimeMemory[0].lines;
        }
        for(var i=0; i<vm.RMULines.length; i++) {
            vm.MemOptions[3].legend.data.push(vm.RMULines[i].name)
            var y = [];
            for(var j=0; j<vm.RMULines[i].points.length; j++) {
                y.push([vm.RMULines[i].points[j].dtX, (vm.RMULines[i].points[j].valY)/(1024*1024)]);
            }
            vm.MemOptions[3].series.push({
                name: vm.RMULines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getThreadData(param).then(function(data) {
        if(data.success == 1) {
            vm.ThreadLines = data.result.thread[0].lines;
        }
        for(var i=0; i<vm.ThreadLines.length; i++) {
            vm.ThreadOptions[0].legend.data.push(vm.ThreadLines[i].name)
            var y = [];
            for(var j=0; j<vm.ThreadLines[i].points.length; j++) {
                y.push([vm.ThreadLines[i].points[j].dtX, (vm.ThreadLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.ThreadOptions[0].series.push({
                name: vm.ThreadLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getClassData(param).then(function(data) {
        if(data.success == 1) {
            vm.ClassLines = data.result.class[0].lines;
        }
        for(var i=0; i<vm.ClassLines.length; i++) {
            vm.ThreadOptions[1].legend.data.push(vm.ClassLines[i].name)
            var y = [];
            for(var j=0; j<vm.ClassLines[i].points.length; j++) {
                y.push([vm.ClassLines[i].points[j].dtX, (vm.ClassLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.ThreadOptions[1].series.push({
                name: vm.ClassLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getTop4Thread(param).then(function(data) {
        if(data.success == 1) {
            vm.Threads = data.result.thread;
        }
        for(var i=0; i<vm.Threads.length; i++) {
            vm.ThreadOptions.push({
                tooltip : {
                    trigger: 'item'
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
                            formatter: '{value} '
                        }
                    }
                ],
                series : []
            })
            for(var j=0; j<vm.Threads[i].lines.length; j++) {
                vm.ThreadOptions[i+2].legend.data.push(vm.Threads[i].lines[j].name);
                var y = [];
                for(var k=0; k<vm.Threads[i].lines[j].points.length; k++){
                    y.push([vm.Threads[i].lines[j].points[k].dtX, vm.Threads[i].lines[j].points[k].valY]);
                }
                vm.ThreadOptions[i+2].series.push({
                    name: vm.Threads[i].lines[j].name,
                    type:'line',
                    smooth:true,
                    data: y
                })
            }
            vm.tabs[1].charts.push({
                data: vm.ThreadOptions[i+2],
                moduleName: "Class",
                tip: vm.Threads[i].description,
                name: vm.Threads[i].name
            })
        }
    })
    JVMsService.getGCYData(param).then(function(data) {
        if(data.success == 1) {
            vm.GCYLines = data.result.gcYoung[0].lines;
        }
        for(var i=0; i<vm.GCYLines.length; i++) {
            vm.GCOptions[0].legend.data.push(vm.GCYLines[i].name)
            var y = [];
            for(var j=0; j<vm.GCYLines[i].points.length; j++) {
                y.push([vm.GCYLines[i].points[j].dtX, (vm.GCYLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.GCOptions[0].series.push({
                name: vm.GCYLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getGCFData(param).then(function(data) {
        if(data.success == 1) {
            vm.GCFLines = data.result.gcFull[0].lines;
        }
        for(var i=0; i<vm.GCFLines.length; i++) {
            vm.GCOptions[1].legend.data.push(vm.GCFLines[i].name)
            var y = [];
            for(var j=0; j<vm.GCFLines[i].points.length; j++) {
                y.push([vm.GCFLines[i].points[j].dtX, (vm.GCFLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.GCOptions[1].series.push({
                name: vm.GCFLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getPSSData(param).then(function(data) {
        if(data.success == 1) {
            vm.PSSLines = data.result.gcSurvivor[0].lines;
        }
        for(var i=0; i<vm.PSSLines.length; i++) {
            vm.GCOptions[2].legend.data.push(vm.PSSLines[i].name)
            var y = [];
            for(var j=0; j<vm.PSSLines[i].points.length; j++) {
                y.push([vm.PSSLines[i].points[j].dtX, (vm.PSSLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.GCOptions[2].series.push({
                name: vm.PSSLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getPESData(param).then(function(data) {
        if(data.success == 1) {
            vm.PESLines = data.result.gcEden[0].lines;
        }
        for(var i=0; i<vm.PESLines.length; i++) {
            vm.GCOptions[3].legend.data.push(vm.PESLines[i].name)
            var y = [];
            for(var j=0; j<vm.PESLines[i].points.length; j++) {
                y.push([vm.PESLines[i].points[j].dtX, (vm.PESLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.GCOptions[3].series.push({
                name: vm.PESLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getCOGData(param).then(function(data) {
        if(data.success == 1) {
            vm.COGLines = data.result.gcOld[0].lines;
        }
        for(var i=0; i<vm.COGLines.length; i++) {
            vm.GCOptions[4].legend.data.push(vm.COGLines[i].name)
            var y = [];
            for(var j=0; j<vm.COGLines[i].points.length; j++) {
                y.push([vm.COGLines[i].points[j].dtX, (vm.COGLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.GCOptions[4].series.push({
                name: vm.COGLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })
    JVMsService.getCPGData(param).then(function(data) {
        if(data.success == 1) {
            vm.CPGLines = data.result.gcPerm[0].lines;
        }
        for(var i=0; i<vm.CPGLines.length; i++) {
            vm.GCOptions[5].legend.data.push(vm.CPGLines[i].name)
            var y = [];
            for(var j=0; j<vm.CPGLines[i].points.length; j++) {
                y.push([vm.CPGLines[i].points[j].dtX, (vm.CPGLines[i].points[j].valY)/(1024*1024)]);
            }
            vm.GCOptions[5].series.push({
                name: vm.CPGLines[i].name,
                type:'line',
                smooth:true,
                data: y
            })
        }
    })

}]);