/* eslint-disable brace-style */
/* eslint-disable semi */
/* eslint-disable no-unused-vars */
import axios from 'axios'
import QS from 'qs'
import vm from '../../main'
import {logout} from '../../utils/auth'
import {Message, Loading} from 'element-ui'

axios.defaults.timeout = 10000
// eslint-disable-next-line eqeqeq
if (process.env.NODE_ENV == 'development') {
  axios.defaults.baseURL = 'http://localhost:9090/'
}

// eslint-disable-next-line eqeqeq
else if (process.env.NODE_ENV == 'debug') {
  axios.defaults.baseURL = 'http://localhost:9090/'
}

// eslint-disable-next-line eqeqeq
else if (process.env.NODE_ENV == 'production') {
  axios.defaults.baseURL = 'http://localhost:9090';
}

// loading 设置局部刷新，并且所有请求完成后关闭loading
let loadingAnimat;
// 声明一个对用用于存储 请求个数
let needLoadingRequesting = 0;

// 开始加载动画方法
function startLoading() {
  loadingAnimat = Loading.service({
    lock: true,
    text: 'Loading',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.7)',
    target: document.querySelector("#default-loading") || document.querySelector("#warp")
  })
}

// 关闭加载动画方法
function endLoading() {
  loadingAnimat.close()
}

function showFullScreenLoading() {
  if (needLoadingRequesting === 0) {
    startLoading();
  }
  needLoadingRequesting++
}

function hideFullScreenLoading() {

  setTimeout(function () {
    if (needLoadingRequesting <= 0) return;
    needLoadingRequesting--
    if (needLoadingRequesting === 0) {
      endLoading()
    }
  }, 300)
}

axios.interceptors.request.use(config => {
  if (!config.isLoading) { // 如果配置了isLoading: false，则不显示loading
    showFullScreenLoading()
  }
  console.log('============请求开始，设置请求头=================')
  // 请求前设置请求头
  config.headers['token'] = 'Bearer ' + vm.$store.getters["UserDetails/getToken"]
  return config;
}, error => {
  hideFullScreenLoading()
  if (error.response.status) {
    console.log(error);
  }
  return Promise.reject(error.response);
})

axios.interceptors.response.use(response => {
  hideFullScreenLoading();
  if (!response) {
    return;
  }
  let respBody = response['data'];
  let cusStatus = respBody.status
  if (cusStatus === 1001001002) {
    logout();
    return;
  } else if (cusStatus === 0) {
    return Promise.resolve(response);
  } else {
    tipMsg(respBody.msg);
    return Promise.resolve(response);
  }
}, error => {
  hideFullScreenLoading()
  let statusCode = error.status;
  // 如果状态没有授权， 那么跳转到登陆页面
  switch (statusCode) {
    case 401:
      vm.$router.push('/login')
      break;
    case 500:
      tipMsg('系统错误')
      break;
    case 404:
      vm.$router.push('/404')
      break;
  }
})

function tipMsg(msg) {
  Message({
    message: msg,
    type: 'error'
  })
}

/**
 * get请求
 * @param {url} 请求地址
 * @param {params} 请求参数
 */
export function get(url, params) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params: params
    }).then(res => {
      resolve(res)
    }).catch(err => {
      reject(err)
    })
  })
}

export function deletes(url, params) {
  return new Promise(((resolve, reject) => {
    axios.delete(url, {
      params: params
    }).then(res => {
      resolve(res)
    }).catch(err => {
      reject(err)
    })
  }))
}

/**
 * post请求
 * @param {url} 请求地址
 * @param {params} 请求参数体
 */
export function post(url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params).then(res => {
      console.log(res)
      resolve(res);
    }).catch(err => {
      console.log(err)
      Message({
        message: '系统错误',
        type: 'error'
      })
      reject(err);
    })
  })
}
