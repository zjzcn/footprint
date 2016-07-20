<template>
    <div :class="{'show':show}" class="modal" role="dialog" transition="modal">
        <div class="modal-dialog" role="document" :style="{'width': width + 'px'}">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" @click="cancel"><span>&times;</span></button>
                    <h4 class="modal-title" >{{title}}</h4>
                </div>
                <div class="modal-body">{{content}}</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" @click="cancel">取消</button>
                    <button type="button" class="btn btn-info" @click="confirm">确认</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    module.exports = {
        data: function () {
            return {
                show:true,
                width:'400'
            }
        },
        ready: function () {
        },
        watch: {

        },
        methods: {
            confirm: function() {
                this.show = false;
                this.callback();
            },
            cancel: function () {
                this.show = false;
            },
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