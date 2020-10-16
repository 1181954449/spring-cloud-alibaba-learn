<template>
  <div>
    <el-table
      :data="pager.list"
      stripe
      :size="pager.sz ? pager.sz : 'small'"
      border
      :height="pager.height?pager.height : 550"
      style="width: 100%">
      <template v-for="(item, index) in colList">
        <el-table-column
          :type="item.type?item.type:''"
          :key="item.prop"
          v-if="!item.show"
          :prop="item.prop"
          :label="item.label"
          :class-name="item.dict"
          :formatter="dictHandler"
          :align="item.align?item.align : 'center'"
          :width="item.width">
        </el-table-column>
      </template>
      <slot></slot>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="text-align: right;margin: 20px 0"
      background
      :current-page="pager.pageNum"
      :page-size="pager.pageSize"
      :page-sizes="[10,20,30,50]"
      :total="pager.total"
      prev-text="上一页"
      next-text="下一页"
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<script>
  import {getDictItems} from "../../../lib/api/dict";

  export default {
    name: "TablePagination",
    props: {
      // 表头中的数据
      colList: {
        type: Array,
        default: () => []
      },
      pager: {
        type: Object,
        default: () => {
        }
      }
    },
    data () {
      return {
        dicts: {}
      }
    },
    methods: {
      handleSizeChange(val) {
        this.$emit('changeSize', val)
      },
      handleCurrentChange(val, newCurrentVal) {
        this.$emit('changeCurrentPage', val, newCurrentVal)
      },
      dictHandler(row, column, cellValue, index) {
        // 使用column 作为 字典得key 获取集合。 用来渲染时。 映射字典中得value
        let dict = column.className
        if (!column.className) {
          return cellValue;
        }
        if (!this.dicts[dict]) {
          this.getDictItemsPage(dict)
        }
        // while (!this.dicts[dict]) {}
        let itemValue = row[column.property]
        // 临时变量保存 字典集合
        let dictTmp =  this.dicts[dict];
        for (let i = 0; i < dictTmp.length; i++) {
          if (dictTmp[i][itemValue]) {
            return dictTmp[i][itemValue];
          }
        }
        return cellValue;
      },
      async getDictItemsPage(dict) {
        await getDictItems('/dict/item/' + dict).then((res) => {
          this.dicts[dict] = res.data.data
          console.log(this.dicts)
        })
      }
    }
  }
</script>

<style scoped>

</style>
