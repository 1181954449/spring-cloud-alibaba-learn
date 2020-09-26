import Vue from 'vue'
import Router from 'vue-router'
import NProgress from "nprogress";
import {checked} from '../utils/auth'
import store from "../store";

Vue.use(Router)

export const constantRouterMap = [
	{
		path: '/',
		name: 'Index',
		component: () => import(`@/components/page/index`),
		redirect: '/analyzer'
	},
	{
		path: '/login',
		name: 'Login',
		component: () => import('@/components/page/Login')
	},
	{
		path: '*',
		name: 'Not Found',
		redirect: '/404',
		hidden: true
	}
]

var router = new Router({
	mode: 'hash',
	routes: constantRouterMap
})

//当路由进入前
router.beforeEach((to, from, next) => {
	// 每次切换页面时，调用进度条
	NProgress.start();
	let isLogin = store.getters["UserDetails/getIsLogin"]
	// 若加载时间长且不定，担心进度条走完都没有加载完，可以调用　　NProgress.inc（）；//这会以随机数量递增，且永远达不到100%，也可以设指定增量    next();
	// 判断是否为登录状态。 true 已经登录， false 未登录、  如果未登录 则判断是否为 login 路径。 否则强制用户去登录
	if (!isLogin && to.path !== '/login') {
		next('/login')
	} else if (isLogin && to.path === '/login') {
		next('/')
	} else {
		if (to.path === '/' || to.path === '/404' || to.path === '/500' || to.path === '/403') {
			next()
			//  通过 auth 中的权限判断用户是否有权限通过 如果没有权限 则 前往 403 页面
		} else if (isLogin && !checked(to.meta.perms)) {
			next('/403')
		} else {
			next()
		}
	}
});
//当路由进入后：关闭进度条
router.afterEach(() => {
	// 在即将进入新的页面组件前，关闭掉进度条
	NProgress.done()
})


export default router
