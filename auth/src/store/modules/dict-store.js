/**
 * 存储字典。
 */

const state = {
  dictItems: {}
}

const getters = {

  getDictItems: (state) => (dictId) => {
    return state.dictItems[dictId]
  },

}

const mutations = {

  /**
   * 设置字典到 vuex 中
   */
  setDictItem (state, payload) {
    state.dictItems[payload.dictId] = payload.dictItems;
  },
  /**
   * 退出登录时，清空字典
   * @param state
   */
  setClearDictItems(state) {
    state.dictItems = {}
  }
}

const actions = {
  /**
   * 设置字典
   * @param context
   * @param payload
   */
  actSetDictItem (context, payload) {
    context.commit('setDictItem', payload )
  },
  /**
   * 退出登录时，清空字典
   * @param state
   */
  actSetClearDictItems (context, payload) {
    context.commit('setClearDictItems')
  }
}

export default {
  namespaced: true,
  state, getters, mutations, actions
}
