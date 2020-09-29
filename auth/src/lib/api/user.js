import {get, deletes} from '../http/request'


export const getUserList = (url, params) => get(url, params)

export const deleteUserById = (url, params) => deletes(url, params)

export const getDeptList = (url) => get(url, {})
