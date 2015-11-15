function setCursorDefault(element) {
	setCursor('default', element);
}

function setCursorPointer(element) {
	setCursor('pointer', element);
}

function setCursorMove(element) {
	setCursor('move', element);
}

function setCursorText(element) {
	setCursor('text', element);
}

function setCursorProgress(element) {
	setCursor('progress', element);
}

function setCursorHelp(element) {
	setCursor('help', element);
}

function setCursorNotAllowed(element) {
	setCursor('not-allowed', element);
}

function setCursorWait(element) {
	setCursor('wait', element);
}

function setCursor(value, element) {
	document.body.style.cursor = value;
	if (element && element.style) {
		//element.style.cursor = value;
	}	
}
