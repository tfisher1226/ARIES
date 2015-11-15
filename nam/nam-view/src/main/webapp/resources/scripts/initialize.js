var windowWidth = 0;
var windowHeight = 0;
var browserName = null;
var browserVersion = null;
var dialog = null;


window.onload = function(event) {
	initializePage(); 
};

//window.onresize = function(event) {
var doResize = function(event) {
	//show(event);
	initializePage();
	initializeLayout();
	//location.reload(true);
	//window.location.href = window.location.href
	//this.location.reload(true);
	//alert(9);
	return true;
};

//var lazyResize = _.debounce(doResize, 200, false);
//window.onresize = lazyResize;

function initializePage() {
	detectBrowser();
	//initializeDimensions();
	//initializeGlobals();
	//alert(9);
}

function detectBrowser() {
	if (jQuery.browser.mozilla) {
		browserName = "mozilla";
		createClass('.browserSpecificInputText', "width: 100%; height: 23px; padding: 0 4px;");
		createClass('.browserSpecificInputTextArea', "width: 100%; padding: 0 4px;");
		//createClass('.browserSpecificToolItem', "margin-bottom: 2px;");
		
	} else if (jQuery.browser.msie) {
		browserName = "msie";
		createClass('.browserSpecificInputText', "width: 98%; height: 23px; padding: 0 1%;");
		createClass('.browserSpecificInputTextArea', "width: 98%; padding: 0 1%;");
		//createClass('.browserSpecificToolItem', "margin-bottom: 4px;");
	}

	//TODO flag error
	//show(browserName);
	browserVersion = jQuery.browser.version;
}

function createClass(name, rules) {
    var style = document.createElement('style');
    style.type = 'text/css';
    document.getElementsByTagName('head')[0].appendChild(style);
    if (!(style.sheet || {}).insertRule) {
        (style.styleSheet || style.sheet).addRule(name, rules);
    } else {
        style.sheet.insertRule(name+"{"+rules+"}", 0);
    }
}

function applyClass(name, element, doRemove) {
    if(typeof element.valueOf() == "string") {
        element = document.getElementById(element);
    }
    if (!element) 
    	return;
    if (doRemove) {
        element.className = element.className.replace(new RegExp("\\b"+name+"\\b", "g"));
    } else {
        element.className = element.className + " "+name;
    }
}

function initializeDimensions() {
	var width = 0;
	var height = 0;
	
	if (document.body && document.body.offsetWidth) {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight;
		//alert(windowHeight);
	}
	if (document.compatMode=='CSS1Compat' && document.documentElement && document.documentElement.offsetWidth) {
		width = document.documentElement.offsetWidth;
		height = document.documentElement.offsetHeight;
		//alert(windowHeight);
	}
	if (window.innerWidth && window.innerHeight) {
		width = window.innerWidth;
		height = window.innerHeight;
		//alert(windowHeight);
	}

	//windowWidth = document.body.offsetWidth;
	//windowHeight = document.body.offsetHeight;
	//alert(windowHeight);
	
	if (windowWidth != width || windowHeight != height) {
		windowWidth = width;
		windowHeight = height;
		//initializeGlobals();
	}
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

