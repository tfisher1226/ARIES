package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementTreeActionsXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementTreeActionsXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "TreeActions.xhtml";

		if (elementName.equals("Application")) {
			element.setParentType("Project");
			element.getSubTypes().clear();
			element.getSubTypes().add("");
		}
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition");
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:f=\"http://xmlns.jcp.org/jsf/core\"");
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:rich=\"http://richfaces.org/rich\"");
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		buf.putLine("	");
		buf.putLine("	");
		buf.putLine("	<!--");
		buf.putLine("	  ** "+elementNameUncapped+"TreeActions");
		buf.putLine("	  ** a4j:jsFunction methods to support the "+elementClassName+" Tree.");
		buf.putLine("	  -->");
		buf.putLine("	");
		buf.putLine("	<a4j:region>");
		buf.putLine("		<h:outputScript>");
		buf.putLine("			var "+elementNameUncapped+"TreeState = null;");
		buf.putLine("		</h:outputScript>");

		buf.put(getRefreshElementTree(element));
		buf.put(getExecuteSelectFromTree(element));
		buf.put(getExecuteActionFromTree(element));
		buf.put(getExecuteActionForTreeViewer(element));
		//buf.put(getExecuteActionForElement(element));
		buf.put(getProcessToggleElementTreeNode(element));

		buf.putLine("	");
		buf.putLine("	");
		buf.putLine("	<!--");
		buf.putLine("	  ** "+elementNameUncapped+"TreeActions");
		buf.putLine("	  ** Javascript methods to support the "+elementClassName+" Tree.");
		buf.putLine("	  -->");
		buf.putLine("	");
		buf.putLine1("	<a4j:outputPanel");
		buf.putLine1("		id=\""+elementNameUncapped+"TreeActions\">");
		buf.putLine1("		");
		buf.putLine1("		<h:outputScript>");

		buf.put(generateGetElementTreeNodeId(element));
		buf.put(generateGetElementTreeNodeLabel(element));
		buf.put(generateInitializeElementTreeState(element));
		buf.put(generateUpdateElementTreeState(element));
		buf.put(generateEnableElementTreeActions(element));

		buf.put(generateProcessElementTreeMouseDown(element));
		buf.put(generateShowElementNodeMenu(element, elementClassName));
		buf.put(generateProcessElementTreeDoubleClick(element));

		buf.put(generateProcessViewElement(element));
		buf.put(generateProcessNewElement(element));
		buf.put(generateProcessEditElement(element));
		//buf.put(generateProcessSaveElement(element));
		buf.put(generateProcessRemoveElement(element));
		//buf.put(generateProcessCancelElement(element));
		
		buf.putLine("			</h:outputScript>");
		buf.putLine("		</a4j:outputPanel>");
		buf.putLine("	</a4j:region>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}

	protected String getRefreshElementTree(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** refresh"+elementClassName+"Tree()");
		buf.putLine2("  ** Refreshes the current "+elementClassName+" Tree.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"refresh"+elementClassName+"Tree\" ");
		buf.putLine2("	execute=\"@this\" ");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+elementNameUncapped+"TreeManager.refresh}\"");
		buf.putLine2("	onbegin=\"setCursorWait(); showProgress('Nam', '"+elementClassName+" Records', 'Refreshing current "+elementClassName+" Tree...');\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(this); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"TreeActions, "+elementNameUncapped+"TreeMenu, "+elementNameUncapped+"TreeToolbar, "+elementNameUncapped+"TreePanel\">");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}

	protected String getExecuteSelectFromTree(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeSelectFrom"+elementClassName+"Tree(rowKey, nodeId)");
		buf.putLine2("  ** Handles actions generated from a node in the Application Tree.");
		buf.putLine2("  ** Selects Element on server-side.  Executes NO action on server-side.");
		buf.putLine2("  ** Uses a queuing delay of 0ms - no waiting to combine with other actions like double-click.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeSelectFrom"+elementClassName+"Tree\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	render=\""+elementNameUncapped+"TreeActions, "+elementNameUncapped+"TreeToolbar\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"rowKey\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedRowKey}\" />");
		buf.putLine2("	<a4j:param name=\"nodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedNodeId}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getExecuteActionFromTree(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeActionFrom"+elementClassName+"Tree(rowKey, nodeId, type, action, section)");
		buf.putLine2("  ** Selects Element on server-side.  Executes action on server-side.");
		buf.putLine2("  ** Uses a queuing delay of 0ms - no waiting for any future actions.");
		buf.putLine2("  ** This is typically used by double-click and other submit actions.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeActionFrom"+elementClassName+"Tree\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{workspaceManager.executeAction}\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"TreeActions, "+elementNameUncapped+"TreeMenu, "+elementNameUncapped+"TreeToolbar\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"rowKey\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedRowKey}\" />");
		buf.putLine2("	<a4j:param name=\"nodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedNodeId}\" />");
		buf.putLine2("	<a4j:param name=\"type\" assignTo=\"#{selectionContext.selectedType}\" />");
		buf.putLine2("	<a4j:param name=\"action\" assignTo=\"#{selectionContext.selectedAction}\" />");
		buf.putLine2("	<a4j:param name=\"section\" assignTo=\"#{"+elementNameUncapped+"Wizard.section}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"0\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getExecuteActionForTreeViewer(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeActionFor"+elementClassName+"TreeViewer(rowKey, nodeId, type, action)");
		buf.putLine2("  ** Executes 'action' associated with Element 'type' on server-side.");
		buf.putLine2("  ** Uses a queuing delay of 0ms - no waiting for any future actions.");
		buf.putLine2("  ** This is used by actions triggered from menus and toolbars.");
		buf.putLine2("  ** Refreshes Element view in "+elementClassName+" Tree viewer..");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeActionFor"+elementClassName+"TreeViewer\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{workspaceManager.executeAction}\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"TreeActions, "+elementNameUncapped+"TreeMenu, "+elementNameUncapped+"TreeToolbar, "+elementNameUncapped+"TreeViewer\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"rowKey\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedRowKey}\" />");
		buf.putLine2("	<a4j:param name=\"nodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedNodeId}\" />");
		buf.putLine2("	<a4j:param name=\"area\" assignTo=\"#{selectionContext.selectedArea}\" />");
		buf.putLine2("	<a4j:param name=\"type\" assignTo=\"#{selectionContext.selectedType}\" />");
		buf.putLine2("	<a4j:param name=\"action\" assignTo=\"#{selectionContext.selectedAction}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getExecuteActionForElement(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeActionForElement(type, action)");
		buf.putLine2("  ** Executes 'action' associated with Element 'type' on server-side.");
		buf.putLine2("  ** Uses a queuing delay of 0ms - no waiting for any future actions.");
		buf.putLine2("  ** This is used by actions triggered from menus and toolbars.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeActionForElement\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{workspaceManager.executeAction}\"");
		buf.putLine2("	render=\""+elementNameUncapped+"TreeActions, "+elementNameUncapped+"TreeMenu, "+elementNameUncapped+"TreeToolbar\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"type\" assignTo=\"#{selectionContext.selectedType}\" />");
		buf.putLine2("	<a4j:param name=\"action\" assignTo=\"#{selectionContext.selectedAction}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestDelay=\"0\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}

	protected String getNewElement(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** newElement()");
		buf.putLine2("  ** Creates new Element, and goes to Element Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"newElement\" ");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	execute=\"@this\" ");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	action=\"#{"+targetNameUncapped+"InfoManager.new"+targetName+"}\">");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getOpenElement(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** openElement()");
		buf.putLine2("  ** Opens selected Element in various ways depending on user access rights.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		return buf.get();
	}
	
	
	protected String getViewElementFromTree(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** viewElementFromTree()");
		buf.putLine2("  ** Opens selected Element for read-only view, and goes to Element Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"viewElementFromTree\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+targetNameUncapped+"InfoManager.viewElement}\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"ManagementViewer\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
        buf.putLine2("	<a4j:param name=\"sectionName\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionName}\" />");
        buf.putLine2("	<a4j:param name=\"selectedRowKey\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedRowKey}\" />  ");
        buf.putLine2("	<a4j:param name=\"selectedNodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedNodeId}\" />");
		buf.putLine2("	");
        buf.putLine2("	<!-- these values are assigned here -->");
        buf.putLine2("	<a4j:param name=\"sectionType\" value=\""+targetNameUncapped+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionType}\" />");
        buf.putLine2("	<a4j:param name=\"sectionTitle\" value=\""+targetName+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionTitle}\" />");
        buf.putLine2("	<a4j:param name=\"sectionIcon\" value=\"/icons/nam/"+targetName+"16.gif\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionIcon}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getEditElementFromTree(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** edit"+targetName+"FromTree()");
		buf.putLine2("  ** Opens selected "+targetName+" for editing, and goes to "+targetName+" Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"edit"+targetName+"FromTree\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	execute=\"@this\" ");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	action=\"#{"+targetNameUncapped+"InfoManager.edit"+targetName+"}\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
        buf.putLine2("	<a4j:param name=\"sectionName\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionName}\" />");
        buf.putLine2("	<a4j:param name=\"selectedRowKey\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedRowKey}\" />  ");
        buf.putLine2("	<a4j:param name=\"selectedNodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedNodeId}\" />");
		buf.putLine2("	");
        buf.putLine2("	<!-- these values are assigned here -->");
        buf.putLine2("	<a4j:param name=\"sectionType\" value=\""+targetNameUncapped+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionType}\" />");
        buf.putLine2("	<a4j:param name=\"sectionTitle\" value=\""+targetName+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionTitle}\" />");
        buf.putLine2("	<a4j:param name=\"sectionIcon\" value=\"/icons/nam/"+targetName+"16.gif\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionIcon}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getProcessSelectFromElementTree(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** processSelectFrom"+elementClassName+"Tree()");
		buf.putLine2("  ** Selects "+elementClassName+" node from "+elementClassName+" Tree.");
		buf.putLine2("  -->");
		buf.putLine2("	");
        buf.putLine2("<a4j:jsFunction ");
        buf.putLine2("	name=\"processSelectFrom"+elementClassName+"Tree\"");
        buf.putLine2("	execute=\"@this\" ");
        buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
        buf.putLine2("	limitRender=\"true\"");
        buf.putLine2("	render=\""+elementNameUncapped+"TreeActions\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
        buf.putLine2("	<a4j:param name=\"selectedRowKey\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedRowKey}\" />  ");
        buf.putLine2("	<a4j:param name=\"selectedNodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.selectedNodeId}\" />");
		buf.putLine2("	");
        buf.putLine2("	<!-- provide queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"400\" />");
        buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}

	protected String getProcessToggleElementTreeNode(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** processToggle"+elementClassName+"TreeNode()");
		buf.putLine2("  ** Toggles expand/collapse for nodes in "+elementClassName+" Tree.");
		buf.putLine2("  -->");
		buf.putLine2("	");
        buf.putLine2("<a4j:jsFunction ");
        buf.putLine2("	name=\"processToggle"+elementClassName+"TreeNode\" ");
        buf.putLine2("	execute=\"@this\" ");
        buf.putLine2("	immediate=\"true\"");
        buf.putLine2("	limitRender=\"true\">");
		buf.putLine2("	");
        buf.putLine2("	<!-- these values are passed in -->");
        buf.putLine2("	<a4j:param name=\"nodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.toggledNodeState}\" /> ");
		buf.putLine2("	");
        buf.putLine2("	<!-- provide queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeEvents\" requestDelay=\"0\" /> ");
        buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}

	protected String generateGetElementTreeNodeId(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("");
		buf.putLine4("");
		buf.putLine4("<!--");
		buf.putLine4("  ** get"+elementClassName+"TreeNodeId()");
		buf.putLine4("  ** Returns the node ID of the selected node.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function get"+elementClassName+"TreeNodeId() {");
		buf.putLine4("	if ("+elementNameUncapped+"TreeState != null)");
		buf.putLine4("		return "+elementNameUncapped+"TreeState.nodeId;");
		buf.putLine4("	return null;");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateGetElementTreeNodeLabel(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** get"+elementClassName+"TreeNodeLabel()");
		buf.putLine4("  ** Returns the node label of the selected node.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function get"+elementClassName+"TreeNodeLabel() {");
		buf.putLine4("	if ("+elementNameUncapped+"TreeState != null)");
		buf.putLine4("		return "+elementNameUncapped+"TreeState.nodeLabel;");
		buf.putLine4("	return null;");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateInitializeElementTreeState(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** initialize"+elementClassName+"TreeState()");
		buf.putLine4("  ** Initializes and verifies "+elementClassName+" Tree state information.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function initialize"+elementClassName+"TreeState() {");
		buf.putLine4("	try {");
		buf.putLine4("		var selectedNodeId = \'#{"+elementNameUncapped+"TreeManager.selectedNodeId}\';");
		buf.putLine4("		var selectedRowKey = \'#{"+elementNameUncapped+"TreeManager.selectedRowKey}\';");
		buf.putLine4("		");
		buf.putLine4("		if (selectedNodeId != '') {");
		buf.putLine4("			"+elementNameUncapped+"TreeState = new Object();");
		buf.putLine4("			var nodeId = selectedNodeId;");
		buf.putLine4("			var rowKey = selectedRowKey;");
		buf.putLine4("			var nodeArea = \'#{"+elementNameUncapped+"TreeManager.selectedNode.area}\';");
		buf.putLine4("			var nodeType = \'#{"+elementNameUncapped+"TreeManager.selectedNode.type}\';");
		buf.putLine4("			var nodeLabel = \'#{"+elementNameUncapped+"TreeManager.selectedNode.label}\';");
		buf.putLine4("			update"+elementClassName+"TreeState(null, rowKey, nodeId, nodeArea, nodeType, nodeLabel);");
		buf.putLine4("		}");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateUpdateElementTreeState(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** update"+elementClassName+"TreeState(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel)");
		buf.putLine4("  ** Updates client-side state information for "+elementClassName+" Tree.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function update"+elementClassName+"TreeState(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel) {");
		buf.putLine4("	"+elementNameUncapped+"TreeState = new Object();");
		buf.putLine4("	"+elementNameUncapped+"TreeState.rowKey = rowKey;");
		buf.putLine4("	"+elementNameUncapped+"TreeState.nodeId = nodeId;");
		buf.putLine4("	"+elementNameUncapped+"TreeState.nodeArea = nodeArea;");
		buf.putLine4("	"+elementNameUncapped+"TreeState.nodeType = nodeType;");
		buf.putLine4("	"+elementNameUncapped+"TreeState.nodeLabel = nodeLabel;");
		buf.putLine4("	"+elementNameUncapped+"TreeState.nodeKey = nodeId;");
		buf.putLine4("	//show("+elementNameUncapped+"TreeState);");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateEnableElementTreeActions(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** enable"+elementClassName+"TreeActions(type)");
		buf.putLine4("  ** Enables (or disables) "+elementClassName+" Tree actions based on current client-side state.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function enable"+elementClassName+"TreeActions(type) {");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"TreeViewButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"TreeNewButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"TreeEditButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"TreeMoveButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"TreeRemoveButton\');");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessElementTreeMouseDown(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** process"+elementClassName+"TreeMouseDown(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel)");
		buf.putLine4("  ** Handles mouseDown event on the "+elementClassName+" Tree.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"TreeMouseDown(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel) {");
		buf.putLine4("	update"+elementClassName+"TreeState(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel);");
		buf.putLine4("	enable"+elementClassName+"TreeActions(nodeArea);");
		buf.putLine4("	try {");
		buf.putLine4("		executeSelectFrom"+elementClassName+"Tree(rowKey, nodeId);");
		buf.putLine4("		if (event.which == 3) {");
		
//		List<String> subTypes = element.getSubTypes();
//		Iterator<String> iterator = subTypes.iterator();
//		for (int i=0; iterator.hasNext(); i++) {
//			String subType = iterator.next();
//			if (i == 0) {
//				buf.putLine4("			if (nodeType == '"+subType+"') {");
//				buf.putLine4("			} else if (nodeType == '"+subType+"') {");
//				buf.putLine4("				show"+subType+"NodeMenu(event);");
//		}
		
		buf.putLine4("			show"+elementClassName+"TreeNodeMenu(event, nodeType);");
		buf.putLine4("		}");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}
	
	protected String generateShowElementNodeMenu(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** show"+targetName+"TreeNodeMenu(event, nodeType)");
		buf.putLine4("  ** Displays context-menu for "+targetName+" Tree node..");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function show"+targetName+"TreeNodeMenu(event, nodeType) {");
		buf.putLine4("	var typeUncapped = uncapitalize(nodeType);");
		buf.putLine4("	var menuName = mainFormId + ':' + typeUncapped + 'TreeMenu';");
		buf.putLine4("	var menu = RichFaces.component(menuName);");
		buf.putLine4("	if (menu != null) {");
		buf.putLine4("		menu.show(event, {});");
		buf.putLine4("	}");

		//buf.putLine4("	//alert("+elementNameUncapped+"TreeState.nodeLabel)");
		//buf.putLine4("	//TODO This currently has no effect, we are waiting for when it will work");
		//buf.putLine4("	var menu = #{rich:component('"+targetNameUncapped+"TreeMenu')};");
		//buf.putLine4("	menu.show(event, {});");
		//buf.putLine4("	menu.show(event, {");
		//buf.putLine4("		'projectName': 'project1',"); 
		//buf.putLine4("		'canAdd': 'false'");
		//buf.putLine4("	});");
		buf.putLine4("}");
		return buf.get();
	}
	
	protected String generateProcessElementTreeDoubleClick(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** process"+elementClassName+"TreeDoubleClick(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel)");
		buf.putLine4("  ** Handles double-click event on the "+elementClassName+" Tree.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"TreeDoubleClick(event, rowKey, nodeId, nodeArea, nodeType, nodeLabel) {");
		buf.putLine4("	update"+elementClassName+"TreeState(null, rowKey, nodeId, nodeArea, nodeType, nodeLabel);");
		buf.putLine4("	enable"+elementClassName+"TreeActions(nodeArea);");
		buf.putLine4("	");
		buf.putLine4("	try {");
		//buf.putLine4("		//show(event.currentTarget);");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('applicationTree')});");
		buf.putLine4("		showProgress('Nam', nodeType+' '+nodeLabel, 'Preparing '+nodeType+' summary view...');");
		buf.putLine4("		executeActionForApplicationTreeViewer(rowKey, nodeId, nodeArea, nodeType, 'workspaceManager.viewObject');");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		//buf.putLine4("	if (nodeType == 'Project') {");
		//buf.putLine4("		processProjectNodeDoubleClick(event, rowKey, nodeId);");
		//buf.putLine4("	} else if (nodeType == 'Application') {");
		//buf.putLine4("		processApplicationNodeDoubleClick(event, rowKey, nodeId);");
		//buf.putLine4("	} else if (nodeType.endsWith('MODULE')) {");
		//buf.putLine4("		processModuleNodeDoubleClick(event, rowKey, nodeId);");
		//buf.putLine4("	} else {");
		//buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	//NOT_USED
	protected String generateProcessElementNodeDoubleClick(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);

		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** process"+targetName+"NodeDoubleClick(event, rowKey, nodeId)");
		buf.putLine4("  ** Handles double-click event on a "+targetName+" Node.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+targetName+"NodeDoubleClick(event, rowKey, nodeId) {");
		buf.putLine4("	try {");
		buf.putLine4("		//show(event.currentTarget);");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		//buf.putLine4("		//show"+elementClassName+"Page(source, 'Identification');");
		buf.putLine4("		showProgress('Nam', '"+targetName+" #{"+targetNameUncapped+"InfoManager.selected"+targetName+".name}', 'Preparing "+targetName+" \"summary\" view...');");
		//buf.putLine4("		//showPage(source, '"+elementPath+"/application/application.seam?section='+section);");
		buf.putLine4("		view"+targetName+"('Overview', rowKey, nodeId);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessViewElement(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processViewElement(event, type, action)");
		buf.putLine4("  ** Opens selected Element record.  ");
		buf.putLine4("  ** Goes to the Element summary page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processViewElement(event, type, action) {");
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = 'workspaceManager.viewObject';");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('applicationTree')});");
		buf.putLine4("		showProgress('Nam', type+' Records', 'Opening '+type+' for viewing...');");
		buf.putLine4("		executeActionForElement(type, action);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessNewElement(Element element) {
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String parentNameUncapped = NameUtil.uncapName(parentName);
		//String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processNewElement(event, type, action)");
		buf.putLine4("  ** Creates new Element 'type' record.");
		buf.putLine4("  ** Goes to Element 'type' Wizard page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processNewElement(event, type, action) {");
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = 'workspaceManager.newObject';");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		if ("+elementNameUncapped+"TreeState.nodeLabel != null) {");
		buf.putLine4("			var label = "+elementNameUncapped+"TreeState.nodeLabel;");
		buf.putLine4("			showProgress('Nam', type+' Records', 'Creating new '+type+' for \\\"' + label + '\\\"...');");
		buf.putLine4("		} else showProgress('Nam', type+' Records', 'Creating new '+type+'...');");
		buf.putLine4("		executeActionForElement(type, action);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessEditElement(Element element) {
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String targetNameUncapped = NameUtil.uncapName(targetName);		
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processEditElement(event, type, action)");
		buf.putLine4("  ** Opens Element 'type' record for editing.");
		buf.putLine4("  ** Goes to Element 'type' Wizard page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processEditElement(event, type, action) {");
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = 'workspaceManager.editObject';");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		if ("+elementNameUncapped+"TreeState.nodeLabel != null) {");
		buf.putLine4("			var label = "+elementNameUncapped+"TreeState.nodeLabel;");
		buf.putLine4("			showProgress('Nam', type+' Records', 'Preparing \\\"'+label+'\\\" '+type+' for editing...');");
		buf.putLine4("		} else showProgress('Nam', type+' Records', 'Preparing '+type+' for editing...');");
		buf.putLine4("		executeActionForElement(type, action);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessSaveElement(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processSaveElement(event, type, action)");
		buf.putLine4("  ** Saves selected Element 'type' record to server-side storage.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processSaveElement(event, type, action) {");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		showProgress('Nam', type+' Records', 'Saving Element '+type+'...');");
		buf.putLine4("		saveElement(type, action);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");		
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessRemoveElement(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processRemoveElement(event, type, action)");
		buf.putLine4("  ** Prompts user to remove selected Element 'type' record.");
		buf.putLine4("  ** Removes Element 'type' record from system.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processRemoveElement(event, type, action) {");
		buf.putLine4("	var typeUncapped = uncapitalize(type);"); 
		buf.putLine4("	var label = type;");
		buf.putLine4("	if ("+elementNameUncapped+"TreeState != null)");
		buf.putLine4("		label = "+elementNameUncapped+"TreeState.nodeLabel + ' ' + type;");
		buf.putLine4("	var warningTitle = 'Remove \\\"'+label+'\\\" from system';");
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = typeUncapped + 'EventManager.remove' + type;");
		//buf.putLine4("	//var removeEvent = typeUncapped+'EventManager.fireRemoveEvent';");
		//buf.putLine4("	//var removeEvent = 'nam.model.'+typeUncapped+'.'+type+'RemoveEvent';");
		buf.putLine4("	popupWarningPrompt('Nam', warningTitle, 'Do you wish to continue?', action, '"+elementNameUncapped+"Tree, "+elementNameUncapped+"TreeViewer');");
		buf.putLine4("	setCursorDefault();");
		buf.putLine4("}");
		return buf.get();
	}
	
	protected String generateProcessCancelElement(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processCancel"+elementClassName+"(event)");
		buf.putLine4("  ** Cancels "+elementClassName+" editing.  Return to previous state.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processRemove"+elementClassName+"() {");
		buf.putLine4("	setCursorWait(event.target);"); 
		buf.putLine4("	setCursorWait(event.currentTarget);"); 
		buf.putLine4("	var typeUncapped = type.toLowerCase();");
		buf.putLine4("	var nodeLabel = get"+elementClassName+"TreeNodeLabel();");
		buf.putLine4("	var warningTitle = 'Remove \"' + nodeLabel + '\" '+type+' from system';");
		buf.putLine4("	var removeEvent = typeUncapped + 'EventManager.remove' + type;");
		buf.putLine4("	//var removeEvent = typeUncapped+'EventManager.fireRemoveEvent';");
		buf.putLine4("	//var removeEvent = 'nam.model.'+typeUncapped+'.'+type+'RemoveEvent';");
		buf.putLine4("	popupWarningPrompt('Nam', warningTitle, 'Do you wish to continue?', removeEvent, '"+elementNameUncapped+"Tree');");
		buf.putLine4("	setCursorDefault();");
		buf.putLine4("}");
		return buf.get();
	}
	
	
}
