// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import TablePagination from "./components/page/common/TablePagination";

import './assets/icon/iconfont.css';
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import store from './store/index'
import Auth from './direct/auth'
import {getRouters} from './utils/router-utils'


/**
 * 添加页面加载进度
 */
NProgress.configure({
	easing: 'ease',  // 动画方式
	speed: 500,  // 递增进度条的速度
	showSpinner: false, // 是否显示加载ico
	trickleSpeed: 200, // 自动递增间隔
	minimum: 0.3 // 初始化时的最小百分比
})

Vue.use(ElementUI)


// 在页面加载的时候 读取 sessionStorage  中的数据
{
	if (sessionStorage.getItem('store')) {
		store.replaceState(Object.assign({}, store.state, JSON.parse(sessionStorage.getItem('store'))))
		sessionStorage.setItem("store", JSON.stringify({}))
	}

	// 页面要关闭的时候， 将 store 数据添加到 sessionStorage 中
	window.addEventListener('beforeunload', () => {
		sessionStorage.setItem("store", JSON.stringify(store.state))
	})

	getRouters()
}


// 注册自定义权限指令
Vue.use(Auth)

Vue.component("TabPage", TablePagination)

/* eslint-disable no-new */
var vm = new Vue({
	el: '#app',
	router,
	store,
	components: {App},
	template: '<App/>'
})
export default vm
