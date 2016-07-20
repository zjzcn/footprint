<style>

</style>

<template>
    <vue-header></vue-header>
    <vue-sidebar :menu="menu"></vue-sidebar>
</template>

<script>
module.exports = {
    ready:function() {
        App.init(); // Init layout and core plugins
        Plugins.init(); // Init all plugins
    },
    data :function() {
        return {
			menu : [
                {
                    url:"/overview",
                    name:"概览",
                    icon:"fa fa-tv"
                },
                {
                    url:"/dashboard",
                    name:"仪表盘",
                    icon:"fa fa-dashboard"
                },
                {
                    url:"",
                    name:"业务管理平台",
                    icon:"fa fa-cube",
                    menu:[
                        {
                            url:"/platform/job_list",
                            name:"任务管理",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/job_record",
                            name:"执行记录",
                            icon:"fa fa-file-o"
                        }
                    ]
                },
                {
                    url:"",
                    name:"系统管理平台",
                    icon:"fa fa-laptop",
                    menu:[
                        {
                            url:"/platform/job_record",
                            name:"数据字典管理",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/job_record",
                            name:"操作日志管理",
                            icon:"fa fa-file-o"
                        }
                    ]
                },
                {
                    url:"",
                    name:"用户管理平台",
                    icon:"fa fa-laptop",
                    menu:[
                        {
                            url:"/platform/job_list",
                            name:"用户管理",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/job_record",
                            name:"角色管理",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/job_record",
                            name:"权限管理",
                            icon:"fa fa-file-o"
                        }
                    ]
                },
				{
					url:"",
					name:"统一调度平台",
					icon:"fa fa-tasks",
					menu:[
						{
							url:"/platform/job_list",
							name:"任务管理",
							icon:"fa fa-file-o"
						},
						{
							url:"/platform/job_record",
							name:"执行记录",
							icon:"fa fa-file-o"
						}
					]
				},
				{
					url:"",
					name:"统一配置平台",
					icon:"fa fa-cog",
					menu:[
						{
							url:"/platform/config_tree",
							name:"配置管理",
							icon:"fa fa-file-o"
						}
					]
				},
                {
                    url:"",
                    name:"ZK监控平台",
                    icon:"fa fa-area-chart",
                    menu:[
                        {
                            url:"/platform/config_tree",
                            name:"集群管理",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"节点树",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"指标监控",
                            icon:"fa fa-file-o"
                        }
                    ]
                },
                {
                    url:"",
                    name:"Kafka监控平台",
                    icon:"fa fa-bar-chart",
                    menu:[
                        {
                            url:"/platform/config_tree",
                            name:"概览",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"集群管理",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"Topic监控",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"生成消费监控",
                            icon:"fa fa-file-o"
                        }
                    ]
                },
                {
                    url:"",
                    name:"告警平台",
                    icon:"fa fa-warning",
                    menu:[
                        {
                            url:"/platform/config_tree",
                            name:"告警列表",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"告警配置",
                            icon:"fa fa-file-o"
                        },
                        {
                            url:"/platform/config_tree",
                            name:"告警人员",
                            icon:"fa fa-file-o"
                        }
                    ]
                },
                {
                    url:"",
                    name:"统计分析平台",
                    icon:"fa fa-line-chart",
                    menu:[
                        {
                            url:"/platform/config_tree",
                            name:"PV-UV统计",
                            icon:"fa fa-file-o"
                        }
                    ]
                }
			]
		}
    },
    components: {
        vueHeader: require('./components/header.vue'),
        vueSidebar: require('./components/sidebar.vue')
    }
}
</script>
