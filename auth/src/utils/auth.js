import store from '../store/index'

function getToken() {
	return store.getters["UserDetails/getToken"]
}

function getIsLogin() {
	return store.getters["UserDetails/getIsLogin"]
}

function logout() {
  store.dispatch('Tabs/actClearTab')
  store.dispatch('UserDetails/actSetIsLogin', false)
  store.dispatch('UserDetails/actSetPerms', [])
  store.dispatch('UserDetails/actSetToken', "")
  store.dispatch('UserDetails/actSetUserDetails', {})
  store.dispatch('UserDetails/actSetMenus', [])
  this.actClearTab()
  this.$router.push("/login")
}

// 获取vuex 中的权限数组
function getAuths () {
	return store.getters["UserDetails/getPerms"]
}

function checked(authority)  {
	var currentAuths = getAuths();
	return currentAuths.some(item => authority.includes(item))
}

export {getToken, getIsLogin,	getAuths, checked, logout}
