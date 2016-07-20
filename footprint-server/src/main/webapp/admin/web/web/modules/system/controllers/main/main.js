angular.module("ng-admin").controller('SystemMainController',
 ["DialogService", "$rootScope", "$state", '$scope', "SystemMainService", "OverviewInfoService", "$compile",
 function(DialogService, $rootScope, $state, $scope, SystemMainService, OverviewInfoService, $compile) {

     var vm = $scope;
     vm.agentList = [];
     var param = {
         appId: 14
     }
     OverviewInfoService.getAllAgentSelectList(param).then(function(data){
         if(data.success == 1){
             vm.agentList = data.result.agentShowList;
         }
     })
     vm.agentId = "all";
     vm.agentType = "all";

     vm.appKeyChange = function(){
//         var appTd = $.cookie('selectSys')
     }
     vm.agentKeyChange = function(){
//         var agentId = $.cookie('selectAgentall')
     }
     vm.dateRangeChange = function() {
/*         var time = getSelectTime( $.cookie('selectTime') );
         vm.startTime = time.startTime;
         vm.endTime = time.endTime;*/
     }

     vm.Options = [];
     for(var i=0; i<5; i++) {
         vm.Options[i] = {
             tooltip : {
                 trigger: 'item',
                 formatter: function () {}
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
             /*        grid : {
              x : 60,
              x2 : 40,
              y2 : 80
              },*/
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
         }
     }
     var params = {
         "agentId":1003,
         "startDate":"2015-10-27T02:43:20.783Z",
         "endDate":"2015-10-27T02:43:20.783Z"
     }
     SystemMainService.getPerformanceData(params).then(function(data) {
         if(data.success == 1) {
             vm.RPTLines = data.result.responseTime[0].lines;
             for(var i=0; i<vm.RPTLines.length; i++) {
                 vm.Options[0].legend.data.push(vm.RPTLines[i].name);
                 var y = [];
                 for(var j=0; j<vm.RPTLines[i].points.length; j++) {
                     y.push([vm.RPTLines[i].points[j].dtX, vm.RPTLines[i].points[j].valY]);
                     var from = vm.RPTLines[i].points[j].fromDt,
                         to = vm.RPTLines[i].points[j].toDt,
                         current = vm.RPTLines[i].points[j].valY,
                         max = vm.RPTLines[i].points[j].maximum;
                     vm.Options[0].tooltip.formatter = function(params) {
                         var res = params.seriesName;
                         res += '<br/>时间段：' + from + '--' + to;
                         res += '<br/>当前值：' + current;
                         res += '<br/>最大值：' + max;
                         return res;
                     }
                 }
                 vm.Options[0].series.push({
                     name: vm.RPTLines[i].name,
                     type:'line',
                     smooth:true,
                     itemStyle: {normal: {areaStyle: {type: 'default'}}},
                     data: y
                 })
             }
         }
     });
     SystemMainService.getPerformanceData(params).then(function(data) {
         if(data.success == 1) {
             vm.THALines = data.result.responseTime[1].lines;
             for(var i=0; i<vm.THALines.length; i++) {
                 vm.Options[1].legend.data.push(vm.THALines[i].name);
                 var y = [];
                 for(var j=0; j<vm.THALines[i].points.length; j++) {
                     y.push([vm.THALines[i].points[j].dtX, vm.THALines[i].points[j].valY]);
                     var from = vm.THALines[i].points[j].fromDt,
                         to = vm.THALines[i].points[j].toDt,
                         current = vm.THALines[i].points[j].valY,
                         max = vm.THALines[i].points[j].maximum;
                     vm.Options[1].tooltip.formatter = function(params) {
                         var res = params.seriesName;
                         res += '<br/>时间段：' + from + '--' + to;
                         res += '<br/>当前值：' + current;
                         res += '<br/>最大值：' + max;
                         return res;
                     }
                 }
                 vm.Options[1].series.push({
                     name: vm.THALines[i].name,
                     type:'line',
                     smooth:true,
                     itemStyle: {normal: {areaStyle: {type: 'default'}}},
                     data: y
                 })
             }
         }
     });
     SystemMainService.getPerformanceData(params).then(function(data) {
         if(data.success == 1) {
             vm.ErrorLines = data.result.responseTime[2].lines;
             for(var i=0; i<vm.ErrorLines.length; i++) {
                 vm.Options[2].legend.data.push(vm.ErrorLines[i].name);
                 var y = [];
                 for(var j=0; j<vm.ErrorLines[i].points.length; j++) {
                     y.push([vm.ErrorLines[i].points[j].dtX, vm.ErrorLines[i].points[j].valY]);
                     var from = vm.ErrorLines[i].points[j].fromDt,
                         to = vm.ErrorLines[i].points[j].toDt,
                         current = vm.ErrorLines[i].points[j].valY,
                         max = vm.ErrorLines[i].points[j].maximum;
                     vm.Options[2].tooltip.formatter = function(params) {
                         var res = params.seriesName;
                         res += '<br/>时间段：' + from + '--' + to;
                         res += '<br/>当前值：' + current;
                         res += '<br/>最大值：' + max;
                         return res;
                     }
                 }
                 vm.Options[2].series.push({
                     name: vm.ErrorLines[i].name,
                     type:'line',
                     smooth:true,
                     data: y
                 })
             }
         }
     });
     SystemMainService.getCPUData(params).then(function(data) {
         if(data.success == 1) {
             vm.CPULines = data.result.cpuUsage[0].lines;
             for(var i=0; i<vm.CPULines.length; i++) {
                 vm.Options[3].legend.data.push(vm.CPULines[i].name);
                 var y = [];
                 for(var j=0; j<vm.CPULines[i].points.length; j++) {
                     y.push([vm.CPULines[i].points[j].dtX, vm.CPULines[i].points[j].valY]);
                     var from = vm.CPULines[i].points[j].fromDt,
                         to = vm.CPULines[i].points[j].toDt,
                         current = vm.CPULines[i].points[j].valY,
                         max = vm.CPULines[i].points[j].maximum;
                     vm.Options[3].tooltip.formatter = function(params) {
                         var res = params.seriesName;
                         res += '<br/>时间段：' + from + '--' + to;
                         res += '<br/>当前值：' + current;
                         res += '<br/>最大值：' + max;
                         return res;
                     }
                 }
                 vm.Options[3].series.push({
                     name: vm.CPULines[i].name,
                     type:'line',
                     smooth:true,
                     data: y
                 })
             }
         }
     });
     SystemMainService.getMemData(params).then(function(data) {
         if(data.success == 1) {
             vm.MemLines = data.result.physicalMemory[0].lines;
             for(var i=0; i<vm.MemLines.length; i++) {
                 vm.Options[4].legend.data.push(vm.MemLines[i].name);
                 var y = [];
                 for(var j=0; j<vm.MemLines[i].points.length; j++) {
                     y.push([vm.MemLines[i].points[j].dtX, vm.MemLines[i].points[j].valY]);
                     var from = vm.MemLines[i].points[j].fromDt,
                         to = vm.MemLines[i].points[j].toDt,
                         current = vm.MemLines[i].points[j].valY,
                         max = vm.MemLines[i].points[j].maximum;
                     vm.Options[4].tooltip.formatter = function(params) {
                         var res = params.seriesName;
                         res += '<br/>时间段：' + from + '--' + to;
                         res += '<br/>当前值：' + current;
                         res += '<br/>最大值：' + max;
                         return res;
                     }
                 }
                 vm.Options[4].series.push({
                     name: vm.MemLines[i].name,
                     type:'line',
                     smooth:true,
                     data: y
                 })
             }
         }
     });
     vm.charts1 = [{
         name: "总体响应时间",
         tip: "展示该系统此时间段内各服务从收到请求到返回的平均总体响应时间",
         data: vm.Options[0]
     },{
         name: "总体吞吐率",
         tip: "展示该系统此时间段内各服务总体平均吞吐率（应用过程的吞吐率，即每分钟请求数）",
         data: vm.Options[1]
     }];
     vm.charts2 = [{
         name: "错误率",
         tip: "展示该系统此时间段内web服务、app服务和SQL发生的错误或异常数量占总请求数量的百分比",
         data: vm.Options[2]
     },{
         name: "CPU",
         tip: "CPU",
         data: vm.Options[3]
     },{
         name: "内存",
         tip: "内存",
         data: vm.Options[4]
     }]

     function LoadEvents() {
         var para = {
             registerId: 8,
             agentId: 1,
             startDate: "1430498001000",
             endDate: "1445341222408",
             pageNum: 1,
             pageSize: 10
         };
         var para1 = {
             registerId: 8,
             agentId: 1,
             eventType: "WARNING",
             startDate: "1430498001000",
             endDate: "1445341222408",
             pageNum: 1,
             pageSize: 10
         };
         var para2 = {
             registerId: 8,
             agentId: 1,
             eventType: "FATAL",
             startDate: "1430498001000",
             endDate: "1445341222408",
             pageNum: 1,
             pageSize: 10
         };
         var para3 = {
             registerId: 8,
             agentId: 1,
             eventType: "COMPOUND",
             startDate: "1430498001000",
             endDate: "1445341222408",
             pageNum: 1,
             pageSize: 10
         }
         SystemMainService.getEventsList(para).then(function(data) {
             if(data.success == 1) {
                 vm.AllEvents = data.result.pageObject.list;
                 vm.count_all = data.result.pageObject.totalCount;
             }
         });
         SystemMainService.getEventsList(para1).then(function(data) {
             if(data.success == 1) {
                 vm.WarnEvents = data.result.pageObject.list;
                 vm.count_warn = data.result.pageObject.totalCount;
             }
         });
         SystemMainService.getEventsList(para2).then(function(data) {
             if(data.success == 1) {
                 vm.FatalEvents = data.result.pageObject.list;
                 vm.count_fatal = data.result.pageObject.totalCount;
             }
         });
         SystemMainService.getEventsList(para3).then(function(data) {
             if(data.success == 1) {
                 vm.ConfEvents = data.result.pageObject.list;
                 vm.count_conf = data.result.pageObject.totalCount;
             }
         })
     }
     //已阅未阅状态
     vm.remark = function(item) {
         DialogService.modal({
             key:"Baymax.System.NoteDialog",
             url:"modules/system/templates/main/note-dialog.html",
             accept:function(data){
             }
         },item);
     }
/*  var oTable = $("#ServerTable").DataTable({
         "data": SystemInfoService.getServerList(para).data,
         "columnDefs": [{
             "targets": 0,
             "data": "Server"
         },{
             "targets": 1,
             "data": "resp",
             "render": function ( data, type, full, meta ) {
                 return '<span>'+data+'</span>';
             }
         },{
             "targets": 2,
             "data": "rpm",
             "render": function ( data, type, full, meta ) {
                 return '<span>'+data+'</span>';
             }
         },{
             "targets": 3,
             "data": "error",
             "render": function ( data, type, full, meta ) {
                 return '<span>'+data+'</span>';
             }
         },{
             "targets": 4,
             "data": "cpu",
             "render": function ( data, type, full, meta ) {
                 return '<span>'+data+'</span>';
             }
         },{
             "targets": 5,
             "data": "memory",
             "render": function ( data, type, full, meta ) {
                 return '<span>'+data+'</span>';
             }
         }],
         "dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // datatable layout without  horizobtal scroll
         "scrollY": "300",
         "deferRender": true,
         "paging": false,
         "info": false,
         "ordering": false,
         "lengthChange": false,
         "searching": false
     });

     $('#ServerTable tbody').on( 'mouseenter', 'span', function (e) {
         var $cell = $(this).parent("td").eq(0),
             data = oTable.cell($cell).data(),//获取这个td的值
             index = oTable.cell($cell).index(),
             dataSrc = oTable.column( index.column ).dataSrc(),//获取这个td的dataname
             server = oTable.row($(this).parents("tr").eq(0)).data().Server;//获取这个td的server
         var positionX = e.clientX, //获取当前鼠标相对img的X坐标
             positionY = e.clientY; //获取当前鼠标相对img的Y坐标
         var _leg = $('#ServerTable thead').find("th").eq(index.column).find("span").html();

         $scope.chartsData = {
             title : {
                 text: server
             },
             tooltip : {
                 trigger: 'axis'
             },
             legend: {
                 data:[_leg],
                 y: 'bottom'
             },
             toolbox: {
                 show : false,
             },
             grid : {
                 x : 60,
                 x2 : 40,
                 y2 : 80
             },
             calculable : true,
             xAxis : [
                 {
                     type : 'category',
                     boundaryGap : false,
                     data : ['18:10','18:15','18:20','18:25']
                 }
             ],
             yAxis : [
                 {
                     type : 'value',
                     axisLabel : {
                         formatter: '{value} rpm'
                     }
                 }
             ],
             series : [
                 {
                     name:_leg,
                     type:'line',
                     smooth:true,
                     itemStyle: {normal: {areaStyle: {type: 'default'}}},
                     data:[ 100*Math.random().toFixed(2), 100*Math.random().toFixed(2), 100*Math.random().toFixed(2), 100*Math.random().toFixed(2)]
                 }
             ]
         };
         var html = '<div class="tool-charts" echarts="chartsData"></div>';
         $html = $compile(html)($scope);// 编译html
         $("#chartsWrap").html($html);// 插入到dom

         $("#chartsWrap").css("top",positionY).css("left",positionX).fadeIn();
         //console.log( dataSrc+"---------"+data+"---------"+server);

         $cell.mouseleave(function(e){
             $("#chartsWrap").fadeOut();
             $(this).unbind(e);
         })

     });*/

    vm.init = function() {
        LoadEvents();
    };

}]);