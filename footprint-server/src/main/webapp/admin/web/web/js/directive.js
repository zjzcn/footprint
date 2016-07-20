angular.module("ng-admin").directive('a', function() {
    return {
        restrict: 'E',
        link: function(scope, elem, attrs) {
            if (attrs.ngClick || attrs.href === '' || attrs.href === '#') {
                elem.on('click', function(e) {
                    e.preventDefault(); // prevent link click for above criteria
                });
            }
        }
    };
});

//tooltip指令
angular.module("ng-admin").directive('toolTip', ["LazyLoader", function(LazyLoader) {
    var i = 0;
    return {
        restrict: 'EA',
        scope: {
            type: "@toolType",
            url: "@toolUrl",
            text: "@toolText"
        },
        link: function(scope, elem, attrs) {
            var tip = "",
                tipType = "",
                url = "javascript:;";
            if(scope.text){tip = scope.text;} 
            if(scope.url){url = scope.url;} 
            if(scope.type){
                tipType = scope.type;
                if(toolTipDictionary[$.trim(tipType)]){
                    tip = toolTipDictionary[$.trim(tipType)];
                }
            } 
            if($.trim(tip) != ""){
                new jBox('Tooltip',{
                    id:'jBox_'+i,
                    attach:elem,
                    zIndex:8000,
                    maxWidth:200,
                    closeOnMouseleave:true,
                    position:{x:'center',y:'bottom'},
                    animation:'zoomIn',
                    content:'<a href="'+url+'">'+tip+'</a>'
                });
                i = i+1;
            }

        },
        controller: function($scope){
        }
    };
}]);

//添加仪表盘
angular.module("ng-admin").directive('addDashboard', ["DialogService", function(DialogService) {
     return {
        restrict: 'EA',
        priority: 2,
        scope: {
        },
        link: function(scope, elem, attrs) {
            var addBtn = angular.element($(elem).find('.dashboardBtn'));
            addBtn.bind('click', function(event) {
                var param = {};
                param.title = $(elem).find(".caption > span").html();
                param.tip = $(elem).find(".caption > i").attr("tool-text");

                DialogService.modal({
                    key:"Baymax.Alarm.AddDashboardDialog",
                    url:"modules/portal/templates/adddashboarddlg.html",
                    accept:function(data){
                    }
                }, param);
            });
        },
        controller: function($scope){

        }
    }
}]);

//slimscroll
angular.module("ng-admin").directive('slimScroll', ["LazyLoader", function(LazyLoader) {
     return {
        restrict: 'EA',
        scope: {
        },
        link: function(scope, elem, attrs) {
            LazyLoader.load(["js/lib/jquery.slimscroll.min.js"])
                .then(function () {

                    $(elem).each(function() {
                        if ($(this).attr("data-initialized")) {
                            return; // exit
                        }

                        var height;

                        if ($(this).attr("data-height")) {
                            height = $(this).attr("data-height");
                        } else {
                            height = $(this).css('height');
                        }

                        $(this).slimScroll({
                            allowPageScroll: true, // allow page scroll when the element scroll is ended
                            size: '7px',
                            color: ($(this).attr("data-handle-color") ? $(this).attr("data-handle-color") : '#bbb'),
                            wrapperClass: ($(this).attr("data-wrapper-class") ? $(this).attr("data-wrapper-class") : 'slimScrollDiv'),
                            railColor: ($(this).attr("data-rail-color") ? $(this).attr("data-rail-color") : '#eaeaea'),
                            height: height,
                            alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
                            railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
                            disableFadeOut: true
                        });

                        $(this).attr("data-initialized", "1");
                    });

                })
        },
        controller: function($scope){

        }
    }
}]);

//系统选择
angular.module("ng-admin").directive('systemSelect', ["OverviewInfoService", "$cookieStore", '$compile',
    function(OverviewInfoService, $cookieStore, $compile) {
     return {
        transclude: true,
        restrict: 'A',
        scope: {
            onChange: '&'
        },
        link: function(scope, elem, attrs) {

            OverviewInfoService.getSysSelectList().then(function(data) {
                if(data.success == 1){
                    scope.data = data.result.appShowList;
                }

                //获取cookie选择appkey
                var selectSys = $.cookie('selectSys');
                if(selectSys == undefined){
                    if(scope.data.length > 0){
                        $.cookie('selectSys', scope.data[0].appId);
                    }
                }

                angular.forEach(scope.data, function(value, index) {
                    var _select = "";
                    if( value.appId == $.cookie('selectSys')){
                        _select = 'selected="selected"';
                    }
                    var mask = angular.element('<option value="'+value.appId+'"'+_select+'>'+value.appName+'</option>');
                    elem.append(mask);
                });

                $(elem).chosen({
                    no_results_text: "未查询到结果",
                    search_contains: true,
                    disable_search_threshold: 10,
                    width: "200px"
                });

                $(elem).on("change",function(){
                    var key = $(elem).val();
                    //选择更新select的值
                    $.cookie('selectSys', key);
                    scope.onChange();
                });
            })
        },

        controller: function($scope){

        }
    }
}]);

//Agent选择
angular.module("ng-admin").directive('agentSelect', ["OverviewInfoService", "$cookieStore", '$compile',
    function(OverviewInfoService, $cookieStore, $compile) {
        return {
            transclude: true,
            restrict: 'A',
            scope: {
                onChange: '&',
                data: "=?data",
                defaultText: "@defaultText",
                defaultValue: "@defaultValue",
                agentId:'=?agentId',
                agentType:'=?agentType'
            },
            link: function(scope, elem, attrs) {
                var typeV = "selectAgent";
                if(scope.agentType) {
                    typeV += scope.agentType;
                }

                //获取cookie选择appkey
                var selectAgent = $.cookie(typeV);
                if(selectAgent == undefined){
                    if(scope.data.length > 0){
                        $.cookie(typeV, scope.data[0].agentId);
                    }
                }

                scope.$watch("data", function(value) {
                    try {
                        
                        $(elem).empty();

                        if(scope.defaultValue && scope.defaultText){
                            elem.append("<option value='"+scope.defaultValue+"'>"+scope.defaultText+"</option>");
                        }

                        angular.forEach(scope.data, function(value, index) {
                            //设置选中状态
                            var _select = "";
                            if( value.agentId == $.cookie(typeV)){
                                _select = 'selected="selected"';
                            }
                            var mask = angular.element('<option value="'+value.agentId+'"'+_select+'>'+value.agentName+'</option>');
                            elem.append(mask);
                        });

                        scope.agentId = scope.data[0].agentId;

                        if($(elem).next(".chosen-container").length > 0){
                             $(elem).trigger('chosen:updated');
                        }else{
                            $(elem).chosen({
                                no_results_text: "未查询到结果",
                                search_contains: true,
                                disable_search_threshold: 10,
                                width: "240px"
                            });
                        }
                    } catch (ex) {
                        console.log(ex);
                    }
                }, true);

                
                $(elem).on("change",function(){
                    var key = $(elem).val();
                    //选择更新select的值
                    $.cookie(typeV, key);
                    scope.onChange();
                });
            }
        }
}]);

//日历控件
angular.module("ng-admin").directive('dateRangePicker', ["LazyLoader", 
    function(LazyLoader) {
     return {
        transclude: true,
        restrict: 'A',
        scope: {
            onChange: '&'
        },
        link: function(scope, elem, attrs) {

            //获取cookie选择时间
            var selectTime =  $.cookie('selectTime');
            if(selectTime == undefined){
                $.cookie('selectTime', '最近30分钟');
            }

            var mask = angular.element('<i class="fa fa-calendar"></i><span>'+$.cookie('selectTime')+'</span><b class="caret"></b>');
            elem.append(mask);

            var cb = function(start, end, label) {
                 var $timeHide;
                 if( label == optionSet.locale.customRangeLabel){
                     var timeRange = start.format('YYYY-MM-DD HH:mm:ss') + ' -- ' + end.format('YYYY-MM-DD HH:mm:ss');
                     $(elem).find("span").html(timeRange);
                 }else{
                     $(elem).find("span").html(label);
                 }

                 //写入cookie
                 $.cookie('selectTime', $(elem).find("span").html());

                 //调回调
                 scope.onChange();
                 //$.cookie(usernum+"-selectTime", $('#defaultrange span').html(), { expires: 30, path: "/" });
                 //callback(obj.appKey,obj.appVersion,obj.startTime,obj.endTime);
             };

             var optionSet = {
                 startDate: moment().subtract(29, 'minute'),
                 endDate: moment(),
                 maxDate: moment().hours(23).minutes(59).seconds(59),
                 minDate: moment().subtract(6, 'days'),
                 timePicker: true,
                 timePicker12Hour: false,
                 timePickerIncrement:1,
                 timePickerSeconds:true,
                 ranges: {
                    '最近30分钟': [moment().subtract(29, 'minute')],
                    '最近1小时': [moment().subtract(1, 'hour'), moment()],
                    '最近6小时': [moment().subtract(6, 'hour'), moment()],
                    '最近12小时': [moment().subtract(12, 'hour'), moment()],
                    '最近1天': [moment(), moment()],
                    '最近3天': [moment().subtract(2, 'days'), moment()],
                    '最近7天': [moment().subtract(6, 'days'), moment()]
                 },
                 opens: 'left',
                 format: 'MM/DD/YYYY hh:mm:ss',
                 separator: ' to ',
                 locale: {
                    applyLabel: '确认',
                    cancelLabel: '关闭',
                    fromLabel: '从',
                    toLabel: '到',
                    customRangeLabel: '自定义',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                    firstDay: 1
                }
             };

             $(elem).daterangepicker(optionSet, cb);
        },
        controller: function($scope){

        }
    }
}]);

//搜索用-时间控件
angular.module("ng-admin").directive('sndateRangePicker', ["LazyLoader", 
    function(LazyLoader) {
        return {
            transclude: true,
            restrict: 'A',
            scope: {
                onChange: '&'
            },
            link: function(scope, elem, attrs) {
                var mask = angular.element('<i class="fa fa-calendar"></i><span>最近30分钟</span><b class="caret"></b>');
                elem.append(mask);

                var cb = function(start, end, label) {
                    var $timeHide;

                    //调回调
                    scope.onChange();
                };

                var optionSet = {
                    startDate: moment().subtract(29, 'minute'),
                    endDate: moment(),
                    maxDate: moment().hours(23).minutes(59).seconds(59),
//                    minDate: moment().subtract(6, 'days'),
                    timePicker: true,
                    timePicker12Hour: false,
                    timePickerIncrement:1,
                    timePickerSeconds:true,
                    opens: 'left',
                    format: 'YYYY-MM-DD HH:mm:ss',
                    separator: ' -- ',
                    locale: {
                        applyLabel: '确认',
                        cancelLabel: '关闭',
                        daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                        firstDay: 1
                    }
                };

                $(elem).daterangepicker(optionSet, cb);
            },
            controller: function($scope){

            }
        }
    }]);

//树控件
angular.module("ng-admin").directive('treeList', ['$timeout',function($timeout) {
     return {
        transclude: true,
        restrict: 'EA',
        scope: {
            onChange: '&',
            unit: '@',
            treeData: '=?'
        },
        templateUrl: 'treeTpl.html',
        link: function(scope, elem, attrs) {

            scope.$watch("treeData", function(value) {
                try {

                    var maxRange = scope.treeData[0].featureValue;
                    angular.forEach(scope.treeData, function(v){
                        var percent = v.featureValue/maxRange*100;
                        if(isNaN(percent)|| percent<0 ){
                            percent=0;
                        }
                        if(percent>100){
                            percent=100;
                        }
                        v.percent = percent;
                    });

                    scope.treeClick = function(item){
                        scope.selectedNode = item.dimensionName;
/*                        var selectNode = $.cookie('selectNode');
                         $.cookie('selectNode', scope.selectedNode);*/
                        scope.$parent.selectedNode = item;
                        $timeout(function() {
                            scope.onChange();
                        })
                    }

                } catch (ex) {
                    console.log(ex);
                }
            }, true);

        }
    }
}]);

//分页控件

angular.module("ng-admin").directive("snPager", function () {
        return {
            restrict: 'EA',
            scope: {
                currentPage: "=?",
                itemsPerPage: "=?",
                listSize: "=?",
                totalItems: "=?"
            },
            controller: function ($scope, pagerConfig) {
                $scope.pages = [];

                $scope.itemsPerPage = $scope.itemsPerPage || 10;
                $scope.listSize = $scope.listSize || 10;
                $scope.currentPage = $scope.currentPage || 0;

                $scope.totalPages = 1;
                $scope.offset = 0;

                $scope.$watch("totalItems", function () {
                    if ($scope.totalItems % $scope.itemsPerPage == 0) {
                        $scope.totalPages = $scope.totalItems / $scope.itemsPerPage;
                    } else {
                        $scope.totalPages = Math.ceil($scope.totalItems / $scope.itemsPerPage);
                    }

                    if ($scope.totalPages == 0) {
                        $scope.totalPages = 1;
                    }

                    $scope.offset = 0;
                    resetPageList();
                    $scope.currentPage = 0;
                });

                function getOffset(page) {
                    var offset = Math.min(page, $scope.totalPages - $scope.listSize);
                    if (offset < 0) {
                        offset = 0;
                    }

                    return offset;
                }

                function resetPageList() {
                    $scope.pages = [];

                    var last = Math.min($scope.offset + $scope.listSize, $scope.totalPages);
                    for (var i = $scope.offset; i < last; i++) {
                        $scope.pages.push(i);
                    }
                };

                $scope.getText = function (key) {
                    return pagerConfig.text[key];
                };

                $scope.isFirst = function () {
                    return $scope.currentPage <= 0;
                };

                $scope.isLast = function () {
                    return $scope.currentPage >= $scope.totalPages - 1;
                };

                $scope.selectPage = function (value) {
                    if (value == $scope.currentPage) {
                        return;
                    }

                    if ((value >= $scope.totalPages) || (value < 0)) {
                        return;
                    }

                    if ((value < $scope.offset) || (value >= $scope.offset + $scope.pages.length)) {
                        var offset = getOffset(value);
                        if (offset != $scope.offset) {
                            $scope.offset = offset;
                            resetPageList();
                        }
                    }

                    $scope.currentPage = value;

                    $scope.$emit("pager:pageIndexChanged", $scope.pages[$scope.currentPage - $scope.offset]);
                };

                $scope.first = function () {
                    if (this.isFirst()) {
                        return;
                    }
                    this.selectPage(0);
                };

                $scope.last = function () {
                    if (this.isLast()) {
                        return;
                    }
                    this.selectPage(this.totalPages - 1);
                };

                $scope.previous = function () {
                    if (this.isFirst()) {
                        return;
                    }
                    this.selectPage(this.currentPage - 1);
                };

                $scope.next = function () {
                    if (this.isLast()) {
                        return;
                    }
                    this.selectPage(this.currentPage + 1);
                };
            },
            templateUrl: "modules/common/pager.html"
        };
    }).constant('pagerConfig', {
        itemsPerPage: 10,
        text: {
            first: '首页',
            previous: '上一页',
            next: '下一页',
            last: '末页'
        }
    });

//检测渲染
angular.module('ng-admin').directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope,element,attr) {
            if (scope.$last === true) {
                var finishFunc=scope.$parent[attr.onFinishRenderFilters];
                if(finishFunc)
                {
                    finishFunc();
                }
            }
        }
    };
})

//系统tooltip指令
angular.module("ng-admin").directive('sysToolTip', [function() {
    var i = 0;
    return {
        restrict: 'A',
        scope: {
            key: "=?key",
            user: "=?user",
            ctime: "=?createTime"
        },
        link: function(scope, elem, attrs) {
            var key = "-",
                user = "-",
                time = "-";
            if(scope.key){key = scope.key;} 
            if(scope.user){user = scope.user;} 
            if(scope.ctime){time = scope.ctime;} 

            new jBox('Mouse',{
                id:'jBox_tool_sys_'+i,
                attach:$(elem),
                zIndex:8000,
                maxWidth:300,
                content:'<div class="tip-div"><span class="key">appKey:</span><span class="value">'+key
                    +'</span></div><div class="tip-div"><span class="key">创建用户:</span><span class="value">'+user
                    +'</span></div><div class="tip-div"><span class="key">创建时间:</span><span class="value">'+time
                    +'</span></div>'
            });

            i += 1;
        },
        controller: function($scope){
        }
    };
}]);