<template>
  <div>
    <el-form :inline="true" :model="userCondition" class="demo-form-inline">
      <el-form-item label="手机号">
        <el-input prefix-icon="el-icon-mobile-phone" v-model="userCondition.phonenumber" size="small"
                  placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input prefix-icon="el-icon-user" v-model="userCondition.userName" size="small" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="userSearch" size="small">查询</el-button>
      </el-form-item>
    </el-form>
    <tab-page
      :col-list="colList"
      :table-list="[]"
      :pager="pager"
      @changeCurrentPage="changeCurrentPage"
      @changeSize="changeSize">
      <el-table-column
        fixed="right"
        label="操作"
        width="280px"
        align="center"
      >
        <template slot-scope="scope">
          <el-button @click="findUser(scope.row)" icon="el-icon-view" type="success" size="small">查看</el-button>
          <el-button @click="editUser(scope.row)" icon="el-icon-edit-outline" type="warning" size="small">编辑</el-button>
          <el-button @click="delUser(scope.row)" icon="el-icon-delete" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </tab-page>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
  import {getUserList} from "../../../../lib/api/user";

  export default {
    name: "User",
    data() {
      return {
        tableData: [],
        dialogVisible: false,
        userCondition: {
          phonenumber: '',
          userName: '',
          page: 0,
          size: 15
        },
        colList: [
          {
            prop: 'userId',
            label: '用户ID',
            type: 'selection'
          },
          {
            prop: 'userName',
            label: '用户名',
          },
          {
            prop: 'phonenumber',
            label: '手机号码'
          },
          {
            prop: 'email',
            label: '邮箱'
          },
          {
            prop: 'sex',
            label: '性别'
          },
          {
            prop: 'deptId',
            label: '部门'
          },
          {
            prop: 'nickName',
            label: '昵称'
          },
          {
            prop: 'loginDate',
            label: '最后登录日期'
          }
        ],
        pager: {}
      }
    },
    methods: {
      getUserList() {
        getUserList("/user/", this.userCondition).then((res) => {
          this.pager = res
          console.log(this.pager)
        })
      },
      changeSize(size) {
        this.userCondition.size = size;
        this.getUserList();
      },
      changeCurrentPage(val) {
        console.log(val);
        this.userCondition.page = val
        this.getUserList()
      },
      findUser(row) {
        this.dialogVisible = true
      },
      editUser(row) {

      },
      delUser (row) {
        this.$confirm('您确定要删除此用户吗？请选择', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      },
      handlerUserSelect(val) {

      },
      userSearch() {
        if (this.userCondition.userName || this.userCondition.phonenumber) {
          this.getUserList();
        }
      },
      handleClose () {

      }
    },
    mounted() {
      this.getUserList(0, 15)
    }
  }
</script>

<style scoped>

</style>
