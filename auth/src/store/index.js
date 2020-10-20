import Vue from 'vue'
import Vuex from 'vuex'
import Menu from "./modules/menus"
import UserDetails from './modules/userdetails'
import Tabs from './modules/tabs'
import DictStore from './modules/dict-store'

Vue.use(Vuex)



const store = new Vuex.Store({
	modules: {
		Menu,
		UserDetails,
		Tabs,
    DictStore
	}
})

export default store;
