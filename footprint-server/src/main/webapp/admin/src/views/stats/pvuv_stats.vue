<style>
</style>

<template>
    <div class="page-content-wrapper">
        <div class="page-content" style="min-height:563px">
            <div class="page-content-wrap" style="min-height:533px">
                <div class="portlet-title page-content-title">
                    <div class="caption">
                        <i class="fa fa-th"></i> <span class="caption-subject uppercase">
							性能概况1111111111 </span>
                    </div>
                    <div class="actions">
                        <div class="btn-group"></div>
                    </div>
                </div>


                <div class="serach-select"></div>
                <div class="row">
                    <div class="col-md-6 column sortable">

                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="caption font-green-sharp">
                                    <i class="fa fa-question-circle tool-tip" data-tip-type="avgResponseTime"></i><span>平均响应时间</span>
                                </div>
                                <div class="actions">
                                    <a href="javascript:;"
                                       class="btn btn-circle btn-icon-only btn-default tool-tip dashboardBtn"
                                       data-module-name="AvgResponseTimeModule" data-tip-type="addDashboard"> <i
                                            class="fa fa-plus"></i>
                                    </a> <a class="btn btn-circle btn-icon-only btn-default fullscreen tool-tip"
                                            data-tip-type="fullScreen"> </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="charts" id="responseTime-chart-div" data-rail-visible="1"
                                     data-rail-color="yellow" data-handle-color="#a1b2bd"
                                     _echarts_instance_="1434523430149"
                                     style="-webkit-tap-highlight-color: transparent; -webkit-user-select: none; cursor: default;">
                                    <div style="position: relative; overflow: hidden; width: 483px; height: 300px;">
                                        <div data-zr-dom-id="bg"
                                             style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none;"></div>
                                        <canvas width="483" height="300" data-zr-dom-id="0"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                        <canvas width="483" height="300" data-zr-dom-id="_zrender_hover_"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="caption font-green-sharp">
                                    <i class="fa fa-question-circle tool-tip"
                                       data-tip-type="top5DomainResponseTime"></i><span>TOP5域名响应时间</span>
                                </div>
                                <div class="actions">
                                    <a href="javascript:;"
                                       class="btn btn-circle btn-icon-only btn-default tool-tip dashboardBtn"
                                       data-module-name="TopNAvgResponseTimeModule" data-dimension="reqDomain"
                                       data-tip-type="addDashboard"> <i class="fa fa-plus"></i>
                                    </a> <a class="btn btn-circle btn-icon-only btn-default fullscreen tool-tip"
                                            data-tip-type="fullScreen"> </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="charts" id="topNDomain-chart-div" data-rail-visible="1"
                                     data-rail-color="yellow" data-handle-color="#a1b2bd"
                                     _echarts_instance_="1434523430148"
                                     style="-webkit-tap-highlight-color: transparent; -webkit-user-select: none;">
                                    <div style="position: relative; overflow: hidden; width: 483px; height: 300px;">
                                        <div data-zr-dom-id="bg"
                                             style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none;"></div>
                                        <canvas width="483" height="300" data-zr-dom-id="0"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                        <canvas width="483" height="300" data-zr-dom-id="_zrender_hover_"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="caption font-green-sharp">
                                    <i class="fa fa-question-circle tool-tip"
                                       data-tip-type="httpResponseMaps"></i><span>HTTP响应地图</span>
                                </div>
                                <div class="actions">
                                    <a href="javascript:;"
                                       class="btn btn-circle btn-icon-only btn-default tool-tip dashboardBtn"
                                       data-module-name="MapModule" data-dimension="province"
                                       data-tip-type="addDashboard"> <i class="fa fa-plus"></i>
                                    </a> <a class="btn btn-circle btn-icon-only btn-default fullscreen tool-tip"
                                            data-tip-type="fullScreen"> </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="charts" id="map-chart-div" data-rail-visible="1" data-rail-color="yellow"
                                     data-handle-color="#a1b2bd" _echarts_instance_="1434523430151"
                                     style="-webkit-tap-highlight-color: transparent; -webkit-user-select: none; background-color: rgba(0, 0, 0, 0);">
                                    <div style="position: relative; overflow: hidden; width: 483px; height: 300px;">
                                        <div data-zr-dom-id="bg"
                                             style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none;"></div>
                                        <canvas width="483" height="300" data-zr-dom-id="0"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                        <canvas width="483" height="300" data-zr-dom-id="1"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                        <canvas width="483" height="300" data-zr-dom-id="_zrender_hover_"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-6">

                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="caption font-green-sharp">
                                    <i class="fa fa-question-circle tool-tip" data-tip-type="httpAndNetError"></i><span>HTTP错误率/网络错误率</span>
                                </div>
                                <div class="actions">
                                    <a href="javascript:;"
                                       class="btn btn-circle btn-icon-only btn-default tool-tip dashboardBtn"
                                       data-module-name="AvgErrorModule" data-error-type="all"
                                       data-tip-type="addDashboard"> <i class="fa fa-plus"></i>
                                    </a> <a class="btn btn-circle btn-icon-only btn-default fullscreen tool-tip"
                                            data-tip-type="fullScreen"> </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="charts" id="error-chart-div" data-rail-visible="1" data-rail-color="yellow"
                                     data-handle-color="#a1b2bd" _echarts_instance_="1434523430147"
                                     style="-webkit-tap-highlight-color: transparent; -webkit-user-select: none; cursor: default;">
                                    <div style="position: relative; overflow: hidden; width: 483px; height: 300px;">
                                        <div data-zr-dom-id="bg"
                                             style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none;"></div>
                                        <canvas width="483" height="300" data-zr-dom-id="0"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                        <canvas width="483" height="300" data-zr-dom-id="_zrender_hover_"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="caption font-green-sharp">
                                    <i class="fa fa-question-circle tool-tip" data-tip-type="numberOfActiveDevices"></i><span>活跃设备数</span>
                                </div>
                                <div class="actions">
                                    <a href="javascript:;"
                                       class="btn btn-circle btn-icon-only btn-default tool-tip dashboardBtn"
                                       data-module-name="ActiveDeviceModule" data-dimension="deviceType"
                                       data-tip-type="addDashboard">
                                        <i class="fa fa-plus"></i>
                                    </a> <a class="btn btn-circle btn-icon-only btn-default fullscreen tool-tip"
                                            data-tip-type="fullScreen"> </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="charts" id="activeDevice-chart-div" data-rail-visible="1"
                                     data-rail-color="yellow" data-handle-color="#a1b2bd"
                                     _echarts_instance_="1434523430150"
                                     style="-webkit-tap-highlight-color: transparent; -webkit-user-select: none;">
                                    <div style="position: relative; overflow: hidden; width: 483px; height: 300px;">
                                        <div data-zr-dom-id="bg"
                                             style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none;"></div>
                                        <canvas width="483" height="300" data-zr-dom-id="0"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                        <canvas width="483" height="300" data-zr-dom-id="_zrender_hover_"
                                                style="position: absolute; left: 0px; top: 0px; width: 483px; height: 300px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="caption font-green-sharp">
                                    <i class="fa fa-question-circle tool-tip"
                                       data-tip="最近事件（最近1小时）"></i><span>最近事件</span>
                                </div>
                                <div class="actions">
                                    <a class="btn btn-circle btn-icon-only btn-default fullscreen tool-tip"
                                       data-tip-type="fullScreen"> </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="tabbable-custom" style="height: 280px; width: 100%;">
                                    <ul class="nav nav-tabs ">
                                        <li class="active"><a
                                                href="http://cloudytrace.cnsuning.com/ctmam-web/mobile/appAnalysis/overview.htm#tab_1"
                                                data-toggle="tab" aria-expanded="false"> 所有 </a></li>
                                        <li class=""><a
                                                href="http://cloudytrace.cnsuning.com/ctmam-web/mobile/appAnalysis/overview.htm#tab_2"
                                                data-toggle="tab" aria-expanded="false"> 警告 </a></li>
                                        <li class=""><a
                                                href="http://cloudytrace.cnsuning.com/ctmam-web/mobile/appAnalysis/overview.htm#tab_3"
                                                data-toggle="tab" aria-expanded="true"> 严重问题 </a></li>
                                        <li class=""><a
                                                href="http://cloudytrace.cnsuning.com/ctmam-web/mobile/appAnalysis/overview.htm#tab_4"
                                                data-toggle="tab" aria-expanded="true"> 配置更改 </a></li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="tab_1">
                                            <div class="slimScrollDiv"
                                                 style="position: relative; overflow: hidden; width: auto; height: 290px;">
                                                <div class="scroller"
                                                     style="height: 290px; overflow: hidden; width: auto;"
                                                     data-always-visible="1" data-rail-visible="0" data-initialized="1">
                                                    <ul class="feeds" id="tab_1_content"><p>暂无事件！</p></ul>
                                                </div>
                                                <div class="slimScrollBar"
                                                     style="background-color: rgb(187, 187, 187); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; height: 290px; background-position: initial initial; background-repeat: initial initial;"></div>
                                                <div class="slimScrollRail"
                                                     style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(234, 234, 234); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="tab_2">
                                            <div class="slimScrollDiv"
                                                 style="position: relative; overflow: hidden; width: auto; height: 290px;">
                                                <div class="scroller"
                                                     style="height: 290px; overflow: hidden; width: auto;"
                                                     data-always-visible="1" data-rail-visible="0" data-initialized="1">
                                                    <ul class="feeds" id="tab_2_content"><p>暂无事件！</p></ul>
                                                </div>
                                                <div class="slimScrollBar"
                                                     style="background-color: rgb(187, 187, 187); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                                <div class="slimScrollRail"
                                                     style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(234, 234, 234); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="tab_3">
                                            <div class="slimScrollDiv"
                                                 style="position: relative; overflow: hidden; width: auto; height: 290px;">
                                                <div class="scroller"
                                                     style="height: 290px; overflow: hidden; width: auto;"
                                                     data-always-visible="1" data-rail-visible="0" data-initialized="1">
                                                    <ul class="feeds" id="tab_3_content"><p>暂无事件！</p></ul>
                                                </div>
                                                <div class="slimScrollBar"
                                                     style="background-color: rgb(187, 187, 187); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                                <div class="slimScrollRail"
                                                     style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(234, 234, 234); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="tab_4">
                                            <div class="slimScrollDiv"
                                                 style="position: relative; overflow: hidden; width: auto; height: 290px;">
                                                <div class="scroller"
                                                     style="height: 290px; overflow: hidden; width: auto;"
                                                     data-always-visible="1" data-rail-visible="0" data-initialized="1">
                                                    <ul class="feeds" id="tab_4_content"><p>暂无事件！</p></ul>
                                                </div>
                                                <div class="slimScrollBar"
                                                     style="background-color: rgb(187, 187, 187); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                                <div class="slimScrollRail"
                                                     style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(234, 234, 234); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    module.exports = {
        data: function(){
            return {
                records : [
                    {id:"11073",title:"Beats入耳式耳机",supplier:"苹果促销",pic:"http://static.diditaxi.com.cn/op/imall_cms_image/20150910/20150910180605list.jpg",per_count:"6000",isHave:true},
                    {id:"12073",title:"AppleIpadMini",supplier:"苹果促销",pic:"http://static.diditaxi.com.cn/op/imall_cms_image/20150821/20150821103302list.jpg",per_count:"2800",isHave:false},
                    {id:"13073",title:"Beats入耳式耳机",supplier:"苹果促销",pic:"http://static.diditaxi.com.cn/op/imall_cms_image/20150910/20150910180605list.jpg",per_count:"6000",isHave:true},
                    {id:"14073",title:"AppleIpadMini",supplier:"苹果促销",pic:"http://static.diditaxi.com.cn/op/imall_cms_image/20150821/20150821103302list.jpg",per_count:"2800",isHave:false}
                ]
            }
        },
        ready: function () {
            console.log(this) // -> 'foo'
        },
        methods:{
            _showDetail:function(gift_id){
                console.info(gift_id);
            }
        }
    }
</script>
