<template>
        <el-menu default-active="1-4-1"
                 background-color="#545c64"
                 text-color="#fff"
                 active-text-color="#42b983"
                 class="el-menu-vertical-auth"
                 :collapse="isMenu"
                 @open="handleOpen"
                 @close="handleClose"
                 :default-active="defaultActive"
                >
            <el-submenu  :index="menu.menuId.toString()"  v-auth="Array.of('sys:setting:m')" v-for="menu in menus">
                <template slot="title">
                    <i :class="menu.icon"></i>
                    <span slot="title">{{ menu.menuName }}</span>
                </template>
                <el-menu-item-group>
                    <router-link v-auth="Array.of(menu.perms)" :to="subMenu.path" v-for="subMenu in menu.children">
                        <el-menu-item  :index="subMenu.path" :key="subMenu.menuId.toString()" >
                            <i :class="subMenu.icon"></i>
                            {{ subMenu.menuName }}
                        </el-menu-item>
                    </router-link>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
</template>

<script>
	import { mapActions} from 'vuex'
	export default {
		name: 'Aside',
		data() {
			return {
				isCollapse: false
			}
		},
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath)
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath)
			},
            ...mapActions('Tabs', [
                'actDelTab',
                'actSetCurrentTab',
            ])
		},
		computed: {
			isMenu() {
				return this.$store.getters["Menu/isShow"]
			},
            menus () {
				return this.$store.getters["UserDetails/getMenus"]
            },
            defaultActive () {
				return this.$route.path
            }
		}
	}
</script>

<style scoped>
    .el-aside {
        scrollbar-width: none; /* firefox */
        -ms-overflow-style: none; /* IE 10+ */
        overflow-x: hidden;
        overflow-y: auto;
        max-width: 200px;
    }
    .el-menu-vertical-auth {
        height: calc(100vh - 60px);
    }

    .el-menu-vertical-auth:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
    }
</style>
