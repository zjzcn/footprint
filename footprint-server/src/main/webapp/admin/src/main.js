//生产环境要改
var ROOT_URL = 'http://localhost:7080/footprint';

//official plugins
var Vue = require('vue')
Vue.config.debug = true // turn on debugging mode
var VueRouter = require('vue-router');
Vue.use(VueRouter);
Vue.use(require('vue-resource'));
Vue.http.options.root = ROOT_URL;
Vue.use(require('vue-async-data'));
Vue.use(require('vue-validator'));
//self plugins
Vue.use(require('./plugins/alert.js'))
Vue.use(require('./plugins/confirm.js'))
//component
Vue.component('vue-crumb', require('./components/crumb.vue'))
Vue.component('vue-modal', require('./components/modal.vue'))
Vue.component('vue-widget', require('./components/widget.vue'))
Vue.component('vue-pager', require('./components/pager.vue'))
Vue.component('vue-spinner', require('./components/spinner.vue'))

var app = Vue.extend(require('./main.vue'))

var router = new VueRouter({
    hashbang: true
})

// normal routes
router.map({
    '/overview': {
        component: require('./views/overview.vue')
    },
    '/monitor': {
        component: require('./views/dashboard.vue')
    },
    '/platform/job_list': {
        component: require('./views/platform/uts/job_list.vue')
    },
    '/platform/job_record': {
        component: require('./views/platform/uts/job_record.vue')
    }
})

//默认首屏为ViewA
router.redirect({
    '/':"/overview"
})

router.beforeEach(function (transition) {
    var toPath = transition.to.path;
    console.info()
    if(toPath.replace(/[^/]/g,"").length>1){
        router.app.isIndex = false;
    }else{
        router.app.isIndex = true;
    }
    transition.next()
})

router.afterEach(function (transition) {
    console.log('当前路由为: ' + transition.to.path)
})

// 启动路由 顶层实例APP挂载到id为app的dom上
router.start(app, '#app');

//暴漏vue和router
window.router = router;
window.vue = Vue;
console.info("暴漏vue和router---可进行调试");