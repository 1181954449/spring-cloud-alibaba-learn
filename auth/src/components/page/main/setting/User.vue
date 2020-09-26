<template>
    <div>
        <tab-page
                :col-list="colList"
                :table-list="[]"
                :pager="pager"
                @handleSizeChange="changeSize"
                @handleCurrentChange="changeCurrentPage">

        </tab-page>
    </div>
</template>

<script>
    import {getUserList} from "../../../../lib/api/user";

    export default {
		name: "User",
        data () {
			return {
				tableData: [],
                colList: [
                    {
                        prop: 'userId',
                        label: '用户ID',
                        show: true,
                    },
                    {
                    	prop: 'username',
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
			getUserList (page, size) {
                getUserList("/user/", {
                	page: page,
                    size: size
                }).then((res) => {
                	this.pager = res
                    console.log(this.pager)
                })
            },
	        changeSize(size){
		        console.log(size);
	        },
	        changeCurrentPage(page) {
		        console.log(page);
	        }
        },
        mounted() {
			this.getUserList(0, 15)
        }
    }
</script>

<style scoped>

</style>
