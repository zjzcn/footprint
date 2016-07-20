<style>
    .table tbody>tr>td.opt{
        padding-top:4px;
        padding-bottom: 4px;
    }
</style>

<template>
    <vue-crumb :list="['统一调度平台','任务管理']"></vue-crumb>
    <!--=== no-padding and table-tabletools ===-->
    <vue-widget title="Job列表">
        <div v-if="$loadingAsyncData">Loading</div>
        <div v-else>
            <table class="table table-striped table-bordered table-tabletools datatable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th class="sorting">任务组</th>
                    <th class="sorting_asc">任务名</th>
                    <th>触发器组</th>
                    <th>触发器名</th>
                    <th>执行服务器</th>
                    <th>创建时间</th>
                    <th>任务接收时间</th>
                    <th>最后上报时间</th>
                    <th>完成时间</th>
                    <th>任务状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="item in resultList">
                        <td>{{item.fireInstanceId}}</td>
                        <td>{{item.jobGroup}}</td>
                        <td>{{item.jobName}}</td>
                        <td>{{item.triggerGroup}}</td>
                        <td>{{item.triggerName}}</td>
                        <td>{{item.clientNodeName}}</td>
                        <td>{{item.createTime}}</td>
                        <td>{{item.clientReceivedTime}}</td>
                        <td>{{item.lastReportTime}}</td>
                        <td>{{item.finishTime}}</td>
                        <td>{{item.jobState}}</td>
                        <td class="opt">
                            <div class="btn-group">
                                <button class="btn btn-sm" @click="resumeJob(item.jobGroup, item.jobName)"><i class="fa fa-play"></i></button>
                                <button class="btn btn-sm" @click="pauseJob(item.jobGroup, item.jobName)"><i class="fa fa-pause"></i></button>
                                <button class="btn btn-sm" @click="deleteJob(item.jobGroup, item.jobName)"><i class="fa fa-trash-o"></i></button>
                                <button class="btn btn-sm" @click="unlockJob(item.jobGroup, item.jobName)"><i class="fa fa-unlock"></i></button>
                                <button class="btn btn-sm" onclick="$(this).closest('tr').next().toggle()"><i class="fa fa-bolt"></i></button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <vue-pager :page-no.sync="pageNo" :page-size="pageSize" :total-count="totalCount"></vue-pager>
        </div>
    </vue-widget>
    <!-- /no-padding and table-tabletools -->
</template>

<script>
    module.exports = {
        data: function () {
            return {
                pageNo: 1,
                pageSize:  1,
                totalCount:  0,
                jobList: {}
            };
        },
        asyncData: function (resolve, reject) {
            this.getRecords(resolve);
        },
        ready: function () {

        },
        watch: {
            'pageNo':function (val, oldVal) {
                this.reloadAsyncData();
            }
        },
        methods: {
            getRecords: function(resolve) {
                this.$http.get('uts/getRecords.do',{pageNo: this.pageNo}).then(function (response) {
                    if(response.data.success == true) {
                        resolve({
                            pageNo: response.data.data.page.pageNo,
                            pageSize:  response.data.data.page.pageSize,
                            totalCount:  response.data.data.page.totalCount,
                            resultList: response.data.data.page.resultList
                        });
                    }
                }, function (response) {
                    // handle error
                });
            }
        },
        components: {}
    }
</script>
