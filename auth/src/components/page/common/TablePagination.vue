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
    methods: {
      handleSizeChange(val) {
        this.$emit('changeSize', val)
      },
      handleCurrentChange(val, newCurrentVal) {
        this.$emit('changeCurrentPage', val, newCurrentVal)
      }
    }
  }
</script>

<style scoped>

</style>
