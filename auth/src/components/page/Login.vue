<template>
  <div class="bg-body">
    <div class="login-container">
      <div class="login login-left">
        <h1>Auth Web</h1>
        <p>介绍啊吧啊吧介绍啊吧啊吧介绍啊吧啊吧介绍啊<br></p>
        <p>吧啊吧介绍啊吧啊吧介绍啊吧啊吧介绍啊</p>
        <p>
          吧啊吧介绍啊吧啊吧介绍啊吧啊吧
        </p>
        <!--          <div class="login-js-bottom">-->
        <!--              <h4 class="h4-js">-->
        <!--                 Auth Web 认证登录-->
        <!--              </h4>-->
        <!--          </div>-->
      </div>
      <div class="login login-right">
        <div class="login-head">
          <h1>登录</h1>
          <a href="javascript:(0);">忘记密码？</a>
        </div>
        <el-form :rules="rules" :model="loginUser" ref="loginUser">
          <div class="login-body">
            <div class="login-input-div">
              <el-form-item prop="userName">
                <el-input
                  placeholder="请输入用户名"
                  prefix-icon="el-icon-user" type="text"
                  v-model="loginUser.userName">
                </el-input>
              </el-form-item>
            </div>
            <div class="login-input-div">
              <el-form-item prop="password">
                <el-input
                  placeholder="请输入密码"
                  prefix-icon="el-icon-view" type="password"
                  v-model="loginUser.password">
                </el-input>
              </el-form-item>
            </div>
            <div class="login-input-div login-input-verify">
              <img v-show="isShow" class="verify-img" @click="getCaptImg()" v-bind:src="codeSrc" alt="">
              <el-form-item prop="code">
                <el-input
                  placeholder="验证码"
                  type="text"
                  v-model="loginUser.code">
                </el-input>
              </el-form-item>
            </div>
            <div class="login-input-div login-btn">
              <el-button type="primary" @click="loginEvent('loginUser')" size="small">登录</el-button>
            </div>
            <div class="login-input-div login-icon">
              <p class="login-other">其他方式登录</p>
              <div>
                <i class="iconfont iconqq"></i>
              </div>
              <div>
                <i class="iconfont iconweixin"></i>
              </div>
              <div>
                <i class="iconfont icongitee-fill-round"></i>
              </div>
              <div>
                <i class="iconfont iconGitHub"></i>
              </div>
            </div>
          </div>
        </el-form>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import {login} from '../../lib/api/login'
  import QS from 'qs'
  import {getRouters} from "../../utils/router-utils";

  export default {
    name: 'Login',
    data() {
      return {
        rules: {
          userName: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 32, message: '长度在 3 ~ 32 之间', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 3, max: 32, message: '长度在 3 ~ 32 之间', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '请输入验证吗', trigger: 'blur'},
            {min: 4, max: 4, message: '请输入正确的验证码', trigger: 'blur'}
          ]
        },
        msg: 'heeh',
        loginUser: {
          userName: '',
          password: '',
          code: '',
        },
        isShow: true,
        codeSrc: 'http://localhost:9090/verify/captchaImage',
        capt: {}
      }
    },
    methods: {

      /**
       * 点击事件  切换验证码图片
       *
       * */
      getCaptImg: function () {
        this.codeSrc = 'http://localhost:9090/verify/captchaImage?' + new Date().getTime()
      },
      /**
       * 登录事件
       * @param formName
       */
      loginEvent(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            login(QS.stringify(this.loginUser)).then(res => {
              if (res.data.status === 0) {
                let data = res.data.data;
                this.actSetUserDetails(data.userDetails)
                this.actSetToken(data.token)
                this.actSetPerms(data.perms)
                this.actSetIsLogin(true)
                this.actSetMenus(data.menus)
                /**
                 * 根据后台传来的menus 生成对应的 动态路由数组
                 * @param menus
                 */
                getRouters();
                this.$router.push('/analyzer')
              } else {
                this.getCaptImg()
              }
            })
          } else {
            return false;
          }
        });
      },

      ...mapActions('UserDetails', [
        'actSetUserDetails',
        'actSetPerms',
        'actSetToken',
        'actSetIsLogin',
        'actSetMenus',
      ]),
      ...mapActions('Tabs', [])
    }
  }
</script>

<style scoped>
  .bg-body {
    background-color: #42b983;
    background-repeat: no-repeat;
    background-size: 100%;
    height: calc(100vh);
    width: 100%;
  }

  .login-container {
    display: flex;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 800px;
    height: 500px;
    background-color: rgba(255, 255, 255, 0.9);
    box-shadow: 0 0 35px #555555;
  }

  .login {
    position: relative;
    width: 50%;
    height: 100%;
  }

  .login-left {
    background-color: #555555;
    box-sizing: border-box;
    padding: 70px 50px;
  }

  .login-left > h1 {
    color: aliceblue;
    font-size: 26px;
  }

  .login-left > p {
    color: aliceblue;
    font-size: 16px;
    line-height: 24px;
    margin-top: 10px;
  }

  .login-js-bottom {
    width: 100%;
    position: absolute;
    bottom: 0;
    border-top: 1px solid #ffffff;
  }

  .login-right {
    box-sizing: border-box;
    padding: 70px 50px;
  }

  .login-right > .login-head {
    display: flex;
  }

  .login-right > .login-head > h1 {
    font-size: 24px;
    font-weight: bold;
    color: #555555;
  }

  .login-right > .login-head > a {
    color: cornflowerblue;
    vertical-align: bottom;
    padding-top: 10px;
    margin-left: auto;
  }

  .login-body {
    margin-top: 30px;
  }

  .login-input-div {
    margin-bottom: 30px;
  }

  .verify-img {
    width: 150px;
    height: 40px;
    margin-right: 10px;
    cursor: pointer;
  }

  .login-input-verify {
    display: flex;
  }

  .login-btn >>> .el-button {
    width: 100%;
    height: 40px;
    background-color: #42b983;
    border: none;
    color: white;
    font-size: 14px;
  }

  .login-icon {
    display: flex;
    padding-top: 20px;
    position: relative;
  }

  .login-other {
    position: absolute;
    top: -20px;
    left: 50%;
    font-size: 16px;
    width: 50%;
    text-align: center;
    transform: translateX(-50%);
  }

  .login-icon > div {
    flex: 1;
    text-align: center;
  }

  .login-icon > div > i {
    cursor: pointer;
    font-size: 26px;
    border-radius: 50%;
  }

  .login-icon > div > i:hover {
    box-shadow: 0 0 3px #555555;
  }
</style>
