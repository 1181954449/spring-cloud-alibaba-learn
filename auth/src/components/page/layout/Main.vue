<template>
  <el-main>
    <Tags class="cus-tags"></Tags>
    <div style="padding: 10px" id="default-loading">
      <el-breadcrumb class="menu-path" separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="b in breadcrumbs">{{ b }}</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="container" style="margin-top: 15px;">
        <router-view></router-view>
      </div>
    </div>
  </el-main>
</template>

<script>
  import {mapActions} from 'vuex'
  import Tags from "./Tags";

  export default {
    name: "Main",
    components: {Tags},
    data() {
      return {
        breadcrumbs: []
      }
    },
    mounted() {
      this.getCrumbs()
    },
    methods: {
      getCrumbs (to) {
        let toPath = to?to.path : this.$route.fullPath;
        let menus = this.$store.getters["UserDetails/getMenus"]
        this.breadcrumbs = []
        menus.forEach((e1) => {
          e1.children.forEach((e2) => {
            if (toPath === e2.path) {
              this.breadcrumbs.push(e1.menuName);
              this.breadcrumbs.push(e2.menuName);
            }
          })
        })
      },
      ...mapActions('Tabs', [
        'actSetCurrentTab'
      ])
    },
    watch: {
      $route(to, from) {
        this.getCrumbs(to)
      },
    },
    computed: {
      tabs() {
        return this.$store.getters["Tabs/getTabs"]
      },
      activeName() {
        return this.$store.getters["Tabs/getCurrentTabName"]
      }
    }
  }
</script>

<style scoped>
  .cus-tags {
    /*position: fixed;*/
  }

  .el-main {
    padding: 0px;
  }

  .container {
    background-color: white;
    padding: 30px;
    min-height: calc(100vh - 218px);
    border-radius: 5px;
  }

  #default-loading {
    background-color: #dedede;
    height: calc(100vh - 128px);
    max-height: calc(100vh - 128px);
    overflow-y: scroll;
  }

  /*滚动条样式*/
  #default-loading::-webkit-scrollbar { /*滚动条整体样式*/
    width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
    height: 6px;
  }

  #default-loading::-webkit-scrollbar-thumb { /*滚动条里面小方块*/
    border-radius: 3px;
    -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.5);
    background: rgba(0, 0, 0, 0.2);
    position: fixed;

  }

  .menu-path {
    margin-left: 20px;
    margin-top: 1px;
    /*margin-bottom: -5px;*/
  }

  #default-loading::-webkit-scrollbar-track { /*滚动条里面轨道*/
    -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.5);
    border-radius: 0;
    background: rgba(0, 0, 0, 0.1);
    position: fixed;
  }
</style>
