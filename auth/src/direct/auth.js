import { checked } from '../utils/auth'

function install(Vue, options = {}) {
	Vue.directive(options.name || 'auth', {
		inserted(el, binding) {
			if (!checked(binding.value)) {
				el.parentNode && el.parentNode.removeChild(el)
			}
		}
	})
}

export default {
	install
}
