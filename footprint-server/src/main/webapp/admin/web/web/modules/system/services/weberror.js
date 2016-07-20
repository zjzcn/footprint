angular.module("ng-admin").service("WebErrorService", ["HttpService", function(HttpService) {
    
    return {
        "getWebErrorList": function(params) {
            //return HttpService.get("", params);
		    var data = {};
			data["data"] = [];
            for (var i=0; i<11; i++) {
	            data["data"].push({
	            	"url":"http://www.suning.com/index.html",
	            	"time":"09-08 15:04:21",
	            	"code":"404",
	            	"info":i + "---Not Found",
	            	"authorWeb": "192.168.1.1:apache01:80",
	            	"authorApp": "192.168.0.1:server01:8080",
	            	"detailInfo": i+"---这条错误消息的详细内容"
	            });
	        }
            return data;
        }
    }
}]);