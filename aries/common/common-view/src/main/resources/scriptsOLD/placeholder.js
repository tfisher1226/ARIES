if (!org) var org = { };
if (!org.aries) org.aries = { };
if (!org.aries.view) org.aries.view = { };
if (!org.aries.view.text) org.aries.view.text = { };

org.aries.view.text.InputTextPlaceHolder = function(textbox) {
    (function(inputText) {
        var COLOR_GRAY  = "#777";
        var COLOR_BLACK = "#000";

        var PLACEHOLDER  = "Type here";
        var EMPTY_STRING = "";
        
        var textbox = inputText;
        
        var prevInput = null;

        if (noInput()) {
            setText(PLACEHOLDER, COLOR_GRAY);
        }
    
        function inputFocus() {
            var userText = getText();
            if (userText != PLACEHOLDER) {
                prevInput = userText;
            }

            setText(EMPTY_STRING, COLOR_BLACK);
        }

        function inputBlur() {
            var isUserInputEmpty = noInput();

            if (isUserInputEmpty && prevInput == null) {
                setText(PLACEHOLDER, COLOR_GRAY);
                return;
            }

            if (isUserInputEmpty) {
                setText(prevInput, COLOR_BLACK);
                return;
            }

        }

        function noInput() {
            return textbox.value.length == 0;
        }

        function getText() {
            return textbox.value;
        }

        function setText(text, color) {
            textbox.value = text;
            textbox.style.color = color;
        }

        // Override the event handlers
        textbox.onfocus = inputFocus;
        textbox.onblur = inputBlur;

    })(textbox);
}

org.aries.view.text.loadPlaceHolders = function () {
    var forms = document.getElementsByTagName("form");
    if (!forms) {
        return;
    }

    for (i = 0; i < forms.length; i++) {
        var elements = forms[i].elements;
        if (!elements) {
            continue;
        }

        for (j = 0; j < elements.length; j++) {
            var element = elements[j];
            if (element.type != "text" || element.className != "ariesPlaceholder") {
                continue;
            }

            new org.aries.view.text.InputTextPlaceHolder(element);
        }
    }
}

org.aries.view.text.processWindowLoad = function () {
    var origLoad = window.onload;
    var loadPlaceHolders = org.aries.view.text.loadPlaceHolders;
    
    if (typeof window.onload != "function") {
        window.onload = loadPlaceHolders;
            return;
    }
    
    window.onload = function() {
        if (origLoad) {
            origLoad();
        }
        
        loadPlaceHolders();
    }
}


org.aries.view.text.processWindowLoad();
