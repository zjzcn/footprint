angular.module("ng-admin").controller("WebUrlErrorCtrl", [ "$scope","WebErrorService","OverviewInfoService", function($scope, WebErrorService, OverviewInfoService) {
    var vm = $scope;
    vm.agentList = [];
    var para = {};
    vm.agentList = OverviewInfoService.getAgentSelectList(para).result.agentShowList;
    var oTable = $("#WebErrorTable").dataTable({
        "data":WebErrorService.getWebErrorList(para).data,

        "columnDefs": [ {
                "targets": 0,
                "data": "url",
                "render": function ( data, type, full, meta ) {
                  return '<a href="'+data+'" target="_blank">'+data+'</a>';
                }
            },
            {
                "targets": 1,
                "data": "time"
            },
            {
                "targets": 2,
                "data": "code"
            },
            {
                "targets": 3,
                "data": "info"
            },
            {
                "targets": 4,
                "data": "authorWeb"
            },
            {
                "targets": 5,
                "data": "authorApp"
            },
            {
                "targets": 6,
                "data": "null",
                "render": function ( data, type, full, meta ) {
                  return '<a href="javascript:;" class="row-details">详情</a>';
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
    });

    function fnFormatDetails(oTable, nTr) {
        var aData = oTable.fnGetData(nTr);
        var sOut = '<table>';
        sOut += '<tr><td>详细内容:</td><td>' + aData.detailInfo + '</td></tr>';
        sOut += '</table>';

        return sOut;
    };

    $("#WebErrorTable").on('click', ' tbody td .row-details', function () {
        var nTr = $(this).parents('tr')[0];
        if (oTable.fnIsOpen(nTr)) {
            $(this).addClass("row-details-close").removeClass("row-details-open");
            oTable.fnClose(nTr);
        } else {
            $(this).addClass("row-details-open").removeClass("row-details-close");
            oTable.fnOpen(nTr, fnFormatDetails(oTable, nTr), 'details');
        }
    });

    // WebErrorService.getWebErrorList(para).then(function(data) {
    //     if(data.success == 1){
    //     }else{
    //     }
    // });

    vm.data1 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['错误百分比'],
            x:'center',
            y:'bottom'
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
                type : 'category',
                boundaryGap : false,
                data : ['18:00','18:30','19:00','19:30','20:00','20:30','21:00']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        grid : {
            x : 60,
            x2 : 20
        },
        series : [
            {
                name:'错误百分比',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[10, 12, 21, 54, 260, 830, 710]
            }
        ]
    };

    vm.data2 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['错误百分比'],
            x:'center',
            y:'bottom'
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
                type : 'category',
                boundaryGap : false,
                data : ['18:00','18:30','19:00','19:30','20:00','20:30','21:00']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        grid : {
            x : 60,
            x2 : 20
        },
        series : [
            {
                name:'错误百分比',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[30, 182, 434, 791, 390, 30, 10]
            }
        ]
    };

}]);