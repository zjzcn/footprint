<style>
    .table tbody>tr>td.opt{
        padding-top:4px;
        padding-bottom: 4px;
    }
    .trigger .row div {
        padding: 8px;
    }
    .trigger .row div.opt {
        padding-top:4px;
        padding-bottom: 4px;
    }
    .trigger .row {
        border-bottom: 1px solid #ddd;
        margin: 0px;
    }
</style>

<template>
    <vue-crumb :list="['统一调度平台','任务管理']"></vue-crumb>
    <!--=== no-padding and table-tabletools ===-->
    <vue-widget title="Job列表">
        <template slot="toolbar">
            <span class="btn btn-xs" @click="show=true"><i class="fa fa-plus"></i>新增</span>
        </template>
        <div v-if="$loadingAsyncData"><vue-spinner></vue-spinner></div>
        <div v-else>
        <table class="table table-striped table-bordered table-tabletools datatable">
            <thead>
            <tr>
                <th class="sorting">任务组</th>
                <th class="sorting_asc">任务名</th>
                <th>已暂停</th>
                <th>执行中</th>
                <th>并发任务</th>
                <th>调用URL</th>
                <th>调用类型</th>
                <th>调用信息</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <template v-for="item in jobList">
                <tr>
                    <td>{{item.jobGroup}}</td>
                    <td>{{item.jobName}}</td>
                    <td>{{item.paused}}</td>
                    <td>{{item.executing}}</td>
                    <td>{{item.concurrentDisallowed}}</td>
                    <td>{{item.invocationUrl}}</td>
                    <td>{{item.invocationType}}</td>
                    <td v-if="item.invocationType=='springBean'">{{'beanId: '+item.beanId+', method: '+item.methodName}}</td>
                    <td v-else>{{'className: '+item.className+', method: '+item.staticMethodName}}</td>
                    <td class="opt">
                        <div class="btn-group">
                            <button class="btn btn-sm" :class="{disabled:!item.paused}" @click="resumeJob(item.jobGroup, item.jobName)"><i class="fa fa-play"></i></button>
                            <button class="btn btn-sm" :class="{disabled:item.paused}"  @click="pauseJob(item.jobGroup, item.jobName)"><i class="fa fa-pause"></i></button>
                            <button class="btn btn-sm" @click="unlockJob(item.jobGroup, item.jobName)"><i class="fa fa-unlock"></i></button>
                            <button class="btn btn-sm" @click="deleteJob(item.jobGroup, item.jobName)"><i class="fa fa-edit"></i></button>
                            <button class="btn btn-sm" @click="deleteJob(item.jobGroup, item.jobName)"><i class="fa fa-trash-o"></i></button>
                            <button class="btn btn-sm" onclick="$(this).closest('tr').next().toggle()"><i class="fa fa-angle-down"></i></button>
                        </div>
                    </td>
                </tr>
                <tr class="trigger" style="display: none"><td colspan="10">
                    <div class="row">
                        <div class="col-md-1">触发器组</div>
                        <div class="col-md-2">触发器名</div>
                        <div class="col-md-2">表达式</div>
                        <div class="col-md-2">上一次触发时间</div>
                        <div class="col-md-2">下一次触发时间</div>
                        <div class="col-md-1">优先级</div>
                        <div class="col-md-1">状态</div>
                        <div class="col-md-1">操作</div>
                    </div>
                    <div class="row" v-for="trigger in item.triggerList">
                        <div class="col-md-1">{{trigger.triggerGroup}}</div>
                        <div class="col-md-2">{{trigger.triggerName}}</div>
                        <div class="col-md-2">{{trigger.cronExpression}}</div>
                        <div class="col-md-2">{{trigger.lastFireTime}}</div>
                        <div class="col-md-2">{{trigger.nextFireTime}}</div>
                        <div class="col-md-1">{{trigger.priority}}</div>
                        <div class="col-md-1"><span class="label label-success">{{trigger.triggerState}}</span></div>
                        <div class="col-md-1 opt">
                            <button class="btn btn-sm" @click="resumeJob(item.jobGroup, item.jobName)"><i class="fa fa-edit"></i></button>
                            <button class="btn btn-sm" @click="pauseJob(item.jobGroup, item.jobName)"><i class="fa fa-trash-o"></i></button>
                        </div>
                    </div>
                </td>
                </tr>
            </template>
            </tbody>
        </table>
        <vue-pager :page-no.sync="pageNo" :page-size="pageSize" :total-count="totalCount"></vue-pager>
        </div>
    </vue-widget>
    <!-- /no-padding and table-tabletools -->

    <vue-modal :show.sync="show" title="新增任务" width="800px" :confirm="">
        <validator name="validation1">
            <form class="form-horizontal" id="pForm">
                <div class="form-group">
                    <label class="col-md-3 control-label">任务组 <span class="required">*</span></label>
                    <div class="col-md-9">
                        <input type="text" name="jobGroup" class="form-control input-width-xlarge">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">任务名 <span class="required">*</span></label>
                    <div class="col-md-5">
                        <input type="text" name="jobName" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">禁止并发 <span class="required">*</span></label>
                    <div class="col-md-8">
                        <label class="radio-inline">
                            <input type="radio" name="optionsRadios2" value="option1">
                            是
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="optionsRadios2" value="option2">
                            否
                        </label>
                        <span class="help-block">Something went wrong</span></div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">调用URL <span class="required">*</span></label>
                    <div class="col-md-8">
                        <input type="text" name="email1" class="form-control">
                        <span class="help-block">Something went wrong</span></div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">调用类型 <span class="required">*</span></label>
                    <div class="col-md-8">
                        <select class="form-control" id="culture">
                            <option value="en-EN"></option>
                            <option value="de-DE">SpringBean调用</option>
                            <option value="ja-JP">静态方法调用</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">SpringBeanId <span class="required">*</span></label>
                    <div class="col-md-8">
                        <input type="text" name="cpass1" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">方法名 <span class="required">*</span></label>
                    <div class="col-md-8">
                        <input type="text" name="digits1" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">类名 <span class="required">*</span></label>
                    <div class="col-md-8">
                        <input type="text" name="cpass1" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">方法名 <span class="required">*</span></label>
                    <div class="col-md-8">
                        <input type="text" name="digits1" class="form-control">
                    </div>
                </div>
            </form>
        </validator>
    </vue-modal>
    <button @click="show=true">ddd</button>
</template>

<script>
    module.exports = {
        data: function () {
            return {
                pageNo: 1,
                pageSize:  1,
                totalCount:  0,
                jobList: {},
                show: false
            };
        },
        asyncData: function (resolve, reject) {
            this.getJobList(resolve);
        },
        ready: function () {
            $('.form-horizontal').validate({
                errorElement : 'span',
                errorClass : 'help-block',
                focusInvalid : true,
                rules : {
                    jobGroup : {
                        required : true,
                        minlength: 2
                    },
                    password : {
                        required : true
                    },
                    intro : {
                        required : true
                    }
                },
                messages : {
                    jobGroup : {
                        required : "Username is required."
                    },
                    password : {
                        required : "Password is required."
                    },
                    intro : {
                        required : "Intro is required."
                    }
                },
                highlight : function(element) {
                    $(element).closest('.form-group').addClass('has-error');
                },
                success : function(label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },
                errorPlacement : function(error, element) {
                    element.parent('div').append(error);
                },
                submitHandler : function(form) {
                    this.createJob();
                }
            });
            $('.form-horizontal input').keypress(function(e) {
                if (e.which == 13) {
                    if ($('.form-horizontal').validate().form()) {
                        $('.form-horizontal').submit();
                    }
                    return false;
                }
            });
        },
        watch: {
            'pageNo':function (val, oldVal) {
                this.reloadAsyncData();
            }
        },
        methods: {
            getJobList: function(resolve) {
                this.$http.get('uts/jobList.do',{pageNo: this.pageNo}).then(function (response) {
                    if(response.data.success == true) {
                        resolve({
                            pageNo: response.data.data.jobList.pageNo,
                            pageSize:  response.data.data.jobList.pageSize,
                            totalCount:  response.data.data.jobList.totalCount,
                            jobList: response.data.data.jobList.resultList
                        });
                    }
                }, function (response) {
                    // handle error
                });
            },
            pauseJob: function(jobGroup, jobName) {
                var self = this;
                this.$confirm('暂停任务','确定暂停任务吗？', function() {
                    var params = {jobGroup: jobGroup, jobName: jobName};
                    this.$http.get('uts/pauseJob.do', params).then(function (response) {
                        if(response.data.success == true) {
                            this.$alert('任务暂停成功', 'success');
                            self.reloadAsyncData();
                        }
                    }, function (response) {
                        this.$alert('任务暂停错误', 'error');
                    });
                });
            },
            resumeJob: function(jobGroup, jobName) {
                var self = this;
                this.$confirm('恢复任务','确定恢复任务吗？', function() {
                    var params = {jobGroup: jobGroup, jobName: jobName};
                    this.$http.get('uts/resumeJob.do', params).then(function (response) {
                        if(response.data.success == true) {
                            this.$alert('任务恢复成功', 'success');
                            self.reloadAsyncData();
                        }
                    }, function (response) {
                        this.$alert('任务恢复错误', 'error');
                    });
                });
            },
            deleteJob: function(jobGroup, jobName) {
                var self = this;
                this.$confirm('删除任务','确定删除任务吗？', function() {
                    var params = {jobGroup: jobGroup, jobName: jobName};
                    this.$http.get('uts/deleteJob.do', params).then(function (response) {
                        if(response.data.success == true) {
                            this.$alert('任务删除成功', 'success');
                            self.reloadAsyncData();
                        }
                    }, function (response) {
                        this.$alert('任务删除错误', 'error');
                    });
                });
            },
            unlockJob: function(jobGroup, jobName) {
                var params = {jobGroup: jobGroup, jobName: jobName};
                this.$http.get('uts/unlockJob.do', params).then(function (response) {
                    if(response.data.success == true) {
                        this.reloadAsyncData();
                    }
                }, function (response) {

                    // handle error
                });
            },
            createJob: function() {
                var self = this;
                this.$confirm('暂停任务','确定暂停任务吗？', function() {
                    var params = {jobGroup: jobGroup, jobName: jobName};
                    this.$http.get('uts/createJob.do', params).then(function (response) {
                        if(response.data.success == true) {
                            this.$alert('任务暂停成功', 'success');
                            self.reloadAsyncData();
                        }
                    }, function (response) {
                        this.$alert('任务暂停错误', 'error');
                    });
                });
            },
        },
        components: {}
    }
</script>
