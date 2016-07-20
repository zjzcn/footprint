<template>
    <div class="load-container load8">
        <div class="loader" :class="{'loader-small': size=='small'}">Loading...</div>
    </div>
</template>

<script>
    module.exports = {
        props: {
            color: {
                type: String,
                default: '#5dc596'
            },
            size: {
                type: String,
                default: ''
            },
            margin: {
                type: String,
                default: '2px'
            },
            radius: {
                type: String,
                default: '100%'
            }
        }
    }

</script>

<style type="text/css">
    .load8 .loader {
        margin: 4px;
        display: inline-block;
        position: relative;
        text-indent: -9999em;
        border-top: 0.6em solid rgba(165, 159, 159, 0.2);
        border-right: 0.6em solid rgba(165, 159, 159, 0.2);
        border-bottom: 0.6em solid rgba(165, 159, 159, 0.2);
        border-left: 0.6em solid #4D7496;
        -webkit-transform: translateZ(0);
        -ms-transform: translateZ(0);
        transform: translateZ(0);
        -webkit-animation: load8 1.1s infinite linear;
        animation: load8 1.1s infinite linear;
    }

    .load8 .loader,
    .load8 .loader:after {
        border-radius: 50%;
        width: 4em;
        height: 4em;
    }

    .load8 .loader-small {
        width: 2em;
        height: 2em;
        border-top: 0.4em solid rgba(165, 159, 159, 0.2);
        border-right: 0.4em solid rgba(165, 159, 159, 0.2);
        border-bottom: 0.4em solid rgba(165, 159, 159, 0.2);
        border-left: 0.4em solid #4D7496;
    }

    .load8 {
        display: inline-block;
    }

    @-webkit-keyframes load8 {
        0% {
            -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
        }
    }

    @keyframes load8 {
        0% {
            -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
        }
    }
</style>