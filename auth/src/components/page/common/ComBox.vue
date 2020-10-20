<template>
  <el-form-item :size="size" :label="dictId?dict.dictName+':':dictObj.dict.dictName + ':'" :prop="rule">
    <el-select :size="size" v-model="val" placeholder="请选择">
      <el-option v-for="item in items"
                 :key="item.itemValue"
                 :label="item.itemText"
                 :value="item.itemValue">

      </el-option>
    </el-select>
  </el-form-item>
</template>

<script>
  import {getDictItemsByDictId} from "../../../lib/api/dict";

  export default {
    name: "ComBox",
    props: {
      dictId: {
        type: String,
        default: ''
      },
      size: {
        type: String,
        default: 'small'
      },
      rule: {
        type: String,
        default: ''
      },
      dictObj: {
        type: Object,
        default: {}
      },
    },
    data() {
      return {
        dict: {},
        items: [],
        val: ''
      }
    },
    mounted() {
      if (!this.dictId) {
        this.items = this.dictObj.items
      } else {
        this.getDictItemsKeyVal()
      }
    },
    methods: {
      getDictItemsKeyVal() {
        getDictItemsByDictId(this.dictId).then((res) => {
          let data = res.data.data
          this.dict = data.dict
          this.items = data.items
        })
      }
    },
    watch: {
      val(newVal, oldVal) {
        if (newVal != oldVal) {
          this.$emit('input', this.val)
        }
      },
      dictObj: {
        deep: true,
        handler: function (newVal, oldVal) {
          if (!this.dictId) {
            this.items = newVal.items
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>
