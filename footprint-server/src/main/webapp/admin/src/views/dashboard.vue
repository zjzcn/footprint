<style>
</style>

<template>
    <sidebar v-bind:menus="menus"/>
</template>

<script>
    module.exports = {
        data: function(){
            return {
                menus : [
                    {
                        path:"",
                        name:"ZK监控平台",
                        icon:"fa fa-laptop",
                        subs:[
                            {
                                path:"/platform/job_list",
                                name:"任务管理",
                                icon:"fa fa-laptop"
                            },
                            {
                                path:"/platform/job_record",
                                name:"执行记录",
                                icon:"fa fa-laptop"
                            }
                        ]
                    },
                    {
                        path:"",
                        name:"Kafka监控平台",
                        icon:"fa fa-share-alt-square",
                        subs:[
                            {
                                path:"/platform/config_tree",
                                name:"配置管理",
                                icon:"fa fa-laptop"
                            }
                        ]
                    },
                    {
                        path:"",
                        name:"ES监控平台",
                        icon:"fa fa-share-alt-square",
                        subs:[
                            {
                                path:"/platform/config_tree",
                                name:"配置管理",
                                icon:"fa fa-laptop"
                            }
                        ]
                    }
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
        },
        components: {
            sidebar: require('../components/sidebar.vue')
        }
    }
</script>