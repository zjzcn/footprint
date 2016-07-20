'use strict';

 angular.module("ng-admin").controller('DetailController', function($scope) {

     $("#selectApp").chosen({
        no_results_text: "未查询到结果",
        search_contains: true,
        disable_search_threshold: 10,
        width: "200px"
     });

     var cb = function(start, end, label) {
         var $timeHide;
         if( label == optionSet.locale.customRangeLabel){
             var timeRange = start.format('YYYY-MM-DD HH:mm:ss') + ' -- ' + end.format('YYYY-MM-DD HH:mm:ss');
             $('#defaultrange span').html(timeRange);
         }else{
             $('#defaultrange span').html(label);
         }
         //var obj = getRefreshValue();
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
     if($('#defaultrange').length > 0){
         $('#defaultrange').daterangepicker(optionSet, cb);
     }

 });
