
/**
 * 存储的状态值
 * @type {{isMenu: boolean}}
 */
const state = {
	isMenu: false
}

/**
 * 同步
 * 操作控制 state 状态的方法
 * @type {{hide(*): void, show(*): void}}
 */
const mutations = {
	show(state) {
		state.isMenu = true
	},
	hide(state) {
		state.isMenu = false
	}
}

/**
 * 异步修改
 * 控制 state 状态
 * @type {{hideMenu(*): void, showMenu(*): void}}
 */
const actions = {
	/**
	 * 自定义触发mutations里函数的方法，context与store+实例具有相同方法和属性
	 * @param context
	 */
	hideMenu(context) {
		context.commit('hide')
	},
	showMenu(context) {
		context.commit('show')
	}
}



/**
 * 获取 state 中的值
 * @type {{isShow(*): *}}
 */
const getters = {
	isShow(state) {
		return state.isMenu;
	}
}

export default {
	namespaced: true,
	getters,
	mutations,
	state,
	actions
}
