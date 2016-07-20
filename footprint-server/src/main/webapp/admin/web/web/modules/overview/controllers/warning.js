angular.module("ng-admin").controller("WarningCtrl", ["$scope", "$filter", "WarningService", function($scope, $filter, WarningService) {
    var vm = $scope;
    vm.pageSize = 10;
    $scope.appKeyChange= function(){
        console.log("appKeyChange");
        $scope.$apply(function () {
            //在此写页面数据绑定变化
        });
    }

    $("#selectTreeOrder_warn").chosen({
        no_results_text : "未查询到结果", // 未检索到结果时显示的文字
        search_contains : true, // 是模糊搜索还是精确搜索
        disable_search_threshold : 10,
        width: "120px"
    });

    function loadWarningList(pageIndex){
        var para = {
            appId: 1,
            pageNo: pageIndex
        }
        WarningService.getWarningList(para).then(function(data) {
            if(data.success == 1) {
                vm.warningList = data.result.data.list;
                vm.totalDataCount = data.result.data.totalCount;
            }
            /*        var dateFormat = 'yyyy-MM-dd HH:mm:ss';//时间格式
             var oTable = $("#WarningTable").dataTable({
             "data": vm.warningList,
             "columnDefs": [ {
             "targets": 0,
             "data": "alarmLevel",
             "orderable": false,
             "render": function ( data ) {
             if(data == 'warning'){
             return '<td class="text-danger">警告</td>';
             }else if(data == 'fatal') {
             return '<td class="text-warning">严重</td>';
             }
             },
             "width": 100
             },{
             "targets": 1,
             "data": "alarmContent",
             "className": "text-center",
             "width": 300
             },{
             "targets": 2,
             "data": "createTime",
             "render": function ( data) {
             var createTime;
             createTime = $filter('date')(data, dateFormat) || '';
             return createTime;
             }
             },{
             "targets": 3,
             "data": "endTime",
             "render": function ( data) {
             var modifyTime;
             modifyTime = $filter('date')(data, dateFormat) || '';
             return modifyTime;
             }
             },{
             "targets": 4,
             "data": "duration",
             "render": function(data) {
             return data + '分钟';
             }
             },{
             "targets": 5,
             "data": "alarmState",
             "render": function ( data ) {
             if(data == '0'){
             return '<td>持续中</td>';
             }else if(data == '1') {
             return '<td>告警解除</td>';
             }
             }
             }
             ],

             "pageLength": 10,
             "language": {
             "lengthMenu": " 每页 _MENU_ 条",
             "info": "当前第_PAGE_页，总共_PAGES_页",
             "zeroRecords": "未查询到数据",
             "emptyTable": "暂无数据",
             "infoEmpty":"未查询到数据"
             },
             "ordering": false,
             "lengthChange": false,
             "searching": false
             });*/
        })
    }

    vm.init = function(){
        loadWarningList(1);
    }
    vm.$on('pager:pageIndexChanged', function (evt,page) {
        // 分页操作
        evt.stopPropagation();
        loadWarningList(page + 1);
    });
    vm.isShowPageIndex = function () {
        return (vm.totalDataCount > vm.pageSize);
    };
    vm.search = function() {
        var para = {
            appId: 1,
            startTime: "2015-10-26 08:00:00",
            endTime: "2015-10-19 14:00:00",
            alarmLevel: "fatal",
            pageNo: 1
        }
        WarningService.getWarningList(para).then(function(data) {
//            loadWarningList(1);
        })
    }
/*
   判断时间是第几周
    var getYearWeek = function (a, b, c) {

         date1是当前日期
         date2是当年第一天
         d是当前日期是今年第多少天
         用d + 当前年的第一天的周差距的和在除以7就是本年第几周

        var date1 = new Date(a, parseInt(b) - 1, c), date2 = new Date(a, 0, 1),
            d = Math.round((date1.valueOf() - date2.valueOf()) / 86400000);
        console.log("d:" +d);
        return Math.ceil(
            (d + date2.getDay()) / 7
        );
    };

    var myDate = new Date(); //当前时间
    var myWeek = getYearWeek(myDate.getFullYear(), myDate.getMonth()+1, myDate.getDate()); //当前时间是第几周
    for(var i=0; i < vm.warningList.length; i++) {
        var d = new Date(vm.warningList[i].starttime); //获取到的时间
        var week = getYearWeek(d.getFullYear(), d.getMonth()+1, d.getDate());
        if(week == myWeek) {
            vm.thisweek_list.push(vm.warningList[i]);
        }else if(week == myWeek - 1) {
            vm.lastweek_list.push(vm.warningList[i]);
        }else{
            vm.earlier_list.push(vm.warningList[i])
        }
    }

    $(function() {
        $('tr.parent')
            .click(function(){
                $(this).siblings('.child_'+this.id).toggle();
            });
    });*/
}]);