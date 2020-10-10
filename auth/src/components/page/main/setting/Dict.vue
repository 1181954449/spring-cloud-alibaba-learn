<template>
  <div>
    <tab-page
      :col-list="colList"
      :pager="pager"
      @changeCurrentPage="changeCurrentPage"
      @changeSize="changeSize"
      >

    </tab-page>
  </div>
</template>

<script>
	export default {
		name: "Dict",
    data () {
		  return {
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
          }
        ],
      }
    },
    methods: {
		  changeCurrentPage (page) {

      },
      changeSize (size) {

      }
    }
	}
</script>

<style scoped>

</style>
