<template>
  <div class="tag-container">
    <template v-for="(n, index) in tabs">
      <el-tag class="active-tag"
              :disable-transitions="false"
              :key="n.path" @close="closeTag(n, index)"
              :closable="n.id !== '99999'" v-if="n.active"
              color="#42b983"> {{ n.title }}
      </el-tag>
      <el-tag class="not-active-tag"
              :disable-transitions="false"
              :key="n.path" @close="closeTag(n, index)"
              :closable="n.id !== '99999'" v-else-if="!n.active"
              @click="setCurrentTag(n.path)"
              color="azure"> {{ n.title }}
      </el-tag>
    </template>
  </div>
</template>

<script>
  import {mapGetters, mapActions, mapMutations} from 'vuex'

  export default {
    name: "Tags",
    data() {
      return {}
    },
    mounted() {
      this.setCurrentTagByPath(this.$route.path)
    },
    methods: {
      closeTag(tag, index) {
        this.actDelTab(index)
        let currentTab = this.$store.getters["Tabs/getCurrentTab"]
        if (currentTab.path === this.$route.fullPath) {
          return;
        }
        this.$router.push(currentTab.path)
      },
      setCurrentTag(path) {
        this.$router.push(path)
      },
      setCurrentTagByPath(path) {

        let menuPath = this.getMenuByPath()
        let menu = menuPath(path)
        if (menu != null) {
          this.actAddTabs({
            'id': menu.menuId,
            'path': menu.path,
            'title': menu.menuName,
            'active': true,
            'name': menu.name
          })
        }
      },
      ...mapActions('Tabs', [
        'actDelTab',
        'actSetCurrentTab',
        'actAddTabs'
      ]),
      ...mapGetters('UserDetails', [
        'getMenuByPath'
      ])
    },
    computed: {
      tabs() {
        return this.$store.getters["Tabs/getTabs"]
      }
    },
    watch: {
      $route(to, from) {
        this.setCurrentTagByPath(to.path)
      },
    }
  }
</script>

<style scoped>
  .tag-container {
    padding: 3px;
    display: flex;
    overflow-x: scroll;
    box-sizing: border-box;
  }

  .tag-container >>> .el-tag {
    margin-right: 10px;
  }

  .active-tag {
    color: white;
    cursor: pointer;
  }

  .not-active-tag {
    color: #555555;
    cursor: pointer;
  }

  .tag-container::-webkit-scrollbar {
    width: 10px;
    height: 10px;
    background-color: #B3C0D1;
  }

  .tag-container::-webkit-scrollbar-thumb {
    border-radius: 5px;
    background-color: #42b983;
  }
</style>
