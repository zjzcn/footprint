angular.module("ng-admin").controller("SqlTraceCtrl", ["$scope", "$stateParams", "DatabaseService", function($scope, $stateParams, DatabaseService) {
    var vm = $scope;

    var para = {
        "traceId": $stateParams.traceId
    }
    DatabaseService.getTraceInfo(para).then(function(data) {
        if(data.result == 1) {

        }
    })

}]);