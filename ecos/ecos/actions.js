<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<ui:composition 
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:ui="http://java.sun.com/jsf/facelets">

	<script type="text/javascript">
	//<![CDATA[ 
	
	function showHomePage(parent) {
		alert(3); 
		showContentSection('home');
	}

	function showAboutPage(parent) {
		//showContentSection('about');
		showSection('about-pane')
	}

	function showContactPage(parent) {
		alert(3); 
		try {
			//show(parent);
			var mapElement = #{rich:element('ECOSMap')};
			var panelElement = #{rich:element('ECOSMapPanel')};
			mapElement.offsetHeight;
			mapElement.style.position = 'static';
			mapElement.style.visibility = 'visible';
			panelElement.appendChild(mapElement);
			showContentSection('contact');
			alert(8);
		} catch (e) {
			alert(e);
		}
	}

	function showDialog2() {
		_showTestDialog("Hello");
	
	function showPublicPage() {
		window.location.href = '/public.seam'
		//window.open('/public.seam');
	}
	
	function showFlyer() {
		window.open('/ecos/flyer.pdf', '_blank');
	}
	
	function showContentSection(sectionName) {
		#{rich:component('ecosContentPane')}.switchToItem(sectionName);
	}
	
	function showSection(sectionName) {
		#{rich:component('ecosViewerPane')}.switchToItem(sectionName);
		saveUserAction(sectionName);
	}

	function showSectionAndTab(sectionName, tabPaneId, tabId, tabLabel) {
		showSection(sectionName);
		showTab(tabPaneId, tabId, tabLabel);
	}

	function showTab(tabPaneId, tabId, tabLabel) {
		try {
			//alert(tabPaneId);
			//#{rich:component(tabPaneId)}.switchToItem(tabId);
			//alert(RichFaces);
			//RichFaces.switchTab(tabPaneId, tabId, tabLabel);
		} catch(e) {
			alert(e);
		}
	}
	
	function showLayeredPane(parentName, sectionName) {
		#{rich:component('ecosViewerPane')}.switchToItem(sectionName);
	}


	//]]>
	</script>
</ui:composition>