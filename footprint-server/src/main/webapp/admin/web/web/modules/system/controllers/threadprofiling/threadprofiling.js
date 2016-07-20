angular.module("ng-admin").controller("ThreadProfilingCtrl", ["$scope", "ProfilingService", function($scope, ProfilingService) {
    var vm = $scope;
    vm.pageSize = 10;
    function LoadAgentInfo(pageIndex) {
        var para = {
            "registerId": 9,
            "currentPage": pageIndex,
            "pageSize":vm.pageSize,
            "startDate": "2015-10-20T11:00:20.783Z",
            "endDate": "2015-10-20T11:10:20.783Z"
        }
        ProfilingService.getAgentInfo(para).then(function(data) {
            if(data.success == 1) {
                vm.agents = data.result.threadAgent.list;
                vm.totalCount = data.result.threadAgent.totalCount;
            }
        })
    }
    vm.init = function() {
        LoadAgentInfo(1);
    }
    vm.$on('pager:pageIndexChanged', function (evt,page) {
        // 分页操作
        evt.stopPropagation();
        LoadAgentInfo(page + 1);
    });
    vm.isShowPageIndex = function () {
        return (vm.totalCount > vm.pageSize);
    };
//时间轴选择
/*     $("#range_5").ionRangeSlider({
        min: 2,
        max: 10,
        type: 'single',
        step: 1,
        postfix: "m",
        hasGrid: true,
        prettify: true,
        hideText: true,
        onChange: function(obj){
            $("#selectTime").html(obj.fromNumber);
        }
    });*/

}]);