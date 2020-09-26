import store from "../store";
import router from "../router";

/**
 * 根据后台传来的menus 生成对应的 动态路由数组
 * @param menus
 */
export function getRouters(){
	let menus = store.getters["UserDetails/getMenus"]
	let routes = []
	menus.forEach((e1) => {
		e1.children.forEach((e2) => {
			let routerObj = {}
			routerObj.path = e2.path
			routerObj.component = () => import(`@/components${e2.component}`)
			routerObj.name = e2.name
			let meta = {}
			meta.title = e2.menuName
			meta.perms = e2.perms;
			routerObj.meta = meta
			routes.push(routerObj)
		})
	})

	routes.push({
		path: '/404',
		name: 'Not Found',
		component: () => import(`@/components/page/view/404`),
		meta: {
			title: 'Not Found'
		}
	})
	routes.push({
		path: '/403',
		name: '权限不足',
		component: () => import(`@/components/page/view/403`),
		meta: {
			title: '权限不足'
		}
	})
	let rootRoutes = [
		{
			path: '/',
			name: 'Index',
			component: () => import(`@/components/page/index`),
			children: routes
		},
	]
	router.options.routes = rootRoutes;
	router.addRoutes(rootRoutes)
}
