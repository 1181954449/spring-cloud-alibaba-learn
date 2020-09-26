import store from '../store/index'

function getToken() {
	return store.getters["UserDetails/getToken"]
}

function getIsLogin() {
	return store.getters["UserDetails/getIsLogin"]
}


// 获取vuex 中的权限数组
function getAuths () {
	return store.getters["UserDetails/getPerms"]
}

function checked(authority)  {
	var currentAuths = getAuths();
	return currentAuths.some(item => authority.includes(item))
}

export {getToken, getIsLogin,	getAuths, checked}
