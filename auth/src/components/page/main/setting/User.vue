<template>
  <div>
    <el-form :inline="true" :model="userCondition" class="demo-form-inline">
      <el-form-item size="small" label="手机号">
        <el-input prefix-icon="el-icon-mobile-phone" v-model="userCondition.phonenumber" size="small"
                  placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item size="small" label="用户名">
        <el-input prefix-icon="el-icon-user" v-model="userCondition.userName" size="small" placeholder="用户名"></el-input>
      </el-form-item>
      <com-box :dict-obj="dictObj" v-model="userCondition.deptId"></com-box>

      <el-form-item size="small">
        <el-button type="primary" icon="el-icon-search" @click="userSearch" size="small">查询</el-button>
      </el-form-item>
      <el-form-item size="small">
        <el-button type="primary" icon="el-icon-refresh-left" @click="userReset" size="small">重置</el-button>
      </el-form-item>
      <el-form-item size="small">
        <el-button type="primary" icon="el-icon-refresh" @click="userRefresh" size="small">刷新</el-button>
      </el-form-item>
      <el-form-item size="small">
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
          <com-radio-group dict-id="jlkjaklsjdlkjlkj" rule="sex" v-model="userInfo.sex"></com-radio-group>
          <el-form-item size="small" label="密码:" prop="password">
            <el-input prefix-icon="el-icon-view" type="password" v-model="userInfo.password"></el-input>
          </el-form-item>
           <el-form-item size="small" label="重复密码:" prop="repeatPassword">
            <el-input prefix-icon="el-icon-view" type="password" v-model="userInfo.repeatPassword"></el-input>
          </el-form-item>
          <com-box :dict-obj="dictObj"
                   v-model="userInfo.deptId"
                   :rule="'deptId'"></com-box>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel()">取 消</el-button>
        <el-button type="primary" size="small" @click="save()">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
  import {getUserList, deleteUserById, getDeptList, addUser} from "../../../../lib/api/user";

  import {phonenumberValidate, emailValidate} from '../../../../utils/data_validate_rules'

  import {getDictItems} from "../../../../lib/api/dict";

  export default {
    name: "User",
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.userInfo.repeatPassword !== '') {
            this.$refs.userInfo.validateField('repeatPassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.userInfo.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        userRules: {
          nickName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
            {min: 3, max: 16, message: '姓名长度不符合规则 3 ~ 16', trigger: 'blur'}
          ],
          userName: [
            {required: true, message: '登录名不能为空', trigger: 'blur'},
            {min: 3, max: 16, message: '登录名长度不符合规则 6 ~ 16', trigger: 'blur'}
          ],
          phonenumber: [
            {validator: phonenumberValidate, trigger: 'blur'},
            {required: true, message: '手机号码不能为空', trigger: 'blur'},
          ],
          email: [
            {validator: emailValidate, trigger: 'blur'},
            {required: true, message: '邮箱账号不能为空', trigger: 'blur'},
          ],
          password: [
            {validator: validatePass, trigger: 'blur'},
            {required: true, message: '密码不能为空', trigger: 'blur'},
          ],
          repeatPassword: [
            {validator: validatePass2, trigger: 'blur'},
            {required: true, message: '重复密码不能为空', trigger: 'blur'},
          ],
          deptId: [
            {required: true, message: '请分配部门', trigger: 'change' }
          ],
          sex: [
            {required: true, message: '请选择性别', trigger: 'change' }
          ]
        },
        tableData: [],
        editUserDialog: false,
        dictObj: {
          dict: {
            dictName: ''
          },
          items: []
        },
        userInfo: {
          nickName: '',
          userName: '',
          phonenumber: '',
          sex: '',
          password: '',
          repeatPassword: '',
          deptId: ''
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
            width: '70',
            dict: 'jlkjaklsjdlkjlkj'
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
        getUserList(this.userCondition).then((res) => {
          this.pager = res.data.data
        })
      },
      getDeptOptions() {
        getDeptList().then((res) => {
          let list = res.data.data
          let deptList = list.map(e => {
            return {
              itemValue: e.deptId,
              itemText: e.detpName,
            }
          })
          let deptLable = {
            dictName: '部门',
          }
          this.dictObj.dict = deptLable
          this.dictObj.items = deptList
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
          deleteUserById({
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
        this.getUserList();
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
        this.getUserList();
      },
      save() {
        this.$refs['userInfo'].validate((valid) => {
          if (valid) {
            addUser(this.userInfo).then((res) => {
              console.log(res)
            })
          } else {
            return false
          }
        })
      },
      handleClose() {
        this.editUserDialog = false
        if (this.$refs['userInfo']!==undefined) {
          this.$refs['userInfo'].resetFields();
        }
      },
      cancel () {
        this.handleClose()
      }
    },
    created() {
      getDictItems('jlkjaklsjdlkjlkj')
      this.getDeptOptions()
    },
    mounted() {
      this.getUserList()
    },
  }
</script>

<style scoped>

</style>
