import {get, deletes, post} from '../http/request'


export const getUserList = (params) => get("/user/", params)

export const deleteUserById = (params) => deletes('/user/delete', params)

export const getDeptList = () => get('/dept/list', {})

export const addUser = (params) => post('/user', params, {
  ele: 'loginUser'
})


export const existUserByPhone = (params) => post('/user/existByPhone', params, {
  isLoading: true
})

export const existUserByEmail = (params) => post('/user/existByEmail', params, {
  isLoading: true
})

export const existUserByUsername = (params) => post('/user/existByUsername', params, {
  isLoading: true
})
