import {post} from '../http/request'

export const login = params => post('login', params)
