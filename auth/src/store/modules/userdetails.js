/**
 * 用户登录之后存储的状态
 * @type {{perms: [], userDetails: {}, token: string}}
 */
const state = {
	userDetails: {},
	token: '',
	perms: [],
	isLogin: false,
	menus: [],
}

const getters = {
	/**
	 * 用来判断是否登录
	 * @param state
	 * @returns {boolean}
	 */
	getIsLogin (state) {
		return state.isLogin;
	},

	getMenuByPath: (state) => (path) => {

		let menus = state.menus;
		let menu = null;
		menus.forEach((e1) => {
			// 判断一级目录中是否存在 path  如果存在直接返回
			if (e1.path === path) {
				menu = e1;
			}
			// 不存在则 循环二级目录
			e1.children.forEach((e2) => {
				if (e2.path === path) {
					menu = e2;
				}
			})

		})
		return menu;
	},

	/**
	 * 用户拥有的菜单列表
	 * @param state
	 * @returns {[]}
	 */
	getMenus (state) {
		return state.menus;
	},

	/**
	 * 获取用户详细信息对象
	 * @param state
	 * @returns {{}}
	 */
	getUserDetails (state) {
		return state.userDetails
	},
	/**
	 * 获取头像
	 * @param state
	 * @returns {*}
	 */
	getAvatar (state) {
		return state.userDetails.avatar
	},
	/**
	 * 获取 昵称
	 * @param state
	 * @returns {*}
	 */
	getNickName (state) {
		return state.userDetails.nickName
	},
	/**
	 * 获取手机号码
	 * @param state
	 * @returns {*}
	 */
	getPhoneNumber (state) {
		return state.userDetails.phonenumber
	},
	/**
	 * 获取邮箱
	 * @param state
	 * @returns {*}
	 */
	getEmail (state) {
		return state.userDetails.eamil
	},
	/**
	 * 获取用户ID
	 * @param state
	 * @returns {*}
	 */
	getUserId (state) {
		return state.userDetails.userId
	},
	/**
	 * 获取用户token
	 * @param state
	 * @returns {string|Object.value|Token.value|CancelToken|string}
	 */
	getToken (state) {
		return state.token
	},
	/**
	 * 获取用户的权限。
	 * @param state
	 * @returns {[]|*[]}
	 */
	getPerms (state) {
		return state.perms
	}
}

const mutations = {
	/**
	 * 设置 token 到 vuex 中
	 * @param state
	 * @param token
	 */
	setToken (state, token) {
		state.token  = token
	},

	/**
	 * 设置用户的菜单列表到 vuex
	 * @param state
	 * @param menus
	 */
	setMenus (state, menus) {
		state.menus = menus;
	},

	/**
	 * 登录成功之后，设置用户信息到 vuex
	 * @param state
	 * @param userDetails
	 */
	setUserDetails (state, userDetails) {
		state.userDetails = userDetails
	},
	/**
	 * 设置用户权限到 vuex
	 * @param state
	 * @param perms
	 */
	setPerms (state, perms) {
		state.perms = perms
	},

	/**
	 * 设置是否登录
	 * @param state
	 * @param isLogin  true 登录  false 未登录
	 */
	setIsLogin(state, isLogin) {
		state.isLogin = isLogin
	}
}

const actions = {
	actSetToken (context, token) {
		context.commit('setToken', token)
	},

	actSetUserDetails (context, userDetails) {
		context.commit('setUserDetails', userDetails)
	},

	actSetPerms (context, perms) {
		context.commit('setPerms', perms)
	},

	actSetIsLogin (context, isLogin) {
		context.commit('setIsLogin', isLogin)
	},

	actSetMenus (context, menus) {
		context.commit('setMenus', menus)
	}
}

export default {
	namespaced: true,
	state, getters, mutations, actions
}
