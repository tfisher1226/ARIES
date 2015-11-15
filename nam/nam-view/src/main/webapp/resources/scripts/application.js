var administrationView = "administrationView"; 
var organizationView = "membershipView"; 


function showPublicPage(source) {
	showPage(source, '/public.seam');
}

function showPage(source, page) {
	setCursorWait(source, page);
	var url = '#{facesContext.externalContext.requestContextPath}' + page;
	//alert(url);
	window.location.href = url;
}

