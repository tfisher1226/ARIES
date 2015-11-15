function showAlert(title, subtitle, message) {
	_showMessage('alert', title, subtitle, message, false);
	setCursorDefault(); 
}

function showPrompt(title, subtitle, message, actionEvent, rerenderList) {
	_showMessage('prompt', title, subtitle, message, true, actionEvent, rerenderList);
	setCursorDefault(); 
}

function showWarning(title, subtitle, message) {
	_showMessage('warning', title, subtitle, message, false);
	setCursorDefault(); 
}

function showError(title, subtitle, message) {
	_showMessage('error', title, subtitle, message, false);
	setCursorDefault(); 
}

function _showMessage(status, title, subtitle, message, cancelEnabled, actionEvent, rerenderList) {
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
	showMessage(status, title, subtitle, message, cancelEnabled, actionEvent, rerenderList);
	setCursorDefault(); 
}
