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

function showPage(url) {
    window.location.href = url;
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

