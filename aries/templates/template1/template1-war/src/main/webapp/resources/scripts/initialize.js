var windowWidth = 0;
var windowHeight = 0;
var browserName = null;
var browserVersion = null;
var dialog = null;


window.onresize = function(event) {
	initializePage();
	initializeLayout();
};

window.onload = function(event) {
	initializePage(); 
};
	
function initializePage() {
	detectBrowser();
	initializeDimensions();
	initializeGlobals();
}

function detectBrowser() {
	if (jQuery.browser.mozilla)
		browserName = "mozilla";
	else if (jQuery.browser.msie)
		browserName = "msie";
	//TODO flag error
	//show(browserName);
	browserVersion = jQuery.browser.version;
}

function initializeDimensions() {
	if (document.body && document.body.offsetWidth) {
		windowWidth = document.body.offsetWidth;
		windowHeight = document.body.offsetHeight;
		//alert(windowHeight);
	}
	if (document.compatMode=='CSS1Compat' && document.documentElement && document.documentElement.offsetWidth) {
		windowWidth = document.documentElement.offsetWidth;
		windowHeight = document.documentElement.offsetHeight;
		//alert(windowHeight);
	}
	if (window.innerWidth && window.innerHeight) {
		windowWidth = window.innerWidth;
		windowHeight = window.innerHeight;
		//alert(windowHeight);
	}

	//windowWidth = document.body.offsetWidth;
	//windowHeight = document.body.offsetHeight;
	//alert(windowHeight);
};

function initializeLayout() {
}

//function setAdministrationView(viewName) {administrationView = viewName}
//function setOrganizationView(viewName) {organizationView = viewName}

//function showAdministrationView(viewName) {if (viewName) {administrationView = viewName} refreshAdministrationView(); changeRichTab('mainTabPanel', 'administrationTab')}
//function showOrganizationView(viewName) {if (viewName) {organizationView = viewName} alert(refreshOrganizationView); refreshOrganizationView(); alert(changeRichTab); changeRichTab('mainTabPanel', 'organizationTab')}

//function refreshAdministrationView() {/*TogglePanelManager.toggleOnClient('pageForm:switch', administrationView)*/}
//function refreshOrganizationView() {/*TogglePanelManager.toggleOnClient('pageForm:switch', organizationView);*/}

function changeRichTab(tabPanelID, tabID) {
	//var fullIDForTabPanel = jQuery("[id$=" + ":"+ tabPanelID + "]").attr("id");
	//var fullIDForTab = jQuery("[id$=" + ":" + tabID + "]").attr("id");
	//RichFaces.switchTab(fullIDForTabPanel, fullIDForTab, tabID);
} 

