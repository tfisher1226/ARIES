function popupToLeft(element, popupId) {
	var point = getPositionOf(element);
	Richfaces.showModalPanel(popupId, {top:point[1], left:point[0]});
}

function popupToRight(element, popupId) {
	var point = getPositionOf(element);
	Richfaces.showModalPanel(popupId, {top:point[1], left:point[0]});
}

function getPositionOf(element, type) {
	var x = y = 0;
	if (type == 'UR') {
		if (element != null) {
			x = element.offsetWidth;
		}
	}
	if (type == 'UL') {
		if (element != null) {
			x = element.offsetWidth;
			y = 0 - element.offsetHeight;
		}
	}
	if (type == 'LR') {
		if (element != null) {
			y = element.offsetHeight;
		}
	}
	if (type == 'LL') {
		if (element != null) {
			x = element.offsetWidth;
			y = element.offsetHeight;
		}
	}
	if (element && element.offsetParent) {	
		do {
			x += element.offsetLeft;
			y += element.offsetTop;	
		} while (element = element.offsetParent);
	}
	return [x, y];
}
