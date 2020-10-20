<template>
  <el-form-item :size="size" :prop="rule" :label="dict.dictName + ':'">
    <el-radio-group v-model="val">
      <el-radio v-for="item in items" :label="item.itemValue" :key="item.itemValue"> {{ item.itemText }} </el-radio>
    </el-radio-group>
  </el-form-item>
</template>

<script>
  import {getDictItemsByDictId} from "../../../lib/api/dict";

  export default {
    name: "ComRadioGroup",
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
      }
    },
    data () {
      return {
        dict: {},
        items: [],
        val: ''
      }
    },
    mounted() {
      this.getDictItemsKeyVal()
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
      val(newVal, oldVal){
        if (newVal != oldVal) {
          this.$emit('input', this.val)
        }
      }
    }
  }
</script>

<style scoped>

</style>
