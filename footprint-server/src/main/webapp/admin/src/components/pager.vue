<style>


</style>

<template>

    <div class="row">
        <div class="dataTables_footer clearfix">
            <div class="col-md-6">
                <div class="dataTables_info">共{{totalCount}}条，{{pageCount}}页</div>
            </div>
            <div class="col-md-6">
                <div class="dataTables_paginate paging_bootstrap">
                    <ul class="pagination">
                        <li v-bind:class="{disabled : pageNo==1}"><a v-on:click="prev()" href="javascript:void(0)"><i class="fa fa-angle-left"></i></a></li>
                        <li v-for="i in pageLinks" v-bind:class="{active : i == pageNo}"><a v-on:click="go(i)" href="javascript:void(0)">{{i}}</a></li>
                        <li v-bind:class="{disabled : pageNo==pageCount}"><a v-on:click="next()" href="javascript:void(0)"><i class="fa fa-angle-right"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    module.exports = {
        props: {
            pageNo:{
                type: Number,
                default:1,
                twoWay: true
            },
            pageSize:{
                type: Number,
                default: 10
            },
            totalCount:{
                type: Number,
                default: 0
            }
        },
        data: function () {

        },
        ready: function () {

        },
        computed: {
            pageCount: function() {
                var pageCount = Math.ceil(this.totalCount / this.pageSize);
                return pageCount;
            },
            pageLinks: function() {
                var ret = [];
                for(var i=1; i <= this.pageCount; i++) {
                    ret.push(i)
                }
                return ret;
            }
        },
        methods: {
            go: function(index) {
                this.pageNo = index;
            },
            next: function() {
                if(this.pageNo < this.pageCount) {
                    this.pageNo++;
                }
            },
            prev: function() {
                if(this.pageNo > 1) {
                    this.pageNo--;
                }
            }
        }
    }
</script>