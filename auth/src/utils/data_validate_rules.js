import {existUserByPhone, existUserByEmail, existUserByUsername} from "../lib/api/user";

export const phonenumberValidate = (rule, value, callback) => {
  if (value === '' || value === undefined ) {
    return callback(new Error('手机号码不能为空'))
  }
  if (value.length !== 11) {
    return callback(new Error('手机号码格式不符合规则'))
  }
  if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(value))) {
    return callback(new Error("手机号码格式不符合规则"))
  }
  existUserByPhone('phonenumber='+value).then((res) => {
    if (res.data.data !== 0) {
      callback(new Error("手机号已存在，请确认手机号码"))
    } else {
      callback()
    }
  })
}
export const emailValidate = (rule, value, callback) => {
  if (value === '' || value === undefined ) {
    return callback(new Error('邮箱不能为空'))
  }
  if (!(/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(value))) {
    return callback(new Error('邮箱格式不符合规则'))
  }
  existUserByEmail('email='+ value).then((res) => {
    if (res.data.data !== 0) {
      callback(new Error("邮箱已存在，请确认邮箱账号"))
    } else {
      callback()
    }
  })
}

export const userNameValidate = (rule, value, callback) => {
  existUserByUsername('username='+value).then(res => {
    if (res.data.data !== 0) {
      callback(new Error("登录名已经存在，请确认登录名"))
    } else {
      callback()
    }
  })
}
