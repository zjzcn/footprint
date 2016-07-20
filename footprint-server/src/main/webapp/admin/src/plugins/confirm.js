
(function () {
    var vue // lazy bind

    var api = {
        install: function (Vue) {
            var Confirm = Vue.extend(require('./confirm.vue'));
            var confirm = null;

            Vue.prototype.$confirm = function ( title, content, callback) {
                confirm = new Confirm({
                    el: document.createElement('div'),
                    data: function () {
                        return {
                            title: title,
                            content: content,
                            callback: callback
                        }
                    }
                });
                confirm.$appendTo(document.body);
            }
        }
    }

    module.exports = api
})()