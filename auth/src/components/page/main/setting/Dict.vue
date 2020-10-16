<template>
  <div>
    <el-form :inline="true" :model="conditionDict" class="">
      <el-form-item label="字典名称">
        <el-input  v-model="conditionDict.dictName" size="small"
                  placeholder="字典名称"></el-input>
      </el-form-item>
      <el-form-item label="字典码值">
        <el-input  v-model="conditionDict.dictCode" size="small" placeholder="字典码值"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="dictSearch" size="small">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-refresh-left" @click="dictReset" size="small">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-refresh" @click="dictRefresh" size="small">刷新</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="openDictDialog" size="small">添加字典</el-button>
      </el-form-item>
    </el-form>
    <tab-page
      :col-list="colList"
      :pager="pager"
      @changeCurrentPage="changeCurrentPage"
      @changeSize="changeSize"
      >
      <el-table-column
        fixed="right"
        label="操作"
        width="280px"
        align="center"
      >
        <template slot-scope="scope">
          <el-button @click="findDictItem(scope.row)" icon="el-icon-edit-outline" type="primary" size="small">查看</el-button>
          <el-button @click="editDict(scope.row)" icon="el-icon-edit-outline" type="warning" size="small">编辑</el-button>
          <el-button @click="delDict(scope.row)" icon="el-icon-delete" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </tab-page>
  </div>
</template>

<script>
  import {getDictPage} from "../../../../lib/api/dict";

  export default {
		name: "Dict",
    data () {
		  return {
        conditionDict: {
          page: '0',
          size: '15',
          dictName: '',
          dictCode: ''
        },
		    pager: {},
        colList: [
          {
            prop: 'id',
            label: 'ID',
            type: 'selection'
          },
          {
            prop: 'dictName',
            label: '字典名称',
          },
          {
            prop: 'description',
            label: '描述'
          },
          {
            prop: 'delFlag',
            label: '状态'
          },
          {
            prop: 'createBy',
            label: '创建人'
          },
          {
            prop: 'createTime',
            label: '创建时间'
          }
        ],
      }
    },
    mounted() {
		  this.getPage()
    },
    methods: {
		  changeCurrentPage (page) {

      },
      changeSize (size) {

      },
      getPage() {
		    getDictPage('/dict/', this.conditionDict).then((res) => {
		      this.pager = res.data.data
        })
      },
      dictSearch () {
		    if (!this.conditionDict.dictName && !this.conditionDict.dictCode) {
		      return
        }
		    this.getPage()
      },
      dictReset () {
		    this.conditionDict.dictCode = ''
        this.conditionDict.dictName = ''
      },
      dictRefresh () {
		    this.getPage()
      },
      openDictDialog () {

      },
      findDictItem (row) {

      },
      editDict (row) {

      },
      delDict (row) {

      }
    }
	}
</script>

<style scoped>

</style>
