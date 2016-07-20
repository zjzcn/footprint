<style>


</style>

<template>
    <!--=== no-padding and table-tabletools ===-->
    <div class="row custom-mt20">
        <div class="col-md-12">
            <div class="widget box">
                <div class="widget-header">
                    <h4><i class="fa fa-reorder"></i> {{title}}</h4>
                    <div class="toolbar no-padding">
                        <div class="btn-group">
                            <slot name="toolbar">
                            </slot>
                            <span class="btn btn-xs widget-collapse"><i class="fa fa-angle-down"></i></span>
                        </div>
                    </div>
                </div>
                <div class="widget-content no-padding">
                    <slot></slot>
                </div>
            </div>
        </div>
    </div>
    <!-- /no-padding and table-tabletools -->
</template>

<script>
    module.exports = {
        props: {
            title:{
                type: String
            },
            buttons: {
                type: Array
            }
        },
        data: function () {

        },
        ready: function () {

        },
        computed: {
        },
        methods: {
        }
    }
</script>