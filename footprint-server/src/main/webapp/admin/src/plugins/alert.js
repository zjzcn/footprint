
(function () {
    var vue // lazy bind

    var api = {
        install: function (Vue) {
            var Alert = Vue.extend(require('./alert.vue'));
            var alert = null;

            Vue.prototype.$alert = function ( message, type) {
                alert = new Alert({
                    el: document.createElement('div'),
                    data: function () {
                        return {
                            message: message,
                            type: type
                        }
                    }
                });
                alert.$appendTo(document.body);
            }
        }
    }

    module.exports = api
})()