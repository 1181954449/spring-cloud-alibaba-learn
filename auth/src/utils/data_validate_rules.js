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
  callback()
}
export const emailValidate = (rule, value, callback) => {
  if (value === '' || value === undefined ) {
    return callback(new Error('邮箱不能为空'))
  }
  if (!(/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(value))) {
    return callback(new Error('邮箱格式不符合规则'))
  }
  callback()
}
