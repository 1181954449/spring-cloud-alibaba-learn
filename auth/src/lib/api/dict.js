import vm from '../../main'


import {get, post} from "../http/request";

/**
 * 获取 字典分页
 * @param url
 * @param params
 * @returns {Promise<unknown>}
 */
export const getDictPage = (params) => get('/dict/', params)

/**
 * 获取字典合集
 * @param url
 * @returns {Promise<unknown>}
 */
export const getDictItems = (dictId) => {
  if (!vm.$store.getters["DictStore/getDictItems"](dictId)) {
    get('/dict/item/' + dictId, {}, {isLogin: true}).then((res) => {
      vm.$store.dispatch('DictStore/actSetDictItem',{
        dictId: dictId,
        dictItems: res.data.data
      })
    })
  }
}

/**
 *
 * @param dictId
 * @returns {Promise | Promise<unknown>}
 */
export const getDictItemsByDictId = (dictId) => get('/dict/dictItems/'+dictId, {}, {isLoading: true})
/**
 * 添加字典
 * @param url
 * @param params
 * @returns {Promise<unknown>}
 */
export const addDict = (url, params) => post(url, params)

/**
 * 添加字典选项
 * @param url
 * @param params
 * @returns {Promise<unknown>}
 */
export const addDictItem = (url, params) => post(url, params)
