const title ="Auth WEB"

export default function getPageTitle(pageTitle) {
	if (pageTitle) {
		return `${pageTitle} - ${title}`
	} else {
		return `${title}`
	}
}
