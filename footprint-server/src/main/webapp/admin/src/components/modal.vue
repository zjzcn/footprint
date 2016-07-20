<template>
    <div class="modal" role="dialog" :class="{'show': show}" transition="modal">
        <div class="modal-dialog" role="document" :style="{'width': width}">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" @click="cancel"><span>&times;</span></button>
                    <h4 class="modal-title">{{title}}</h4>
                </div>

                <div class="modal-body"><slot></slot></div>

                <div class="modal-footer">
                    <slot name="modal-footer">
                        <button type="button" class="btn btn-default" @click="cancel">取消</button>
                        <button type="button" class="btn btn-info" @click="confirm">确定</button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    module.exports = {
        props: {
            show: {
                require: true,
                type: Boolean,
                twoWay: true
            },
            title: {
                type: String,
                default: ''
            },
            width: {
                type: String,
                default: '600px'
            },
            callback: {
                type: Function,
                default: function (){}
            }
        },
        data: function () {
            return {

            }
        },
        ready: function () {
        },
        watch: {},
        methods: {
            confirm: function () {
                this.show = false;
                this.callback();
                this.clear();
            },
            cancel: function () {
                this.show = false;
                this.clear();
            },
            clear: function () {
                $(':input',this.$el)
                        .not(':button, :submit, :reset, :hidden')
                        .val('')
                        .removeAttr('checked')
                        .removeAttr('selected');
            }
        }
    }
</script>

<style>
    .modal-enter, .modal-leave {
        opacity: 0;
    }

    .modal-enter,
    .modal-leave {
        -webkit-transform: scale(1.1);
        transform: scale(1.1);
    }
</style>