import {get, deletes, post} from '../http/request'


export const getUserList = (params) => get("/user/", params)

export const deleteUserById = (params) => deletes('/user/delete', params)

export const getDeptList = () => get('/dept/list', {})

export const addUser = (params) => post('/user', params)
