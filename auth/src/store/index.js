import Vue from 'vue'
import Vuex from 'vuex'
import Menu from "./modules/menus"
import UserDetails from './modules/userdetails'
import Tabs from './modules/tabs'

Vue.use(Vuex)



const store = new Vuex.Store({
	modules: {
		Menu,
		UserDetails,
		Tabs
	}
})

export default store;
