<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>
	export default {
		name: 'App',
		data() {
			return {
		        loading: ''
            }
		},
		beforeCreate() {

		},
        mounted() {
         document.getElementById("loading").remove()
        }
	}
</script>
<style>
</style>
