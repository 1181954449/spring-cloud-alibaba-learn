<template>
  <div>
    <el-table
      :data="pager.list"
      stripe
      style="width: 100%">
      <template v-for="(item, index) in colList">
        <el-table-column
          :key="item.prop"
          v-if="!item.show"
          :prop="item.prop"
          :label="item.label"
          :align="item.align?item.align : 'center'"
          :width="item.width">
        </el-table-column>
      </template>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="text-align: left;margin: 20px 0"
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
      handleCurrentChange(val) {
        this.$emit('changeCurrentPage', val)
      }
    }
  }
</script>

<style scoped>

</style>
