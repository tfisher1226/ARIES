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


public class ElementListActionsXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListActionsXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListActions.xhtml";

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
		String elementPackage = ModelLayerHelper.getElementPackageName(element);
		String elementPath = elementPackage.replace(".", "/");
		
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
		buf.putLine("	<!-- action support -->");
		buf.putLine("	<ui:include src=\"/common/actionSupport.xhtml\" />");
		buf.putLine("	");
		buf.putLine("	");
		buf.putLine("	<!--");
		buf.putLine("	  ** "+elementNameUncapped+"ListActions");
		buf.putLine("	  ** a4j:jsFunction methods to support the "+elementClassName+" List.");
		buf.putLine("	  -->");
		buf.putLine("	");
		buf.putLine("	<a4j:region>");
		buf.putLine("		<h:outputScript>");
		buf.putLine("			var "+elementNameUncapped+"ListState = null;");
		buf.putLine("		</h:outputScript>");

		buf.put(getRefreshElementList(element));
		buf.put(getExecuteSelectFromList(element));
		buf.put(getExecuteActionFromList(element));
		buf.put(getExecuteActionForElement(element));
		buf.put(getExecuteHandleRowChecked(element));

		buf.putLine("	");
		buf.putLine("	");
		buf.putLine("		<!--");
		buf.putLine("		  ** "+elementNameUncapped+"ListActions");
		buf.putLine("		  ** Javascript methods to support the "+elementClassName+" List.");
		buf.putLine("		  -->");
		buf.putLine("		");
		buf.putLine("		<a4j:outputPanel");
		buf.putLine("			id=\""+elementNameUncapped+"ListActions\">");
		buf.putLine("			");
		buf.putLine("			<h:outputScript>");

		buf.put(generateGetElementListRowKey(element));
		buf.put(generateGetElementListRowLabel(element));
		buf.put(generateInitializeElementListState(element));
		buf.put(generateUpdateElementListState(element));
		buf.put(generateEnableElementListActions(element));
		
		buf.put(generateProcessElementListMouseDown(element));
		buf.put(generateProcessElementListDoubleClick(element));
		buf.put(generateProcessElementListSelectionChanged(element));
		
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
	
	protected String getRefreshElementList(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** refresh"+elementClassName+"List(event)");
		buf.putLine2("  ** Refreshes the current "+elementClassName+" List.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"refresh"+elementClassName+"List\" ");
		buf.putLine2("	execute=\"@this\" ");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+elementNameUncapped+"ListManager.refresh}\"");
		buf.putLine2("	onbegin=\"setCursorWait(); showProgress('Nam', '"+elementClassName+" Records', 'Refreshing current "+elementClassName+" List...')\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(this); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"ListActions "+elementNameUncapped+"ListMenu "+elementNameUncapped+"ListToolbar "+elementNameUncapped+"NameListToolbar "+elementNameUncapped+"ListTable "+elementNameUncapped+"NameListTable #{render}\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are assigned here -->");
		buf.putLine2("	<a4j:param name=\"scope\" assignTo=\"#{"+elementNameUncapped+"DataManager.scope}\" value=\"#{scope}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListEvents\" requestDelay=\"0\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}

	protected String getExecuteSelectFromList(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementClassNameUncapped = NameUtil.uncapName(elementClassName);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeSelectFrom"+elementClassName+"List(recordIndex, recordKey)");
		buf.putLine2("  ** Handles actions generated from a row or column in the "+elementClassName+" List.");
		buf.putLine2("  ** Selects "+elementClassName+" element on server-side.  Executes NO action on server-side.");
		buf.putLine2("  ** Uses a queing delay of 400ms - we pause to combine with other actions like double-click");
		buf.putLine2("  ** or checkbox-click.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeSelectFrom"+elementClassName+"List\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	onbegin=\"beginSelect(this)\"");
		buf.putLine2("	oncomplete=\"completeSelect(this)\"");
		buf.putLine2("	render=\""+elementNameUncapped+"ListActions "+elementNameUncapped+"ListToolbar "+elementNameUncapped+"NameListToolbar\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"recordIndex\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordIndex}\" />");
		buf.putLine2("	<a4j:param name=\"recordKey\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordKey}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are assigned here -->");
		buf.putLine2("	<a4j:param name=\"selector\" assignTo=\"#{selectionContext.selectedAction}\" value=\""+elementClassNameUncapped+"\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListEvents\" requestDelay=\"600\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getExecuteActionFromList(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeActionFrom"+elementClassName+"List(recordIndex, recordKey, type, action, section)");
		buf.putLine2("  ** Selects Element on server-side.  Executes action on server-side.");
		buf.putLine2("  ** Uses a queing delay of 0ms - no waiting for any future actions.");
		buf.putLine2("  ** This is typically used by double-click and other submit actions.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeActionFrom"+elementClassName+"List\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{workspaceManager.executeAction}\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"ListActions "+elementNameUncapped+"ListToolbar "+elementNameUncapped+"NameListToolbar #{render}\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"recordIndex\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordIndex}\" />");
		buf.putLine2("	<a4j:param name=\"recordKey\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordKey}\" />");
		buf.putLine2("	<a4j:param name=\"type\" assignTo=\"#{selectionContext.selectedType}\" />");
		buf.putLine2("	<a4j:param name=\"action\" assignTo=\"#{selectionContext.selectedAction}\" />");
		buf.putLine2("	<a4j:param name=\"section\" assignTo=\"#{"+elementNameUncapped+"Wizard.section}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListEvents\" requestDelay=\"0\" />");
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
		buf.putLine2("  ** Uses a queing delay of 0ms - no waiting for any future actions.");
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
		buf.putLine2("	render=\""+elementNameUncapped+"ListActions "+elementNameUncapped+"ListMenu "+elementNameUncapped+"ListToolbar "+elementNameUncapped+"NameListToolbar #{render}\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"type\" assignTo=\"#{selectionContext.selectedType}\" />");
		buf.putLine2("	<a4j:param name=\"action\" assignTo=\"#{selectionContext.selectedAction}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListEvents\" requestDelay=\"0\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getExecuteHandleRowChecked(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeHandle"+elementClassName+"Checked(recordIndex, recordKey, checked, target)");
		buf.putLine2("  ** Handles setting and unsetting of checked state within selected "+elementClassName+".");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeHandle"+elementClassName+"Checked\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+elementNameUncapped+"PageManager.handle"+elementClassName+"Checked}\"");
		buf.putLine2("	onbegin=\"setCursorWait(this); lockScreen()\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(); unlockScreen()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"ListActions "+elementNameUncapped+"ListToolbar "+elementNameUncapped+"NameListToolbar #{render}\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"recordIndex\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordIndex}\" />");
		buf.putLine2("	<a4j:param name=\"recordKey\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordKey}\" />");
		buf.putLine2("	<a4j:param name=\"checked\" assignTo=\"#{"+elementNameUncapped+"ListManager.checkedState}\" />");
		buf.putLine2("	<a4j:param name=\"target\" assignTo=\"#{selectionContext.currentTarget}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are assigned here -->");
		buf.putLine2("	<a4j:param name=\"scope\" assignTo=\"#{"+elementNameUncapped+"DataManager.scope}\" value=\"#{scope}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListEvents\" requestDelay=\"0\" />");
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
		buf.putLine2("  ** new"+targetName+"()");
		buf.putLine2("  ** Creates new "+targetName+", and goes to "+targetName+" Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"new"+targetName+"\" ");
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
		buf.putLine2("  ** open"+targetName+"()");
		buf.putLine2("  ** Opens selected "+targetName+" in various ways depending on user access rights.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		return buf.get();
	}
	
	protected String getViewElementFromList(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** view"+targetName+"FromList()");
		buf.putLine2("  ** Opens selected "+targetName+" for read-only view, and goes to "+targetName+" Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"view"+targetName+"FromList\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+targetNameUncapped+"InfoManager.view"+targetName+"}\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"ManagementViewer\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"sectionName\" assignTo=\"#{"+elementNameUncapped+"Wizard.section}\" />");
		buf.putLine2("	<a4j:param name=\"recordIndex\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordIndex}\" />");
		buf.putLine2("	<a4j:param name=\"recordKey\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordKey}\" />");
		//buf.putLine2("	");
        //buf.putLine2("	<!-- these values are assigned here -->");
        //buf.putLine2("	<a4j:param name=\"sectionType\" value=\""+targetNameUncapped+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionType}\" />");
        //buf.putLine2("	<a4j:param name=\"sectionTitle\" value=\""+targetName+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionTitle}\" />");
        //buf.putLine2("	<a4j:param name=\"sectionIcon\" value=\"/icons/nam/"+targetName+"16.gif\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionIcon}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListClickEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
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
		buf.putLine2("  ** view"+targetName+"FromTree()");
		buf.putLine2("  ** Opens selected "+targetName+" for read-only view, and goes to "+targetName+" Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"view"+targetName+"FromTree\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+targetNameUncapped+"InfoManager.view"+targetName+"}\"");
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
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeClickEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getEditElementFromList(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** edit"+targetName+"FromList()");
		buf.putLine2("  ** Opens selected "+targetName+" for editing, and goes to "+targetName+" Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"edit"+targetName+"FromList\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	execute=\"@this\" ");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	action=\"#{"+targetNameUncapped+"InfoManager.edit"+targetName+"}\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"sectionName\" assignTo=\"#{"+elementNameUncapped+"Wizard.section}\" />");
		buf.putLine2("	<a4j:param name=\"recordIndex\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordIndex}\" />");
		buf.putLine2("	<a4j:param name=\"recordKey\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordKey}\" />");
		//buf.putLine2("	");
        //buf.putLine2("	<!-- these values are assigned here -->");
        //buf.putLine2("	<a4j:param name=\"sectionType\" value=\""+targetNameUncapped+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionType}\" />");
        //buf.putLine2("	<a4j:param name=\"sectionTitle\" value=\""+targetName+"\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionTitle}\" />");
        //buf.putLine2("	<a4j:param name=\"sectionIcon\" value=\"/icons/nam/"+targetName+"16.gif\" assignTo=\"#{"+elementNameUncapped+"TreeManager.sectionIcon}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- provide event queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListClickEvents\" requestDelay=\"400\" />");
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
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeClickEvents\" requestDelay=\"400\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getProcessSelectFromElementList(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** processSelectFrom"+elementClassName+"List()");
		buf.putLine2("  ** Selects "+elementClassName+" record from "+elementClassName+" List.");
		buf.putLine2("  -->");
		buf.putLine2("	");
        buf.putLine2("<a4j:jsFunction ");
        buf.putLine2("	name=\"processSelectFrom"+elementClassName+"List\"");
        buf.putLine2("	execute=\"@this\" ");
        buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
        buf.putLine2("	limitRender=\"true\"");
        buf.putLine2("	render=\""+elementNameUncapped+"ListActions, "+elementNameUncapped+"ListToolbar\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
        buf.putLine2("	<a4j:param name=\"recordIndex\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordIndex}\" />  ");
        buf.putLine2("	<a4j:param name=\"recordKey\" assignTo=\"#{"+elementNameUncapped+"ListManager.selectedRecordKey}\" />");
		buf.putLine2("	");
        buf.putLine2("	<!-- provide queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListClickEvents\" requestDelay=\"400\" />");
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
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeClickEvents\" requestDelay=\"400\" />");
        buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	

	protected String generateGetElementListRowKey(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("");
		buf.putLine4("");
		buf.putLine4("<!--");
		buf.putLine4("  ** get"+elementClassName+"ListRowKey()");
		buf.putLine4("  ** Returns the unique record key of the selected row.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function get"+elementClassName+"ListRowKey() {");
		buf.putLine4("	if ("+elementNameUncapped+"ListState != null)");
		buf.putLine4("		return "+elementNameUncapped+"ListState.recordKey;");
		buf.putLine4("	return null;");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateGetElementListRowLabel(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** get"+elementClassName+"ListRowLabel()");
		buf.putLine4("  ** Returns the record label of the selected row.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function get"+elementClassName+"ListRowLabel() {");
		buf.putLine4("	if ("+elementNameUncapped+"ListState != null)");
		buf.putLine4("		return "+elementNameUncapped+"ListState.recordLabel;");
		buf.putLine4("	return null;");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateInitializeElementListState(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** initialize"+elementClassName+"ListState()");
		buf.putLine4("  ** Initializes and verifies "+elementClassName+" List state information.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function initialize"+elementClassName+"ListState() {");
		buf.putLine4("	try {");
		buf.putLine4("		var rowIndex = '#{"+elementNameUncapped+"ListManager.selectedRecordIndex}';");
		buf.putLine4("		var recordKey = '#{"+elementNameUncapped+"ListManager.selectedRecordKey}';");
		buf.putLine4("		var recordLabel = '#{"+elementNameUncapped+"ListManager.selectedRecordLabel}';");
		buf.putLine4("		if (recordKey != '') {");
		buf.putLine4("			update"+elementClassName+"ListState(null, rowIndex, recordKey, recordLabel);");
		buf.putLine4("		}");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateUpdateElementListState(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** update"+elementClassName+"ListState(event, rowIndex, recordKey, recordLabel)");
		buf.putLine4("  ** Updates client-side state information for "+elementClassName+" List.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function update"+elementClassName+"ListState(event, rowIndex, recordKey, recordLabel) {");
		buf.putLine4("	"+elementNameUncapped+"ListState = new Object();");
		buf.putLine4("	"+elementNameUncapped+"ListState.rowIndex = rowIndex;");
		buf.putLine4("	//"+elementNameUncapped+"ListState.recordId = recordId;");
		buf.putLine4("	"+elementNameUncapped+"ListState.recordKey = recordKey;");
		buf.putLine4("	"+elementNameUncapped+"ListState.recordLabel = recordLabel;");
		buf.putLine4("	//show("+elementNameUncapped+"ListState);");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateEnableElementListActions(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** enable"+elementClassName+"ListActions(type)");
		buf.putLine4("  ** Enables (or disables) "+elementClassName+" List actions based on current client-side state.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function enable"+elementClassName+"ListActions(type) {");
		buf.putLine4("	//enableButton(\'"+elementNameUncapped+"ListViewButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"ListNewButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"ListEditButton\');");
		//buf.putLine4("	//enableButton(\'"+elementNameUncapped+"ListMoveButton\');");
		buf.putLine4("	enableButton(\'"+elementNameUncapped+"ListRemoveButton\');");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessElementListMouseDown(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** process"+elementClassName+"ListMouseDown(event, rowIndex, recordKey, recordLabel)");
		buf.putLine4("  ** Handles mouseDown event on the "+elementClassName+" List.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"ListMouseDown(event, rowIndex, recordKey, recordLabel) {");
		buf.putLine4("	update"+elementClassName+"ListState(event, rowIndex, recordKey, recordLabel);");
		buf.putLine4("	enable"+elementClassName+"ListActions('"+elementNameUncapped+"');");
		buf.putLine4("	if (isRightMouseClick(event))");
		buf.putLine4("		return;");
		buf.putLine4("	try {");
		buf.putLine4("		executeSelectFrom"+elementClassName+"List(rowIndex, recordKey);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}
	
	protected String generateProcessElementListDoubleClick(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** process"+elementClassName+"ListDoubleClick(event, rowIndex, recordKey, recordLabel)");
		buf.putLine4("  ** Handles double-click action on the "+elementClassName+" List.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"ListDoubleClick(event, rowIndex, recordKey, recordLabel) {");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		//buf.putLine4("		//setCursorWait(#{rich:element('applicationListTable')});");
		buf.putLine4("		showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" ' + recordLabel + ' for editing...');");
		buf.putLine4("		executeActionFrom"+elementClassName+"List(rowIndex, recordKey, '"+elementClassName+"', 'workspaceManager.editObject');");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}
	
	protected String generateProcessElementListSelectionChanged(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** process"+elementClassName+"ListSelectionChanged(event, rowIndex, recordKey, recordLabel, target)");
		buf.putLine4("  ** Handles check-box selection state changed event for "+elementClassName+" List item.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"ListSelectionChanged(event, rowIndex, recordKey, recordLabel, target) {");
		buf.putLine4("	try {");
		buf.putLine4("		beginSelect(event.target);"); 
		buf.putLine4("		var checked = event.target.checked;"); 
		buf.putLine4("		executeHandle"+elementClassName+"Checked(rowIndex, recordKey, checked, target);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	} finally {");
		buf.putLine4("		completeSelect(event.target);");
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
		buf.putLine4("  ** Opens selected Element 'type' record.");
		buf.putLine4("  ** Goes to the Element 'type' summary page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processViewElement(event, type, action) {");
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = 'workspaceManager.viewObject';");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"ListTable')});");
		buf.putLine4("		if ("+elementNameUncapped+"ListState != null) {");
		buf.putLine4("			var label = "+elementNameUncapped+"ListState.recordLabel;");
		buf.putLine4("			showProgress('Nam', type+' Records', 'Opening \\\"'+label+'\\\" '+type+' for viewing...');");
		buf.putLine4("		} else showProgress('Nam', type+' Records', 'Opening '+type+' for viewing...');");
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
		//buf.putLine4("	setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		if ("+elementNameUncapped+"ListState != null) {");
		buf.putLine4("			var label = "+elementNameUncapped+"ListState.recordLabel;");
		buf.putLine4("			showProgress('Nam', type+' Records', 'Creating new '+type+' for \\\"'+label+'\\\"...');");
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
		//buf.putLine4("	setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		if ("+elementNameUncapped+"ListState != null) {");
		buf.putLine4("			var label = "+elementNameUncapped+"ListState.recordLabel;");
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
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = 'workspaceManager.saveObject';");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		showProgress('Nam', type+' Records', 'Saving '+type+' record...')");
		buf.putLine4("		saveElement(event);");
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
		buf.putLine4("	if ("+elementNameUncapped+"ListState != null)");
		buf.putLine4("		label = "+elementNameUncapped+"ListState.recordLabel + ' ' + type;");
		buf.putLine4("	var warningTitle = 'Remove \\\"'+label+'\\\" from system';");
		buf.putLine4("	if (action == null)");
		buf.putLine4("		action = typeUncapped + 'EventManager.remove' + type;");
		//buf.putLine4("	//var removeEvent = typeUncapped+'EventManager.fireRemoveEvent';");
		//buf.putLine4("	//var removeEvent = 'nam.model.'+typeUncapped+'.'+type+'RemoveEvent';");
		buf.putLine4("	popupWarningPrompt('Nam', warningTitle, 'Do you wish to continue?', action, '"+elementNameUncapped+"ListPane');");
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
		buf.putLine4("  ** processCancel"+elementClassName+"(event, type)");
		buf.putLine4("  ** Cancels "+elementClassName+" editing.  Return to previous state.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processCancel"+elementClassName+"() {");
		buf.putLine4("}");
		return buf.get();
	}
	
	
}
