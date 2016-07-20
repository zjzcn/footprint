angular.module("sn.controls", []);

angular.module("sn.controls").service("EventBus", [function () {
    var eventMap = {};

    return {
        on: function (eventType, handler) {
            //multiple event listener
            if (!eventMap[eventType]) {
                eventMap[eventType] = [];
            }
            eventMap[eventType].push(handler);
        },

        off: function (eventType, handler) {
            for (var i = 0; i < eventMap[eventType].length; i++) {
                if (eventMap[eventType][i] === handler) {
                    eventMap[eventType].splice(i, 1);
                    break;
                }
            }
        },

        fire: function (event) {
            var eventType = event.type;
            if (eventMap[eventType]) {
                for (var i = 0; i < eventMap[eventType].length; i++) {
                    eventMap[eventType][i](event);
                }
            }
        }
    };
}]);

angular.module("sn.controls").service("HttpService", ["$http", "$q", "$document", "$window", "AlertService", "EventBus", "baseUrl",
    function ($http, $q, $document, $window, AlertService, EventBus, baseUrl) {
        function busy() {
            document.body.style.cssText = "cursor: progress !important";
        }

        function idle() {
            document.body.style.cssText = "";
        }

        function sendRequest(url, params, method) {
            var defer = $q.defer();
            busy();

            $http[method](url, params).success(function (result, status) {
                idle();
                if (result["success"] == "302") {
                    var url = result.result["redirect_url"];
                    $window.location.href = url;
                }

                defer.resolve(result);
            }).error(function (reason, status) {
                    idle();

                    var errorContent = reason;
                    if (reason != undefined && reason.errorresponse != undefined) {
                        errorContent = reason.errorresponse.errortext;
                    }
                    AlertService.alert({
                        title: "服务端异常",
                        content: errorContent
                    });

                    defer.reject(reason);
                });

            return defer.promise;
        }

        return {
            "get": function (url, param, option) {
                var defer = $q.defer();

                sendRequest(baseUrl + url, {params:param}, "get").then(
                    function(result) {
                        defer.resolve(result);
                    }
                );

                return defer.promise;
            },
            "post": function (url, param, option) {
                var defer = $q.defer();

                sendRequest(baseUrl + url, param, "post").then(
                    function(result) {
                        defer.resolve(result);
                    }
                );
                return defer.promise;
            }
        };
    }]);

angular.module("sn.controls").service("DialogService", ["$http", "$document", "$rootScope", "$compile", function ($http, $document, $rootScope, $compile) {
    var zIndex = 1050;
    var dialogCounter = 0;

    var dialogMap = {};
    return {
        modal: function (param, data) {
            $http.get(param.url).then(function (result) {
                dialogCounter += 2;

                var mask = angular.element('<div class="modal-backdrop fade in"></div>');
                $document.find("body").append(mask);
                mask.css("z-index", zIndex + dialogCounter);

                var dialog = angular.element(result.data);
                var newScope = $rootScope.$new();
                if (data) {
                    angular.extend(newScope, data);
                }
                var element = $compile(dialog)(newScope);

                $document.find("body").append(element);
                element.css("display", "block");
                element.css("z-index", zIndex + dialogCounter + 1);

                dialogMap[param.key] = param;
                dialogMap[param.key].dialog = element;
                dialogMap[param.key].mask = mask;
            });
        },

        accept: function (key, result) {
            this.dismiss(key);

            if (dialogMap[key].accept) {
                dialogMap[key].accept(result);
            }
        },

        refuse: function (key, reason) {
            this.dismiss(key);

            if (dialogMap[key].refuse) {
                dialogMap[key].refuse(reason);
            }
        },

        dismiss: function (key) {
            dialogMap[key].mask.remove();
            dialogMap[key].dialog.remove();
//            delete dialogMap[key];
        },

        dismissAll: function() {
            for (var key in dialogMap) {
                this.dismiss(key);
            }
        },

        postMessage: function (key, type, message) {
            if (dialogMap[key].messageHandler) {
                if (dialogMap[key].messageHandler[type]) {
                    dialogMap[key].messageHandler[type](message);
                }
            }
        }
    };
}]);

angular.module("sn.controls").service("AlertService", ["$http", "$document", "$q", "$rootScope", "$compile", function ($http, $document, $q, $rootScope, $compile) {
    var zIndex = 1200;
    var dialogCounter = 0;

    var dialogArr = [];

    var mask = angular.element('<div class="modal-backdrop fade in"></div>');
    mask.css("z-index", zIndex);

    function getTemplate() {
        var dialogTpl;
        var defer = $q.defer();
        if (dialogTpl) {
            defer.resolve(dialogTpl);
        } else {
            /*
             $http.get("templates/alert/alert.html").then(function (result) {
             dialogTpl = result.data;
             defer.resolve(dialogTpl);
             });*/

            dialogTpl = document.getElementById("alertTpl").innerHTML;
            defer.resolve(dialogTpl);
        }
        return defer.promise;
    }

    var service = {
        alert: function (param) {
            var defer = $q.defer();

            getTemplate().then(function (dialogTpl) {
                var dialog;
                dialogCounter++;

                if (dialogCounter == 1) {
                    $document.find("body").append(mask);
                }

                var data = $rootScope.$new();
                angular.extend(data, param);

                data.ok = function () {
                    service.dismiss(dialog);
                    defer.resolve("ok");
                };
                data.close = function () {
                    service.dismiss(dialog);
                    defer.resolve("ok");
                };

                dialog = $compile(angular.element(dialogTpl))(data);

                $document.find("body").append(dialog);
                dialog.css("display", "block");
                dialog.css("z-index", zIndex + dialogCounter);

                dialogArr.push(dialog);
            });

            return defer.promise;
        },
        confirm: function (param) {
            var defer = $q.defer();

            getTemplate().then(function (dialogTpl) {
                var dialog;
                dialogCounter++;

                if (dialogCounter == 1) {
                    $document.find("body").append(mask);
                }

                var data = $rootScope.$new();
                angular.extend(data, param);

                data.ok = function () {
                    service.dismiss(dialog);
                    defer.resolve("ok");
                };
                data.cancel = function () {
                    service.dismiss(dialog);
                    defer.reject("cancel");
                };
                data.close = function () {
                    service.dismiss(dialog);
                    defer.reject("cancel");
                };

                dialog = $compile(dialogTpl)(data);

                $document.find("body").append(dialog);
                dialog.css("display", "block");
                dialog.css("z-index", zIndex + dialogCounter);

                dialogArr.push(dialog);
            });

            return defer.promise;
        },
        dismiss: function (dialog) {
            dialogCounter--;
            dialog.remove();

            if (dialogCounter == 0) {
                mask.remove();
            }

            for (var i=0; i<dialogArr.length; i++) {
                if (dialogArr[i] == dialog) {
                    dialogArr.splice(i, 1);
                    break;
                }
            }
        },
        dismissAll: function() {
            while (dialogArr.length>0) {
                this.dismiss(dialogArr[0]);
            }
        }
    };

    return service;
}]);

angular.module("sn.controls").directive("echarts", ["LazyLoader","echartsId",
 function (LazyLoader, echartsId) {
    return {
        restrict: "A",
        scope: {
            echarts: "="
        },
        link: function (scope, element, attrs) {

            LazyLoader.load("js/lib/echarts-all.js")
                .then(function () {
                    var myChart = echarts.init(element[0]);
                    myChart.setTheme("macarons");

                    //modify by her echarts的全屏事件
                    var chartsId = "echarts_" + echartsId;
                    element[0].setAttribute("id", chartsId);
                    echartsArr[chartsId] = myChart;
                    echartsId += 1;

                    scope.$watch("echarts", function(value) {
                        try {
                            myChart.setOption(scope.echarts, true);
                        } catch (ex) {
                            console.log(ex);
                        }
                    }, true);
                });
        }
    };
}]);

angular.module("sn.controls").service("LazyLoader", ["$q", function ($q) {
    var createdScripts = {}; //是否已创建script标签
    var pendingScripts = {}; //哪些处于加载过程中

    var loader = {
        load: function (url) {
            var deferred = $q.defer();

            if (!createdScripts[url]) {
                var script = document.createElement('script');
                script.src = encodeURI(url);

                script.onload = function () {
                    if (pendingScripts[url]) {
                        for (var i = 0; i < pendingScripts[url].length; i++) {
                            pendingScripts[url][i].deferred && pendingScripts[url][i].deferred.resolve();
                            pendingScripts[url][i].callback && pendingScripts[url][i].callback();
                        }
                        delete pendingScripts[url];
                    }
                };

                createdScripts[url] = script;
                document.body.appendChild(script);

                if (!pendingScripts[url]) {
                    pendingScripts[url] = [];
                }
                pendingScripts[url].push({
                    deferred: deferred
                });
            } else if (pendingScripts[url]) {
                pendingScripts[url].push({
                    deferred: deferred
                });
            } else {
                deferred.resolve();
            }

            return deferred.promise;
        },
        loadArr: function (arr) {
            var deferred = $q.defer();
            var counter = 0;

            function checkAll() {
                if (counter == arr.length) {
                    deferred.resolve();
                }
            }

            for (var j = 0; j < arr.length; j++) {
                var url = arr[j];
                if (!createdScripts[url]) {
                    var script = document.createElement('script');
                    script.src = encodeURI(url);

                    script.onload = function () {
                        //这段是唯一需要关注pendingScripts的，因为你是顺带帮别人加载了代码，对你自己并无本质帮助
                        if (pendingScripts[url]) {
                            for (var i = 0; i < pendingScripts[url].length; i++) {
                                pendingScripts[url][i].deferred && pendingScripts[url][i].deferred.resolve();
                                pendingScripts[url][i].callback && pendingScripts[url][i].callback();
                            }
                            delete pendingScripts[url];
                        }

                        counter++;
                        checkAll();
                    };

                    createdScripts[url] = script;
                    document.body.appendChild(script);

                    if (!pendingScripts[url]) {
                        pendingScripts[url] = [];
                    }
                    pendingScripts[url].push({
                        callback: checkAll
                    });
                } else if (pendingScripts[url]) {
                    //这里很麻烦啊，要是你想加载的js被别人顺带加载了，怎么办？
                    pendingScripts[url].push({
                        callback: checkAll
                    });
                } else {
                    checkAll();
                }
            }

            return deferred.promise;
        },
        loadQueue: function (arr) {
            var deferred = $q.defer();

            loadStep(0);

            function loadStep(index) {
                if (index == arr.length) {
                    deferred.resolve();
                } else {
                    loader.load(arr[index]).then(function () {
                        loadStep(index + 1);
                    });
                }
            }

            return deferred.promise;
        }
    };

    return loader;
}]);