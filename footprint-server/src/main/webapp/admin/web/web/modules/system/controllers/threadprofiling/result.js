angular.module("ng-admin").controller("ResultCtrl", ["$scope", "$stateParams", "ProfilingService", function($scope, $stateParams, ProfilingService) {

    var vm = $scope;
    var para = {
        "agentId": $stateParams.agentId,
        "currentDate": "2015-10-20T11:02:00.000Z"
    }
    ProfilingService.getStackInfo(para).then(function(data) {
        if(data.success == 1) {

        }
    })

    $('#nestable_list_1').nestable('collapseAll');

    $('#nestable_list_menu').on('click', function (e) {
        var target = $(e.target),
            action = target.data('action');
        if (action === 'expand-all') {
            $('.dd').nestable('expandAll');
        }
        if (action === 'collapse-all') {
            $('.dd').nestable('collapseAll');
        }
    });

    $('#treeTable').treetable({
        clickableNodeNames: true,
        expandable: false,
        initialState: 'expanded'
    });
}]);