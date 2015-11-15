var zIndex = 1000;

function showAlert(title, subtitle, message) {
	showMessage('alert', title, subtitle, message, '', false, false, '');
}

function showPrompt(title, subtitle, message, actionEvent, renderList) {
	showMessage('prompt', title, subtitle, message, actionEvent, true, true, renderList);
}

function showWarning(title, subtitle, message) {
	showMessage('warning', title, subtitle, message, '', false, false, '');
}

function showError(title, subtitle, message) {
	showMessage('error', title, subtitle, message, '', false, false, '');
}

function showMessage(status, title, subtitle, message, actionEvent, cancelEnabled, progressEnabled, renderList) {
	if (!status)
		status = 'alert';
	if (!title)
		title = '';
	if (!subtitle)
		subtitle = '';
	if (!message)
		message = '';
	if (!renderList)
		renderList = '';
	_showMessage(status, title, subtitle, message, actionEvent, cancelEnabled, progressEnabled, renderList);
	setCursorDefault(); 
}

function hideMessage() {
	_hideMessage();
}


function popupAlert(title, subtitle, message) {
	showPopup('alert', title, subtitle, message, '', false, false, '');
}

function popupPrompt(title, subtitle, message, actionEvent, renderList) {
	showPopup('prompt', title, subtitle, message, actionEvent, true, true, renderList);
}

function popupWarningPrompt(title, subtitle, message, actionEvent, renderList) {
	showPopup('warning', title, subtitle, message, actionEvent, true, true, renderList);
}

function popupWarning(title, subtitle, message) {
	showPopup('warning', title, subtitle, message, '', false, false, '');
}

function popupError(title, subtitle, message) {
	showPopup('error', title, subtitle, message, '', false, false, '');
}

function showPopup(status, title, subtitle, message, actionEvent, cancelEnabled, progressEnabled, renderList) {
	if (!status)
		status = 'alert';
	if (!title)
		title = '';
	if (!subtitle)
		subtitle = '';
	if (!message)
		message = '';
	if (!renderList)
		renderList = '';
	_showPopup(status, title, subtitle, message, actionEvent, cancelEnabled, progressEnabled, renderList);
	setCursorDefault(); 
}

function hidePopup() {
	_hidePopup();
}

function _processDialogKeyDown(dialog, buttonId, event) {
	if (event.keyCode == 13) {
		var button = document.getElementById(buttonId);
		if (button != null)
			button.click(); 
		return false;
	} 
	if (event.keyCode == 27) {
		dialog.hide(); 
		return false;
	}
	return true;
}

