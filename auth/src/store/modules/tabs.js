/**
 * 存储 tab 切换页
 * @type {{tabs: []}}
 */
const state = {
	tabs: [
		{
			id: '99999',
			path: '/analyzer',
			title: '仪表盘',
			active: true
		}
	],
  keepLive: [
  ]
}

const getters = {

  /**
   * 获取keeplive
   */
  getKeepLive (state) {
    return state.keepLive
  },

  /**
	 *
	 * @param state
	 * @returns {[]|*[]}
	 */
	getTabs (state) {
		return state.tabs;
	},

	/**
	 * 获取当前 tab 页
	 * @param state
	 * @returns {{path: string, active: boolean}|*}
	 */
	getCurrentTab(state) {
		let current;
		let tabs = state.tabs;
		tabs.forEach((e) => {
			if (e.active) {
				current =  e;
			}
		})
		return current ? current : tabs[0]
	},

	getCurrentTabName (state) {
		let current;
		let tabs = state.tabs;
		tabs.forEach((e) => {
			if (e.active) {
				current = e;
			}
		})
		if (current) {
			return current.path
		} else {
			return tabs[0].path;
		}
	}
}

const mutations = {

	/**
	 * 添加 tab 页到 数组中
	 * @param state
	 * @param tab
	 */
	addTabs (state, tab) {
		let index;
		state.tabs.forEach((e,i) => {
			e.active = false
			if (e.path === tab.path) {
				index = i
			}
		})
		index !== undefined ? state.tabs[index].active = true : state.tabs.push(tab)
    !state.keepLive.includes(tab.name) && tab.name !== 'Analyzer' ? state.keepLive.push(tab.name) : ''
	},
	/**
	 * 删除索引
	 * @param state
	 * @param path
	 */
	delTab (state, index) {
		if (index) {
			if (state.tabs[index].active) {
				state.tabs.splice(index, 1)
				state.tabs[state.tabs.length - 1].active = true
			}else {
				state.tabs.splice(index, 1)
			}
      state.keepLive.splice(index-1, 1)
		}
	},
	/**
	 * 设置当前索引
	 * @param state
	 * @param index
	 */
	setCurrentTab(state, index) {
		state.tabs.forEach((e) => {
			e.active = false
		})
		state.tabs[index].active = true
	},
	clearTab (state) {
		state.tabs = Array.of(state.tabs[0]);
	}
}

const actions = {
	actAddTabs (context, tab) {
		context.commit('addTabs', tab)
	},
	actDelTab (context, index) {
		context.commit('delTab', index)
	},
	actSetCurrentTab (context, index) {
		context.commit('setCurrentTab', index)
	},
	actClearTab (context) {
		context.commit('clearTab')
	}
}

export default {
	namespaced: true,
	state, getters, mutations, actions
}
