var eventSource = null;

function setCursorDefault(element) {
	setCursor('default', element);
	if (eventSource != null)
		setLocalCursor('pointer', eventSource);
}

function setGlobalCursorDefault() {
	setGlobalCursor('default');
}

function setLocalCursorDefault(element) {
	setLocalCursor('default', element);
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

function setGlobalCursorWait() {
	setGlobalCursor('wait');
}

function setCursor(value, element) {
	setLocalCursor(value, element);
	setGlobalCursor(value);
}

function setGlobalCursor(value) {
	document.body.style.cursor = value;
}

function setLocalCursor(value, element) {
	/*
	if (eventSource && eventSource.style && eventSource != element) {
		eventSource.style.cursor = 'pointer';
	}
	*/
	if (element && element.style) {
		element.style.cursor = value;
		this.eventSource = element; 
	}	
}
