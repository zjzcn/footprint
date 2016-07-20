<style>
    body {
        background-color: #efeff4;
        -webkit-animation: fadeIn .5s ease both;
        animation: fadeIn .5s ease both;
    }
    .aui-content {
        padding: 10px;
        overflow: hidden;
    }
    #header {
        background-color: #1abc9c;
    }
    .aui-content {
        padding-top: 10px;
        padding-bottom: 0px;
    }
    .aui-content > .aui-list-view:last-child {
        margin-bottom: 0;
    }


    .spread-my {
        margin-top: 10px;
        background: #ffffff;
        overflow: hidden;
        color: #999999;
        position: relative;
        padding: 15px;
    }
    .spread-my .aui-col-xs-8 {

    }
    .spread-my .spread-nums {
        color: #8f8f94;
        font-size: 14px;
    }
    .spread-my .spread-nums p {
        color: #8f8f94;
    }
    .spread-my strong {
        color: #e74c3c;
        font-size: 18px;
        font-weight: 700;
        line-height: 26px;
        padding-left: 5px;
    }
    .spread-my .aui-icon-people3 {
        font-size: 20px;
        line-height: 26px;
        color: #8f8f94;
    }
    .circle-my .aui-pull-right {
        color: #8f8f94;
        font-size: 14px;
    }
    .spread-my .aui-icon-right{
        font-size: 18px;
        line-height: 26px;
        color: #8f8f94;
        padding-left: 5px;
    }
    .spread-my .spread-avatar {
        width: 26px;
        height: 26px;
        float: left;
        margin-bottom: 2px;
        margin-right: 2px;
    }
    .spread-my .spread-avatar img {
        width: 100%;
        height: 100%;
        border-radius: 2px;
    }
    /*我的会员中心*/
    .my-header {
        width: 100%;
        height: 200px;
        position: relative;
        background: #1abc9c;
        /*background: url('../image/mybg.jpg') no-repeat;
        background-position: center;
        background-size: cover;*/
    }
    .my-header .mask {
        width: 100%;
        height: 200px;
        position: absolute;
        left: 0;
        top: 0;
        background: #000000;
        background:rgba(0, 0, 0, 0.7);
        z-index: 1;
    }
    .my-header .my-today-manager {
        width: 100%;
        height: 120px;
        padding: 30px 10px 10px;
        color: #ffffff;
        font-size: 14px;
        position: relative;
        z-index: 2;
    }
    .my-header .my-today-manager p {
        color: #ffffff;
    }
    .my-header .my-today-manager .aui-pull-right {
        padding-top: 10px;
    }
    .my-header .my-sign .aui-iconfont {
        font-size: 36px;
    }
    .my-header .my-sign p {
        padding-top: 5px;
    }
    .my-header .my-resttime {
        width: 100%;
        padding: 10px 10px 0 10px;
        height: 35px;
        line-height: 35px;
        color: #ffffff;
        font-size: 14px;
        position: relative;
        z-index: 2;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .my-manager-btn {
        padding-top: 20px;
        padding-bottom: 20px;
        background: #ffffff;
    }
    .my-manager-btn .aui-iconfont{
        padding: 10px 8px;
        margin-bottom: 5px;
        font-size: 26px;
        display: inline-block;
        border-radius: 3px;
        color: #ffffff;
    }
    .my-manager-btn p {
        font-size: 12px;
        margin-bottom: 0;
    }
    .my-manager-list {
        padding-left: 0;
        padding-right: 0;
    }
    .my-manager-list .aui-img-object {
        width: 32px;
        height: 32px;
        line-height: 32px;
        text-align: center;
    }
    .my-manager-list .aui-img-object.aui-iconfont {
        font-size: 20px;
    }
    .my-manager-list .aui-img-body {
        line-height: 32px;
        font-size: 16px;
        color: #666666;
    }
    #nickname.aui-text-danger{
        color: #e74c3c;
    }
    .vipicon {
        font-size: 14px;
        color: #ff9900;
        line-height: 35px;
        margin: 0;
        margin-right: 20px;
        padding: 0;
    }
    .vipicon img {
        width: 25px;
        height: 15px;
    }
    #vip {
        display: none;
    }
    /*筛选背景颜色类*/
    .bg-normal {
        background-color: #1ABC9C;
    }
    .bg-more {
        background-color: #BDC3C7;
    }
    .bg-white {
        background-color: #ECF0F1;
    }
    .bg-1 {
        background-color: #1ABC9C;
    }
    .bg-2 {
        background-color: #3498DB;
    }
    .bg-3 {
        background-color: #34495E;
    }
    .bg-4 {
        background-color: #F39C12;
    }
    .bg-5 {
        background-color: #E67E22;
    }
    .bg-6 {
        background-color: #E74C3C;
    }
    .bg-7 {
        background-color: #F1C40F;
    }
    .bg-8 {
        background-color: #D35400;
    }
    .bg-9 {
        background-color: #F1C40F;
    }
    .bg-10 {
        background-color: #9B59B6;
    }

    .text-1, .aui-iconfont.text-1 {
        color: #1ABC9C;
    }
    .text-2, .aui-iconfont.text-2 {
        color: #3498DB;
    }
    .text-3, .aui-iconfont.text-3 {
        color: #34495E;
    }
    .text-4, .aui-iconfont.text-4 {
        color: #F39C12;
    }
    .text-5, .aui-iconfont.text-5 {
        color: #E67E22;
    }
    .text-6, .aui-iconfont.text-6 {
        color: #E74C3C;
    }
    .text-7, .aui-iconfont.text-7 {
        color: #F1C40F;
    }
    .text-8, .aui-iconfont.text-8 {
        color: #D35400;
    }
    .text-9, .aui-iconfont.text-9 {
        color: #F1C40F;
    }
    .text-9, .aui-iconfont.text-10 {
        color: #9B59B6;
    }
    .text-9, .aui-iconfont.text-11 {
        color: #95A5A6;
    }
    .text-9, .aui-iconfont.text-12 {
        color: #2ECC71;
    }
    @-webkit-keyframes fadeIn {
        from { opacity: 0.3; }
        to {  }
    }
    @keyframes fadeIn {
        from { opacity: 0.3; }
        to {  }
    }
</style>

<template>
    <div class="my-header">
        <div class="my-today-manager">
            <div class="aui-pull-left aui-col-xs-8">
                <p>今日便笺</p>
                <p id="notepaperToday"><!--今日便笺--></p>
            </div>
            <div class="aui-pull-right aui-col-xs-4 aui-text-center my-sign" id="signStatus" tapmode onclick="sign();">
                <span class="aui-iconfont aui-icon-emoji" id="signEmoji"></span>
                <p id="signText">点我签到</p>
            </div>
        </div>
        <div class="my-resttime">
            作息：<span id="myRestTime"><!--作息时间--></span>
        </div>
    </div>
    <div class="aui-content my-manager-btn">
        <div class="aui-col-5 aui-text-center" tapmode onclick="my('dynamic_add_win');">
            <span class="aui-iconfont aui-icon-edit bg-white text-10"></span>
            <p>分享故事</p>
        </div>
        <div class="aui-col-5 aui-text-center" tapmode onclick="my('my_notepaper_win');">
            <span class="aui-iconfont aui-icon-enclosure bg-white text-4"></span>
            <p>写便笺</p>
        </div>
        <div class="aui-col-5 aui-text-center" tapmode onclick="my('my_rest_win');">
            <span class="aui-iconfont aui-icon-history bg-white text-3"></span>
            <p>作息表</p>
        </div>
        <div class="aui-col-5 aui-text-center" tapmode onclick="my('activity_add_aa_win');">
            <span class="aui-iconfont aui-icon-mark bg-white text-2"></span>
            <p>发约伴</p>
        </div>
        <div class="aui-col-5 aui-text-center" tapmode onclick="my('activity_add_service_win');">
            <span class="aui-iconfont aui-icon-emoji bg-white text-6"></span>
            <p>做达人</p>
        </div>
    </div>
    <div class="aui-content spread-my">

        <div class="aui-text-left aui-col-xs-4 spread-nums">
            <span class="aui-iconfont aui-icon-people3"></span>
            <span>我的邀请码</span>

        </div>
        <div class="aui-text-center aui-pull-right">
            <span><strong id="spreadid"></strong></span>
            <span class="aui-iconfont aui-icon-right"></span>
        </div>
    </div>
    <div class="aui-content my-manager-list">
        <ul class="aui-list-view">
            <li class="aui-list-view-cell aui-img">
                <a class="aui-arrow-right">
                    <div class=" aui-pull-left" tapmode onclick="spaceShow();">
                        <img class="aui-pull-left aui-img-object" src="" id="avatar">
                        <div class="aui-pull-left aui-img-body" id="nickname">sdf</div>
                    </div>
                    <div class="aui-pull-right" tapmode onclick="vipBuy();">
                        <span class="vipicon" id="vipstatus">VIP</span>
                    </div>
                </a>
            </li>
            <li class="aui-list-view-cell aui-img" tapmode onclick="my('my_datum_win');">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-person bg-white text-1"></span>
                    <div class="aui-img-body">资料修改</div>
                </a>
            </li>
            <!--<li class="aui-list-view-cell aui-img" tapmode onclick="my('my_realname_win');">
	            <a class="aui-arrow-right">
	                <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-card bg-white text-5"></span>
	                <div class="aui-img-body">实名认证</div>
	                <span class="aui-badge" id="realNameStatus"></span>
	            </a>
            </li>-->
            <li class="aui-list-view-cell aui-img" tapmode onclick="my('my_wallet_win')">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-money bg-white text-10"></span>
                    <div class="aui-img-body">我的钱包</div>
                </a>
            </li>
            <li class="aui-list-view-cell aui-img" tapmode onclick="my('my_notepaper_win');">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-enclosure bg-white text-4"></span>
                    <div class="aui-img-body">我的便笺</div>
                </a>
            </li>
            <li class="aui-list-view-cell aui-img" tapmode onclick="my('my_activity_win');">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-emoji bg-white text-6"></span>
                    <div class="aui-img-body">我的活动/服务</div>
                </a>
            </li>
            <li class="aui-list-view-cell aui-img" tapmode onclick="my('my_order_win');">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-file2 bg-white text-1"></span>
                    <div class="aui-img-body">活动/服务订单</div>
                </a>
            </li>
            <!--<li class="aui-list-view-cell aui-img">
                <a class="aui-arrow-right">
	                <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-share bg-white text-2"></span>
	                <div class="aui-img-body">我的推广</div>
	            </a>
            </li>-->
        </ul>
    </div>
    <div class="aui-content my-manager-list">
        <ul class="aui-list-view">
            <!--<li class="aui-list-view-cell aui-img">
	            <a class="aui-arrow-right">
	                <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-sitting bg-white text-3"></span>
	                <div class="aui-img-body">智能设置</div>
	            </a>
            </li>-->
            <li class="aui-list-view-cell aui-img" tapmode onclick="changeAccount();">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-order bg-white text-5"></span>
                    <div class="aui-img-body">切换账号</div>
                </a>
            </li>
            <!--<li class="aui-list-view-cell aui-img">
	            <a class="aui-arrow-right">
	                <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-upload bg-white text-12"></span>
	                <div class="aui-img-body">检查更新</div>
	            </a>
            </li>-->
            <li class="aui-list-view-cell aui-img" tapmode onclick="clearCache();">
                <a class="aui-arrow-right">
                    <span class="aui-img-object aui-pull-left aui-iconfont aui-icon-delete bg-white text-6"></span>
                    <div class="aui-img-body">清除缓存</div>
                </a>
            </li>
        </ul>
    </div>
</template>
<script>
    module.exports = {
        data: function(){
            return {
            }
        },
        ready: function () {
            console.log("D-dom准备完毕") // -> 'foo'
        },
        methods:{
            loadPage:function(gift_id){
                console.info(gift_id);
            }
        }
    }
</script>