<template>
    <el-container class="is-vertical">
        <cus-header></cus-header>
        <el-container>
            <Aside></Aside>
            <Main></Main>
        </el-container>
    </el-container>
</template>

<script>
	import Aside from './layout/Aside'
    import CusHeader from './layout/CusHeader'
	import Main from './layout/Main'

	export default {
		name: 'index',
		components: {Aside, CusHeader, Main},
	}
</script>

<style scoped>
    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
    }

    .el-main {
        height: calc(100vh - 60px);
    }

    body > .el-container {
        margin-bottom: 40px;
    }


</style>
