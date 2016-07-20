angular.module("ng-admin").controller("TopologyCtrl", 
	["LazyLoader", "$scope", "DialogService",
	function(LazyLoader, $scope, DialogService) {

    var vm = $scope;

	LazyLoader.loadQueue(["js/lib/dom.jsPlumb-1.7.5-min.js","js/lib/echarts-all.js"])
        .then(function () {

		    $(function(){
		    	setHeight();
		    	jsPlumbInit();

				initTooltip();
				initCicle();
		    	
		    });
		    
        });

    function initTooltip(){
    	if($(".window-p").length > 0){
    		$(".window-p").each(function(index, el) {
    			new jBox('Tooltip',{
	                id:'jBox_tool_topology_'+index,
	                attach:$(this),
	                zIndex:8000,
	                maxWidth:300,
	                closeOnMouseleave:true,
	                position:{x:'right',y:'center'},
	                outside: 'x',
	                animation:'zoomIn',
	                content:'<div class="tip-div"><span class="key">吞吐量:</span><span class="value">'+$(this).attr("data-rpm")+"rpm"
	                +'</span></div><div class="tip-div"><span class="key">响应时间:</span><span class="value">'+$(this).attr("data-rt")+"ms"
	                +'</span></div><div class="tip-div"><span class="key">错误率:</span><span class="value">'+$(this).attr("data-err")+"%"
				});
    		});
    	}
    };

    function initCicle(){
    	$(".topology-circle").each(function(){
    		var $dom = $(this);
    		var myChart = echarts.init($dom[0]);
		    var option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{b}:{c}个",
			        position: [145,50]
			    },
		        legend: {
		            show: false,
		            data:['正常节点','警告节点','严重节点','无数据节点']
		        },
		        color: ["#2EC7C9","#FFB980","#D87A80","#EEEEEE"],
		        series : [
		            {
		                name:'节点情况',
		                type:'pie',
		                radius : ['65%', '90%'],
		                itemStyle : {
		                    normal : {
		                        label : {
		                            show : false
		                        },
		                        labelLine : {
		                            show : false
		                        }
		                    },
		                    emphasis : {
		                        label : {
		                            show : false,
		                            position : 'center',
		                            textStyle : {
		                                fontSize : '12',
		                                fontWeight : 'bold'
		                            }
		                        }
		                    }
		                },
		                data:[
		                    {value:6, name:'正常节点'},
		                    {value:2, name:'警告节点'},
		                    {value:2, name:'严重节点'},
		                    {value:1, name:'无数据节点'}
		                ]
		            }
		        ]
		    };
		    myChart.setOption(option);

		    myChart.on("click", function (param){

				var params = {};
				params.title = $dom.parents(".window").eq(0).find("p.title").text();
				
				params.title += param.name;
				DialogService.modal({
	                key:"Baymax.System.TopologyDialog",
	                url:"modules/system/templates/topology/topodlg.html",
	                accept:function(data){
	                }
	            }, params);
		    });
    	})
    }

}]);


var setHeight = function(){
    var _length = $("#flowchart-demo").find(".window").length-2;    
    $("#flowchart-demo").height(_length * 140); 
    $("#flowchartWindow0").css("top",(_length * 130-220)/2);
    $("#flowchartWindow1").css("top",(_length * 130-220)/2);
    if($("#flowchart-demo").find(".window").length > 0 ){
        $("#flowchart-demo").find(".window").each(function(index){
            if(index > 1){
                $(this).css("top", 130*(index-2));
            }
        })
    }
}

var jsPlumbInit = function () {

	jsPlumb.ready(function () {
	    var instance = jsPlumb.getInstance({
	        DragOptions: { cursor: 'pointer', zIndex: 2000 },
	        ConnectionOverlays: [
	            [ "Arrow", { width:8, height:2,location:1}],
	            [ "Label", {
	                location: 0.1,
	                id: "label",
	                cssClass: "aLabel"
	            }]
	        ],
	        Container: "flowchart-demo"
	    });

	    var basicType = {
	        connector: "StateMachine",
	        paintStyle: { strokeStyle: "red", lineWidth: 1 },
	        hoverPaintStyle: { strokeStyle: "blue" },
	        overlays: [ "Arrow"]
	    };

	    instance.registerConnectionType("basic", basicType);

	    var connectorPaintStyle = {
	            lineWidth: 1,
	            strokeStyle: "#B1B1B1",
	            joinstyle: "round",
	            outlineColor: "white",
	            outlineWidth: 1
	        },

	        connectorHoverStyle = {
	            lineWidth: 1,
	            strokeStyle: "#61B7CF",
	            outlineWidth: 1,
	            outlineColor: "white"
	        },
	        endpointHoverStyle = {
	            fillStyle: "#B1B1B1",
	            strokeStyle: "#B1B1B1"
	        },

	        sourceEndpoint = {
	            endpoint: "Dot",
	            paintStyle: {
	                strokeStyle: "transparent",
	                fillStyle: "transparent",
	                radius: 2,
	                lineWidth: 1
	            },
	            isSource: true,
	            connector: [ "Straight", { stub: [0, 40], gap: 0, cornerRadius: 5, alwaysRespectStubs: true } ],
	            connectorStyle: connectorPaintStyle,
	            hoverPaintStyle: endpointHoverStyle,
	            connectorHoverStyle: connectorHoverStyle,
	            dragOptions: {},
	            overlays: [
	                [ "Label", {
	                    location: [0.5, 1.5],
	                    cssClass: "endpointSourceLabel"
	                } ]
	            ]
	        },
	        targetEndpoint = {
	            endpoint: "Dot",
	            paintStyle: { fillStyle: "transparent", radius: 5 },
	            hoverPaintStyle: endpointHoverStyle,
	            maxConnections: -1,
	            dropOptions: { hoverClass: "hover", activeClass: "active" },
	            isTarget: true,
	            overlays: [
	                [ "Label", { location: [0.5, -0.5], cssClass: "endpointTargetLabel" } ]
	            ]
	        },
	        
	        init = function (connection) {
	            
	        };

	        var _addEndpoints = function (toId, sourceAnchors, targetAnchors) {
	        for (var i = 0; i < sourceAnchors.length; i++) {
	            var sourceUUID = "flowchart" + toId + sourceAnchors[i];
	            instance.addEndpoint("flowchart" + toId, sourceEndpoint, {
	                uuid: sourceUUID,
	                anchor: sourceAnchors[i], 
	                maxConnections: -1
	            });
	        }
	        for (var j = 0; j < targetAnchors.length; j++) {
	            var targetUUID = "flowchart" + toId + targetAnchors[j];
	            instance.addEndpoint("flowchart" + toId, targetEndpoint, { 
	                uuid: targetUUID, 
	                anchor: targetAnchors[j], 
	                maxConnections: -1 
	            });
	        }
	    };

	    instance.batch(function () {

	    	$("#flowchart-demo").find(".window").each(function(index){
	    		if(index > 1){
	    			var i = index;    			
	    	        _addEndpoints("Window" + i, [], ["LeftMiddle"]);
	    		}else if(index > 0){
	    	        _addEndpoints("Window1", ["RightMiddle"], ["LeftMiddle"]);
	    		}else{
	    	        _addEndpoints("Window0", ["RightMiddle"], []);
	    		}
	    	})

	        instance.bind("connection", function (connInfo, originalEvent) {
	            init(connInfo.connection);
	        });

	        instance.draggable(jsPlumb.getSelector(".flowchart-demo .window"), { grid: [20, 20] });

			instance.connect({uuids: ["flowchartWindow0RightMiddle", "flowchartWindow1LeftMiddle"], editable: false});
	        instance.connect({uuids: ["flowchartWindow1RightMiddle", "flowchartWindow2LeftMiddle"], editable: false});
	        instance.connect({uuids: ["flowchartWindow1RightMiddle", "flowchartWindow3LeftMiddle"], editable: false});
	        instance.connect({uuids: ["flowchartWindow1RightMiddle", "flowchartWindow4LeftMiddle"], editable: false});
	        instance.connect({uuids: ["flowchartWindow1RightMiddle", "flowchartWindow5LeftMiddle"], editable: false});

	    });

	    jsPlumb.fire("jsPlumbDemoLoaded", instance);
	})
};

angular.module("ng-admin").controller("TopologyDialogCtrl", ["$scope", "DialogService", "OverviewInfoService",
 function($scope, DialogService, OverviewInfoService) {

    var vm = $scope;

    vm.close = function() {
        DialogService.dismiss("Baymax.System.TopologyDialog");
    };
     vm.agentList = [];
    vm.agentList = [{"agentId":5,"errorRate":0.0,"agentName":"java_jvm_10.10.10.10_server4","rpm":0.0,"responseTime":0.0},{"agentId":6,"errorRate":0.0,"agentName":"java_apache_10.10.10.10_server5","rpm":0.0,"responseTime":0.0},{"agentId":10,"errorRate":0.0,"agentName":"java_apache_192,168.1.1_server7","rpm":0.0,"responseTime":0.0}];

    vm.option1 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['响应时间'],
            y: 'bottom'
        },
        toolbox: {
            show : false
        },
		grid : {
			x : 60,
			y : 20,
			x2 : 20,
			y2 : 60
		},
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
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
        series : [
            {
                name:'响应时间',
                type:'line',
                smooth:true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data:[12, 12.5, 1.2, 1.26, 7.5, 1.00,1.35, 1.11]
            }
        ]
    };
    vm.option2 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['update111'],
            y: 'bottom'
        },
		grid : {
			x : 60,
			y : 20,
			x2 : 20,
			y2 : 60
		},
        toolbox: {
            show : false
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} cpm '
                }
            }
        ],
        series : [
            {
                name:'update111',
                type:'line',
                data:[1.2,2.3, 1.6, 3.5, 7.5, 5.3, 5.8, 1.6]
            }
        ]
    };
    vm.option3 = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['update111'],
            y: 'bottom'
        },
		grid : {
			x : 60,
			y : 20,
			x2 : 20,
			y2 : 60
		},
        toolbox: {
            show : false
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['18:10','18:15','18:20','18:25','18:30','18:35','18:40','18:45']
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} cpm '
                }
            }
        ],
        series : [
            {
                name:'update111',
                type:'line',
                data:[1.2,2.3, 1.6, 3.5, 7.5, 5.3, 5.8, 1.6]
            }
        ]
    };

    vm.echarts = [{
        name: "程响应时间",
        tip: "在图表横坐标粒时间度下的总耗时时间/图表横坐标粒度时间",
        data: vm.option1
    },{
        name: "吞吐率",
        tip: "当前系统的每分钟请求数",
        data: vm.option2
    },{
        name: "错误率",
        tip: "当前系统的每分钟错误率",
        data: vm.option3
    }];

}]);