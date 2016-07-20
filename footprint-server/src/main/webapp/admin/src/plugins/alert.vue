<template>
    <div
            v-show="show"
            :class="{
            'alert': true,
            'alert-success':(type == 'success'),
            'alert-warning':(type == 'warning'),
            'alert-info':	(type == 'info'),
            'alert-danger':	(type == 'error'),
            'top-right': 	(placement === 'top-right')
           }"
            transition="fade"
            :style="{'width': width + 'px'}"
            style="z-index: 10000000;"
            role="alert">
        <button type="button" class="close" @click="show = false">
            <span>&times;</span>
        </button>
        <span class="icon-ok-circled alert-icon-float-left">
            <i v-if="type=='success'" class="fa fa-check-circle"></i>
            <i v-if="type=='info'" class="fa fa-info-circle"></i>
            <i v-if="type=='warning'" class="fa fa-exclamation-triangle"></i>
            <i v-if="type=='error'" class="fa fa-times-circle"></i>
        </span>
        <strong>提示</strong>

        <p>{{message}}</p>

    </div>
</template>

<script>
    module.exports = {
        data: function () {
            return {
                show:true,
                duration: 3,
                placement: 'top-right',
                width:'300'
            }
        },
        ready: function () {
            if (!!this.duration) {
                var self = this;
                this._timeout = setTimeout(function () {
                    self.show = false
                }, this.duration * 1000)
            }
        },
        watch: {
            'show': function (val, oldVal) {
                if (this._timeout) clearTimeout(this._timeout)
            }
        },
        methods: {}
    }
</script>

<style>
    .fade-transition {
        transition: opacity .3s ease;
    }

    .fade-enter,
    .fade-leave {
        opacity: 0;
    }

    .alert.top-right {
        position: fixed;
        top: 30px;
        right: 50px;
        z-index: 2;
    }

    .alert-icon-float-left {
        font-size: 32px;
        float: left;
        margin-right: 5px;
        padding: 0 10px;
    }

</style>