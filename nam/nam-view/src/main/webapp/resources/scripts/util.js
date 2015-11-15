
function isDefined(element) {
    if (!element)
    	return false;
    if (element == 'undefined')
    	return false;
    return true;
}

function forceRedraw(element) {
	element.style.display='none';
	element.offsetHeight; // no need to store this anywhere, the reference is enough
	element.style.display='block';
}

function forceRedrawOLD(element) {
    if (!element) { 
    	return; 
    }

    var n = document.createTextNode(' ');
    var disp = element.style.display;  // don't worry about previous display style

    element.appendChild(n);
    element.style.display = 'none';

    setTimeout(function() {
        element.style.display = disp;
        n.parentNode.removeChild(n);
    }, 20); // you can play with this timeout to make it as short as possible
}

/**
 * Set focus on the element of the given id.
 * @param id The id of the element to set focus on.
 */
function setFocus(id) {
    var element = document.getElementById(id);
    if (element && element.focus) {
        element.focus();
    }
}

/**
 * Set highlight on the elements of the given ids. It basically sets the classname of the elements
 * to 'highlight'. This require at least a CSS style class '.highlight'.
 * @param ids The ids of the elements to be highlighted, comma separated.
 */
function setHighlight(ids) {
    var idsArray = ids.split(",");
    for (var i = 0; i < idsArray.length; i++) {
        var element = document.getElementById(idsArray[i]);
        if (element) {
            element.className = 'highlight';
        }
    }
}

function enableButton(buttonId) {
	var fullId = mainFormId + ':' + buttonId;
	var buttonEnabled = document.getElementById(fullId);
	var buttonDisabled = document.getElementById(+'Disabled');
	if (buttonEnabled != null)
		buttonEnabled.style.display = 'block';
	if (buttonDisabled != null)
		buttonDisabled.style.display = 'none';
}

function showDisplay(id) {
	setDisplay(id, 'block');
}

function hideDisplay(id) {
	setDisplay(id, 'none');
}

function setDisplay(id, value) {
	var fullId = mainFormId + ':' + id;
	var element = document.getElementById(fullId);
	if (element != null)
		element.style.display = value;
}

function toggleDisplay(id) {
	var fullId = mainFormId + ':' + id;
	var element = document.getElementById(fullId);
	if (element.style.display != 'block')
		element.style.display = 'block';
	else element.style.display = 'none';
}

function set(id, name, value) {
	var fullId = mainFormId + ':' + id;
	var element = document.getElementById(fullId);
	//alert(element);
	if (element != null) {
		//alert(element.style[name]);
		element.style[name] = value;
		//alert(name + ', ' + value);
	}
}

function isDefined(object) {
    return object != null && typeof object !== "undefined";
}

function toString(object) {
	var text = "";
	for (p in object)
		text += "property: name="+p+", value="+object[p]+"\n";
	return text;
}

function show(object) {
	alert(toString(object));
}

function showObject(object) {
	var text = "";
	for (p in object) {
		text += "property: name="+p+", value="+object[p]+"\n";
	}
	alert(text);
}

function onlyDigits(evt) {
    var asciiCode = (evt.which) ? evt.which : evt.keyCode;

    // Allow the backspace key (ASCII code 8) or tab key (ASCII Code 9) or user 
    // input between 0 and 9 (ASCII codes 48 to 57).
    return asciiCode == 8 || asciiCode == 9 || (asciiCode >= 48 && asciiCode <= 57);
}


function sleep(ms) {
    var sleeping = true;
    var now = new Date();
    var alarm;
    var startTime = now.getTime();
    //alert("starting nap at timestamp: " + startTime + "\nWill sleep for: " + ms + " ms");
    while (sleeping) {
        alarm = new Date();
        alarmTime = alarm.getTime();
        if (alarmTime - startTime > ms) { 
        	sleeping = false; 
        }
    }     
    //alert("Wakeup!");
}

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function uncapitalize(string) {
	return string.charAt(0).toLowerCase() + string.substr(1);
}

function replaceContent(parentNodeId, newNodeId) {
	try {
		var newNode = document.getElementById(mainFormId+":"+newNodeId);
		var parentNode = document.getElementById(mainFormId+":"+parentNodeId);
		while (parentNode.firstChild) {
			var firstChild = parentNode.firstChild;
			parentNode.removeChild(firstChild);
		}
		//alert(newNode);
		parentNode.appendChild(newNode);
		newNode.style.display = "block";
	} catch(e) {
		alert("Error" + e);
	}
}

function errorsExist() {
	try {
		var errorSeverity = document.getElementById('maximumSeverity');
		//alert(errorSeverity);
		if (!isDefined(errorSeverity)) {
			//alert('null');
			return false;
		}
		var severityLevel = errorSeverity.value;
		//alert(severityLevel);
		if (!isDefined(severityLevel)) {
			//alert('null');
			return false;
		}
		if (severityLevel == '1') {
			//alert('1');
			return false;
		} else if (severityLevel == '2') {
			//alert('2');
			return true;
		}
	} catch(e) {
		alert("Error" + e);
	}
	return false;
}

function isRightMouseClick(event) {
    var isRightMB = false;
    event = event || window.event;
    if ("which" in e)  { // Gecko (Firefox), WebKit (Safari/Chrome) and Opera
		isRightMB = e.which == 3; 
	} else if ("button" in e)  { // IE, Opera 
		isRightMB = e.button == 2;
	} 
    return isRightMB;
}

function filter(table, tableId, columnId, value) {
	try {
	    //var table = document.getElementById(mainFormId + ':' + tableId);
		//alert(table.filter);
	    if (table != null)
	    	table.filter(columnId, value, true); 
	    else alert("Table not found: "+tableId);
	    //alert(8);
	} catch (e) {
		alert("Error: " + e);
	}
}
