<style>

</style>

<template>
    <div id="container">
        <div id="sidebar" class="sidebar-fixed">
            <div id="sidebar-content">
                <!--=== Navigation ===-->
                <ul id="nav">
                    <li v-for="menu1 in menu">
                        <a v-if="menu1.url" v-link="{path: menu1.url, activeClass: 'current'}">
                            <i class="{{menu1.icon}}"></i>
                            <span class="title">{{menu1.name}}</span>
                        </a>
                        <a v-else href="javascript:void(0);">
                            <i class="{{menu1.icon}}"></i>
                            <span class="title">{{menu1.name}}</span>
                        </a>
                        <ul class="sub-menu" v-if="menu1.menu">
                            <li v-for="menu2 in menu1.menu">
                                <a v-link="{path: menu2.url, activeClass: 'current'}">
                                    <i class="{{menu2.icon}}"></i>
                                    {{menu2.name}}
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- /Navigation -->
            </div>
            <div id="divider" class="resizeable"></div>
        </div>
        <!-- /Sidebar -->
        <div id="content">
            <div class="container">
                <router-view keep-alive></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    module.exports = {
        props: ['menu'],
        data: function () {

        },
        ready: function () {
            //$('a.current').parent().parent('ul').show();
        },
        methods: {}
    }
</script>