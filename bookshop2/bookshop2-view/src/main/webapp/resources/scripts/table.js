function highLightRow(tableRow) {
    jQuery(tableRow).siblings().removeClass('tableRowActive'); 
	jQuery(tableRow).addClass('tableRowActive');
}

function highLightField(tableRow, borderColor) {
	setFieldBorder(tableRow, borderColor);
	highLightRow(tableRow);
}

function unhighLightRow(row) {
	jQuery(row).removeClass('tableRowActive');
}

function unhighLightField(tableRow, borderColor) {
	setFieldBorder(tableRow, borderColor);
	unhighLightRow(tableRow);
}

var currentRow = undefined;

function selectRow(row) {
	unselectRow(currentRow);
    jQuery(row).siblings().removeClass('tableRowSelected'); 
    jQuery(row).removeClass('tableRowActive'); 
    jQuery(row).addClass('tableRowSelected');
    currentRow = row;
    //alert(row);
}

function focusField(tableRow, borderColor) {
	setFieldBorder(tableRow, borderColor);
	selectRow(tableRow);
}

function unselectRow(row) {
	if (!row)
		row = currentRow;
	if (row) {
	    jQuery(row).siblings().removeClass('tableRowSelected'); 
	    jQuery(row).removeClass('tableRowSelected'); 
	    jQuery(row).removeClass('tableRowActive'); 
	}
}

function enableToolbar(toolbar) {
	jQuery(toolbar).find(".rf-ddm-itm-dis").hide();
	jQuery(toolbar).find(".toolItem").show();
	//jQuery(#{rich:element('RoleListEditRoleButtonDisabled')}).hide();
	//jQuery(#{rich:element('RoleListEditRoleButton')}).show();
}

function unfocusField(tableRow, borderColor) {
	setFieldBorder(tableRow, borderColor);
	unselectRow(tableRow);
}

function setFieldBorder(tableRow, borderColor) {
	getBorderedParent(tableRow).style.borderColor = borderColor;
}

function getBorderedParent(tableRow) {
	return tableRow.parentElement.parentElement.parentElement;
}