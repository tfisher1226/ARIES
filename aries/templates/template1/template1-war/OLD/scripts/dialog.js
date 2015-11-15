function showAlert(title, subtitle, message) {
	_showMessage('alert', title, subtitle, message, null, false, false, null);
	setCursorDefault(); 
}

function showPrompt(title, subtitle, message, actionEvent, rerenderList) {
	_showMessage('prompt', title, subtitle, message, actionEvent, true, true, rerenderList);
	setCursorDefault(); 
}

function showWarning(title, subtitle, message) {
	_showMessage('warning', title, subtitle, message, null, false, false, null);
	setCursorDefault(); 
}

function showError(title, subtitle, message) {
	_showMessage('error', title, subtitle, message, null, false, false, null);
	setCursorDefault(); 
}

function _showMessage(status, title, subtitle, message, actionEvent, cancelEnabled, progressEnabled, rerenderList) {
	if (!status)
		status = 'alert';
	if (!title)
		title = '';
	if (!subtitle)
		subtitle = '';
	if (!message)
		message = '';
	if (!rerenderList)
		rerenderList = '';
	showMessage(status, title, subtitle, message, actionEvent, cancelEnabled, progressEnabled, rerenderList);
	setCursorDefault(); 
}
