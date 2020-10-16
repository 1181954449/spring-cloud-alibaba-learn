import {get, post} from "../http/request";

/**
 * 获取 字典分页
 * @param url
 * @param params
 * @returns {Promise<unknown>}
 */
export const getDictPage = (url, params) => get(url, params)

/**
 * 获取字典合集
 * @param url
 * @returns {Promise<unknown>}
 */
export const getDictItems = async (url) => {
  await get(url, {}).then((res) => {
    return res.data.data;
  })
}

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
