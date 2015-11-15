var eventSource = null;
var eventSources = [];

function setCursorDefault(element) {
	setCursor('default', element);
	//setGlobalCursor('default');
	if (eventSource != null)
		setLocalCursor('pointer', eventSource);
	//setCursor('pointer', null);
	for	(index = 0; index < this.eventSources.length; index++) {
		var source = this.eventSources[index];
		if (source && source.style) {
			source.style.cursor = 'pointer';
			//alert(element2);
		}
	} 
	eventSources = [];
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
	this.eventSources.push(element);
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
		//this.eventSources.push(element);
		//alert(element);
	}
	
//	if (element == null) {
//		for	(index = 0; index < this.eventSources.length; index++) {
//		    var element2 = this.eventSources[index];
//		    if (element2 && element2.style) {
//				element2.style.cursor = value;
//				//alert(element2);
//		    }
//		} 
//		eventSources = [];
//	}
}
