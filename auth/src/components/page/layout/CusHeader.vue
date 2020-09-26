<template>
    <el-header class="auth-header">
        <div class="logo">
            <h1>Auth WEB</h1>
            <i class="el-icon-s-unfold icon-is-show " v-if="isMenu" @click="showMenu()"></i>
            <i class="el-icon-s-fold icon-is-show" v-if="!isMenu" @click="showMenu()"></i>
        </div>
        <div class="info">
            <i class="el-icon-message-solid" title="通知">
                <el-badge is-dot class="item">
                </el-badge>
            </i>
            <i class="el-icon-s-comment" title="消息">
                <el-badge :value="msgCount" class="item msg-count">
                </el-badge>
            </i>
            <el-popover
                    ref="imgInfo"
                    placement="bottom"
                    width="200"
                    trigger="click"
                    >
                <ul class="user-detatils-ul">
                    <li>
                        <i class="el-icon-user"></i>
                        <span class="user-details-val"> {{ userDetails.nickName }} </span>
                    </li>
                    <li>
                        <i class="el-icon-phone"></i>
                        <span class="user-details-val"> {{ userDetails.phonenumber }} </span>
                    </li>
                    <li>
                        <i class="el-icon-setting"></i>
                        <span class="user-details-val"> 设置 </span>
                    </li>
                    <li @click="logout">
                        <i class="el-icon-switch-button"></i>
                        <span class="user-details-val"> 退出登录 </span>
                    </li>
                </ul>
            </el-popover>
            <img v-popover:imgInfo class="head-img" title="头像" :src="userDetails.avatar" alt="">
        </div>
    </el-header>
</template>

<script>
	import {mapGetters, mapActions, mapMutations, mapState} from 'vuex'

	export default {
		name: "CusHeader",

		data() {
			return {
				msgCount: 10,
				userDetails: {}
			}
		},
		mounted() {
			this.userDetails = this.$store.getters["UserDetails/getUserDetails"]
		},
		methods: {
			showMenu: function () {
				let isShow = this.$store.getters["Menu/isShow"]
				if (isShow) {
					this.$store.dispatch('Menu/hideMenu')
				} else {
					this.$store.dispatch('Menu/showMenu')
				}
			},
            logout () {
	            this.$confirm('您确定需要退出登录吗？, 是否继续?', '提示', {
		            confirmButtonText: '确定',
		            cancelButtonText: '取消',
		            type: 'warning'
	            }).then(() => {
		            this.actSetIsLogin(false)
		            this.actSetPerms([])
		            this.actSetToken("")
		            this.actSetUserDetails({})
                    this.actSetMenus([])
                    this.actClearTab()
		            this.$router.push("/login")
		            this.$message({
			            type: 'success',
			            message: '成功退出登录!'
		            });
	            }).catch(() => {
		            this.$message({
			            type: 'info',
			            message: '已取消退出登录'
		            });
	            });

            },
			...mapGetters({
				getUserDetails(state) {
					return state.UserDetails.userDetails;
				}
			}),
			...mapActions('UserDetails', [
				'actSetUserDetails',
				'actSetPerms',
				'actSetToken',
				'actSetIsLogin',
                'actSetMenus'
			]),
            ...mapActions('Tabs', [
            	'actClearTab'
            ])
		},
		computed: {
			isMenu() {
				return this.$store.getters["Menu/isShow"]
			}
		}
	}
</script>

<style scoped>
    .auth-header {
        width: 100%;
        padding: 0px;
        background-color: #42b983;
        display: flex;
    }

    .logo {
        display: flex;
        width: 200px;
        color: white;
    }

    .logo > h1 {
        font-weight: bold;
        font-size: 24px;
        line-height: 60px;
        text-align: center;
        width: 100%;
    }

    .logo > .icon-is-show {
        font-size: 36px;
        color: white;
        line-height: 60px;
        margin-right: 5px;
        cursor: pointer;
    }

    .info {
        margin-left: auto;
        padding-right: 30px;
        line-height: 60px;
        font-size: 22px;
        color: #555555;
    }

    .info i, .info img {
        cursor: pointer;
    }

    .info > i > .item {
        margin-top: -10px;
        margin-right: 20px;
    }

    .info > i > .msg-count {
        height: 13px;
        line-height: 13px;
        font-size: 9px;
        margin-top: -20px;
    }

    .head-img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        vertical-align: sub;
    }
    .user-detatils-ul > li {
        cursor: pointer;
        padding-bottom: 10px;
    }
    .user-detatils-ul > li:hover {
        color: #42b983;
    }
    .user-details-val {
        margin-left: 28px;
    }
</style>
