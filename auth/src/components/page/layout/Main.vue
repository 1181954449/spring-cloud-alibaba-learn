<template>
    <el-main>
        <Tags></Tags>
        <div class="container">
            <router-view></router-view>
        </div>
    </el-main>
</template>

<script>
    import {mapActions} from 'vuex'
    import Tags from "./Tags";
	export default {
		name: "Main",
		components: {Tags},
		methods: {
	        ...mapActions('Tabs', [
		        'actSetCurrentTab'
	        ])
        },
        computed: {
			tabs () {
				return this.$store.getters["Tabs/getTabs"]
            },
            activeName () {
                return this.$store.getters["Tabs/getCurrentTabName"]
            }
        }
	}
</script>

<style scoped>
    .el-main {
        padding: 0px;
    }
    .container {
        padding: 20px;
    }
</style>
