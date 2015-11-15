function highlightNode(node) {
	jQuery(node).siblings().removeClass('treeNodeActive'); 
    jQuery(node).addClass('treeNodeActive');
}

function unhighlightNode(node) {
	jQuery(node).removeClass('treeNodeActive');
}

function selectNode(node) {
	jQuery(node).siblings().removeClass('treeNodeSelected'); 
    jQuery(node).removeClass('treeNodeActive'); 
    jQuery(node).addClass('treeNodeSelected');
}