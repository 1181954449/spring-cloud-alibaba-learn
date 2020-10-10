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
      <el-form-item label="部门">
        <el-select size="small" filterable v-model="userCondition.deptId" placeholder="请选择">
          <el-option
            v-for="dept in deptList"
            :key="dept.deptId"
            :label="dept.detpName"
            :value="dept.deptId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="userSearch" size="small">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-refresh-left" @click="userReset" size="small">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-refresh" @click="userRefresh" size="small">刷新</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="openUserDialog" size="small">添加用户</el-button>
      </el-form-item>
    </el-form>
    <tab-page
      :col-list="colList"
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
      title="用户信息"
      :visible.sync="editUserDialog"
      width="30%"
      :before-close="handleClose">
      <span>
        <el-form ref="userInfo" :rules="userRules" :model="userInfo" label-width="100px">
          <el-form-item size="small" label="用户姓名:" prop="nickName">
            <el-input prefix-icon="el-icon-female" v-model="userInfo.nickName"></el-input>
          </el-form-item>
          <el-form-item size="small" label="登录名:" prop="userName">
            <el-input prefix-icon="el-icon-user" v-model="userInfo.userName"></el-input>
          </el-form-item>
          <el-form-item size="small" label="手机号码:" prop="phonenumber">
            <el-input prefix-icon="el-icon-mobile-phone" v-model="userInfo.phonenumber"></el-input>
          </el-form-item>
          <el-form-item size="small" label="邮箱:" prop="email">
            <el-input prefix-icon="el-icon-message" v-model="userInfo.email"></el-input>
          </el-form-item>
          <el-form-item label="性别:">
            <el-radio-group v-model="userInfo.sex">
              <el-radio label="0">男</el-radio>
              <el-radio label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="editUserDialog = false">取 消</el-button>
        <el-button type="primary" size="small" @click="save()">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
  import {getUserList, deleteUserById, getDeptList} from "../../../../lib/api/user";

  import {phonenumberValidate, emailValidate} from '../../../../utils/data_validate_rules'

  export default {
    name: "User",
    data() {
      return {
        userRules: {
          nickName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
            {min: 3, max: 16, message: '姓名长度不符合规则 3 ~ 16', trigger: 'blur'}
          ],
          userName: [
            {required: true, message: '登录名不能为空', trigger: 'blur'},
            {min: 3, max: 16, message: '登录名长度不符合规则 3 ~ 16', trigger: 'blur'}
          ],
          phonenumber: [
            {validator: phonenumberValidate, trigger: 'blur'}
          ],
          email: [
            {validator: emailValidate, trigger: 'blur'}
          ]
        },
        tableData: [],
        editUserDialog: false,
        deptList: [],
        userInfo: {
          nickName: '',
          userName: '',
          phonenumber: '',
          sex: '0'
        },
        userCondition: {
          phonenumber: '',
          userName: '',
          page: 0,
          size: 15,
          deptId: ''
        },
        colList: [
          {
            prop: 'userId',
            label: '用户ID',
            type: 'selection'
          },
          {
            prop: 'nickName',
            label: '姓名',
            width: '180'
          },
          {
            prop: 'userName',
            label: '登录名',
            width: '100'
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
            label: '性别',
            width: '70'
          },
          {
            prop: 'deptId',
            label: '部门'
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
          this.pager = res.data.data
        })
      },
      getDeptOptions() {
        getDeptList('/dept/list').then((res) => {
          this.deptList = res.data.data
        })
      },
      changeSize(size) {
        this.userCondition.size = size;
        this.getUserList();
      },
      changeCurrentPage(val) {
        this.userCondition.page = val
        this.getUserList()
      },
      findUser(row) {
        this.dialogVisible = true
      },
      editUser(row) {

      },
      delUser(row) {
        this.$confirm('您确定要删除此用户吗？请选择', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          deleteUserById('/user/delete', {
            id: row.userId
          }).then(res => {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.getUserList()
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
        if (this.userCondition.userName || this.userCondition.phonenumber || this.userCondition.deptId) {
          this.getUserList();
        }
      },
      userRefresh() {
        this.getUserList();
      },
      openUserDialog() {
        this.editUserDialog = true
      },
      userReset () {
        this.userCondition.userName = '';
        this.userCondition.phonenumber ='';
        this.userCondition.deptId = '';
      },
      save() {
        this.$refs['userInfo'].validate((valid) => {
          if (valid) {

          } else {
            return false
          }
        })
      },
      handleClose() {
      }
    },
    mounted() {
      this.getUserList()
      this.getDeptOptions()
    }
  }
</script>

<style scoped>

</style>
