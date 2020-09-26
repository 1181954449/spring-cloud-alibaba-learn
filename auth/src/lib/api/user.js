import {get} from '../http/request'


export const getUserList = (url, params) => get(url, params)
