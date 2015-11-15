package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementActionsXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementActionsXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "Actions.xhtml";

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
		buf.putLine("	<a4j:region>");
		
		buf.put(getShowElementListPage(element));
		buf.put(getShowElementTreePage(element));
		buf.put(getShowElementManagementPage(element));
		buf.put(getShowElementWizardPage(element));
		
		buf.put(getShowElementDialog(element, true));
		buf.put(getShowElementDialog(element, false));

////		buf.put(getRefreshElementList(element));
////		buf.put(getRefreshElementTree(element));
//
//		//buf.put(getExecuteActionNoDelay(element));
//		
//		buf.put(getNewElement(element, elementClassName));
//		//buf.put(getOpenElement(element, elementClassName));
//		buf.put(getViewElementFromList(element, elementClassName));
//		buf.put(getViewElementFromTree(element, elementClassName));
//		buf.put(getEditElementFromList(element, elementClassName));
//		buf.put(getEditElementFromTree(element, elementClassName));
//		
////		buf.put(getNewElement(element, "Project"));
////		buf.put(getEditElementFromTree(element, "Project"));
////		buf.put(getViewElementFromTree(element, "Project"));
////
////		buf.put(getNewElement(element, "Module"));
////		buf.put(getEditElementFromTree(element, "Module"));
////		buf.put(getViewElementFromTree(element, "Module"));
//		
//		buf.put(getProcessSelectFromElementList(element));
//		buf.put(getProcessSelectFromElementTree(element));
//		buf.put(getProcessToggleElementTreeNode(element));
		buf.putLine("	</a4j:region>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}	
	
	protected String getShowElementListPage(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("");
		buf.putLine2("<!--");
		buf.putLine2("  ** show"+elementClassName+"ListPage()");
		buf.putLine2("  ** Goes to the "+elementClassName+" Management List Page.");
		buf.putLine2("  -->");
		buf.putLine2("");
        buf.putLine2("<a4j:jsFunction ");
        buf.putLine2("	name=\"show"+elementClassName+"ListPage\" ");
        buf.putLine2("	execute=\"@this\"");
        buf.putLine2("	immediate=\"true\"");
        buf.putLine2("	bypassUpdates=\"true\"");
        buf.putLine2("	action=\"#{"+elementNameUncapped+"PageManager.initialize"+elementClassName+"ListPage}\"");
        buf.putLine2("	onbegin=\"setCursorWait(event.source); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" List...')\">");
        buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getShowElementTreePage(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("");
		buf.putLine2("<!--");
		buf.putLine2("  ** show"+elementClassName+"TreePage()");
		buf.putLine2("  ** Goes to the "+elementClassName+" Management Tree Page.");
		buf.putLine2("  -->");
		buf.putLine2("");
        buf.putLine2("<a4j:jsFunction ");
        buf.putLine2("	name=\"show"+elementClassName+"TreePage\" ");
        buf.putLine2("	execute=\"@this\"");
        buf.putLine2("	immediate=\"true\"");
        buf.putLine2("	bypassUpdates=\"true\"");
        buf.putLine2("	action=\"#{"+elementNameUncapped+"PageManager.initialize"+elementClassName+"TreePage}\"");
        buf.putLine2("	onbegin=\"setCursorWait(event.source); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" Tree...')\">");
        buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getShowElementManagementPage(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("");
		buf.putLine2("<!--");
		buf.putLine2("  ** show"+elementClassName+"ManagementPage()");
		buf.putLine2("  ** Goes to the "+elementClassName+" Management Overview Page.");
		buf.putLine2("  -->");
		buf.putLine2("");
        buf.putLine2("<a4j:jsFunction ");
        buf.putLine2("	name=\"show"+elementClassName+"ManagementPage\" ");
        buf.putLine2("	execute=\"@this\"");
        buf.putLine2("	immediate=\"true\"");
        buf.putLine2("	bypassUpdates=\"true\"");
        buf.putLine2("	action=\"#{"+elementNameUncapped+"PageManager.initialize"+elementClassName+"ManagementPage}\"");
        buf.putLine2("	onbegin=\"setCursorWait(event.source); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" related information...')\">");
        buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getShowElementWizardPage(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** show"+elementClassName+"WizardPage()");
		buf.putLine2("  ** Goes to the "+elementClassName+" Wizard Page.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"show"+elementClassName+"WizardPage\" ");
        buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	action=\"#{"+elementNameUncapped+"Wizard.refresh}\"");
		buf.putLine2("	onbegin=\"setCursorWait(event.source); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" information...')\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are passed-in -->");
		buf.putLine2("	<a4j:param name=\"section\" assignTo=\"#{"+elementNameUncapped+"Wizard.section}\" />");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are assigned here -->");
		buf.putLine2("	<a4j:param name=\"origin\" value=\"#{selectionContext.origin}\" assignTo=\"#{"+elementNameUncapped+"Wizard.origin}\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}

	protected String getShowElementDialog(Element element, boolean newMode) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String mode = newMode ? "New" : "Edit";
		String modeUncapped = NameUtil.uncapName(mode);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** show"+mode+elementClassName+"Dialog()");
		buf.putLine2("  ** Launches the "+elementClassName+" Dialog in \""+modeUncapped+"\" mode.");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"show"+mode+elementClassName+"Dialog\" ");
        buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{"+elementNameUncapped+"InfoManager."+modeUncapped+elementClassName+"}\"");
		if (newMode)
			buf.putLine2("	onbegin=\"setCursorWait(event.source); showProgress('Nam', '"+elementClassName+" Records', 'Creating new "+elementClassName+"...')\"");
		else buf.putLine2("	onbegin=\"setCursorWait(event.source); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" information...')\"");
		buf.putLine2("	oncomplete=\"setCursorDefault(this); hideProgress(); show"+elementClassName+"Dialog()\"");
		buf.putLine2("	render=\""+elementNameUncapped+"Module, "+elementNameUncapped+"Dialog\">");
		buf.putLine2("	");
		buf.putLine2("	<!-- these values are assigned here -->");
		buf.putLine2("	<a4j:param name=\"targetDomain\" value=\"#{section}\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetDomain}\" />");
		buf.putLine2("	<a4j:param name=\"targetField\" value=\""+elementNameUncapped+"ListPane\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetField}\" />");
		buf.putLine2("	<a4j:param name=\"targetInstance\" value=\"#{instanceName}\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetInstance}\" />");
		buf.putLine2("</a4j:jsFunction>");
		return buf.get();
	}
	
	protected String getExecuteActionNoDelay(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine2("	");
		buf.putLine2("	");
		buf.putLine2("<!--");
		buf.putLine2("  ** executeActionNoDelay(action, type)");
		buf.putLine2("  ** Creates new Element, and goes to Element creation Page..");
		buf.putLine2("  -->");
		buf.putLine2("	");
		buf.putLine2("<a4j:jsFunction ");
		buf.putLine2("	name=\"executeActionNoDelay\"");
		buf.putLine2("	execute=\"@this\"");
		buf.putLine2("	immediate=\"true\"");
		buf.putLine2("	bypassUpdates=\"true\"");
		buf.putLine2("	limitRender=\"true\"");
		buf.putLine2("	action=\"#{workspaceManager.executeAction}\"");
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
		buf.putLine2("	<!-- this provides queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"ListClickEvents\" requestDelay=\"400\" />");
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
		buf.putLine2("	<!-- this provides queue settings -->");
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
		buf.putLine2("	<!-- this provides queue settings -->");
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
		buf.putLine2("	<!-- this provides queue settings -->");
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
		buf.putLine2("	<!-- this provides queue settings -->");
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
        buf.putLine2("	<!-- these values are assigned here -->");
        buf.putLine2("	<a4j:param name=\"nodeId\" assignTo=\"#{"+elementNameUncapped+"TreeManager.toggledNodeState}\" /> ");
		buf.putLine2("	");
        buf.putLine2("	<!-- provide queue settings -->");
        buf.putLine2("	<a4j:attachQueue requestGroupingId=\""+elementNameUncapped+"TreeClickEvents\" /> ");
        buf.putLine2("</a4j:jsFunction>");
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
		buf.putLine4("	enable"+elementClassName+"ListActions();");
		buf.putLine4("	try {");
		buf.putLine4("		processSelect"+elementClassName+"ListNode(rowIndex, recordKey);");
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
		buf.putLine4("  ** Handles double-click event on the "+elementClassName+" List.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"ListDoubleClick(event, rowIndex, recordKey, recordLabel) {");
		buf.putLine4("	update"+elementClassName+"ListState(event, rowIndex, recordKey, recordLabel);");
		buf.putLine4("	enable"+elementClassName+"ListActions();");
		buf.putLine4("	");
		buf.putLine4("}");
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
		buf.putLine4("  ** enable"+elementClassName+"ListActions()");
		buf.putLine4("  ** Enables (or disables) "+elementClassName+" List actions based on current client-side state.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function enable"+elementClassName+"ListActions() {");
		buf.putLine4("	//enableButton(\'pageForm:"+elementNameUncapped+"ListViewButton\');");
		buf.putLine4("	//enableButton(\'pageForm:"+elementNameUncapped+"ListNewButton\');");
		buf.putLine4("	//enableButton(\'pageForm:"+elementNameUncapped+"ListEditButton\');");
		buf.putLine4("	//enableButton(\'pageForm:"+elementNameUncapped+"ListMoveButton\');");
		buf.putLine4("	//enableButton(\'pageForm:"+elementNameUncapped+"ListRemoveButton\');");
		buf.putLine4("}");
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
		buf.putLine4("			var nodeType = \'#{"+elementNameUncapped+"TreeManager.selectedNode.type}\';");
		buf.putLine4("			var nodeLabel = \'#{"+elementNameUncapped+"TreeManager.selectedNode.label}\';");
		buf.putLine4("			update"+elementClassName+"TreeState(null, rowKey, nodeId, nodeType, nodeLabel);");
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
		buf.putLine4("  ** update"+elementClassName+"TreeState(event, rowKey, nodeId, nodeType, nodeLabel)");
		buf.putLine4("  ** Updates client-side state information for "+elementClassName+" Tree.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function update"+elementClassName+"TreeState(event, rowKey, nodeId, nodeType, nodeLabel) {");
		buf.putLine4("	"+elementNameUncapped+"TreeState = new Object();");
		buf.putLine4("	"+elementNameUncapped+"TreeState.rowKey = rowKey;");
		buf.putLine4("	"+elementNameUncapped+"TreeState.nodeId = nodeId;");
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
		buf.putLine4("  ** enable"+elementClassName+"TreeActions()");
		buf.putLine4("  ** Enables (or disables) "+elementClassName+" Tree actions based on current client-side state.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function enable"+elementClassName+"TreeActions() {");
		buf.putLine4("	enableButton(\'pageForm:"+elementNameUncapped+"TreeViewButton\');");
		buf.putLine4("	enableButton(\'pageForm:"+elementNameUncapped+"TreeNewButton\');");
		buf.putLine4("	enableButton(\'pageForm:"+elementNameUncapped+"TreeEditButton\');");
		buf.putLine4("	enableButton(\'pageForm:"+elementNameUncapped+"TreeMoveButton\');");
		buf.putLine4("	enableButton(\'pageForm:"+elementNameUncapped+"TreeRemoveButton\');");
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
		buf.putLine4("  ** process"+elementClassName+"TreeMouseDown(event, rowKey, nodeId, nodeType, nodeLabel)");
		buf.putLine4("  ** Handles mouseDown event on the "+elementClassName+" Tree.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function get"+elementClassName+"TreeNodeLabel(event, rowKey, nodeId, nodeType, nodeLabel) {");
		buf.putLine4("	update"+elementClassName+"TreeState(event, rowKey, nodeId, nodeType, nodeLabel);");
		buf.putLine4("	enable"+elementClassName+"TreeActions();");
		buf.putLine4("	try {");
		buf.putLine4("		processSelect"+elementClassName+"TreeNode(rowKey, nodeId);");
		buf.putLine4("		if (event.which == 3) {");
		
		List<String> subTypes = element.getSubTypes();
		Iterator<String> iterator = subTypes.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String subType = iterator.next();
			if (i == 0)
				buf.putLine4("			if (nodeType == '"+subType+"') {");
			else buf.putLine4("			} else if (nodeType == '"+subType+"') {");
			buf.putLine4("				show"+subType+"NodeMenu(event);");
		}
		buf.putLine4("			}");
		buf.putLine4("		}");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
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
		buf.putLine4("  ** process"+elementClassName+"TreeDoubleClick(event, rowKey, nodeId, nodeType, nodeLabel)");
		buf.putLine4("  ** Handles double-click event on the "+elementClassName+" Tree.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function process"+elementClassName+"TreeDoubleClick(event, rowKey, nodeId, nodeType, nodeLabel) {");
		buf.putLine4("	update"+elementClassName+"TreeState(null, rowKey, nodeId, nodeType, nodeLabel);");
		buf.putLine4("	enable"+elementClassName+"TreeActions();");
		buf.putLine4("	");
		buf.putLine4("	if (nodeType == 'Project') {");
		buf.putLine4("		processProjectNodeDoubleClick(event, rowKey, nodeId);");
		buf.putLine4("	} else if (nodeType == 'Application') {");
		buf.putLine4("		processApplicationNodeDoubleClick(event, rowKey, nodeId);");
		buf.putLine4("	} else if (nodeType.endsWith('MODULE')) {");
		buf.putLine4("		processModuleNodeDoubleClick(event, rowKey, nodeId);");
		buf.putLine4("	} else {");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

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
		buf.putLine4("		showProgress('Nam', '"+targetName+" #{"+targetNameUncapped+"InfoManager.selected"+targetName+".name}', 'Preparing "+targetName+" \"Summary\" view...')");
		//buf.putLine4("		//showPage(source, '/nam/model/application/application.seam?section='+section);");
		buf.putLine4("		view"+targetName+"('Overview', rowKey, nodeId);");
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
		buf.putLine4("  ** show"+targetName+"NodeMenu(event)");
		buf.putLine4("  ** Displays context-menu for "+targetName+" Tree node..");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function show"+targetName+"NodeMenu(event) {");
		//buf.putLine4("	//alert("+elementNameUncapped+"TreeState.nodeLabel)");
		//buf.putLine4("	//TODO This currently has no effect, we are waiting for when it will work");
		buf.putLine4("	var menu = #{rich:component('"+targetNameUncapped+"TreeMenu')};");
		buf.putLine4("	menu.show(event, {});");
		//buf.putLine4("	menu.show(event, {");
		//buf.putLine4("		'projectName': 'project1',"); 
		//buf.putLine4("		'canAdd': 'false'");
		//buf.putLine4("	});");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessViewElement(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processView"+targetName+"(event)");
		buf.putLine4("  ** Opens selected "+targetName+" record.  Goes to the "+targetName+" Summary page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processView"+targetName+"(event) {");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('applicationTree')});");
		buf.putLine4("		showProgress('Nam', '"+targetName+" Records', 'Opening "+targetName+" for viewing...')");
		buf.putLine4("		viewProject(event);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessNewElement(Element element, String parentName, String targetName) {
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String parentNameUncapped = NameUtil.uncapName(parentName);
		//String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processNew"+targetName+"(event)");
		buf.putLine4("  ** Creates new "+targetName+" record.  Goes to the "+targetName+" Wizard page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processNew"+targetName+"(event) {");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		if (parentName != null) {
			buf.putLine4("		if ("+elementNameUncapped+"TreeState.nodeLabel != null) {");
			buf.putLine4("			var label = "+elementNameUncapped+"TreeState.nodeLabel;");
			buf.putLine4("			showProgress('Nam', '"+targetName+" Records'. 'Creating new "+targetName+" for "+parentName+" \\\"' + label + '\\\"...')");
			buf.putLine4("		} else showProgress('Nam', '"+targetName+" Records', 'Creating new "+targetName+"...');");
		} else {
			buf.putLine4("		showProgress('Nam', '"+targetName+" Records', 'Creating new "+targetName+"...');");
			//buf.putLine4("	showProgress('Nam', 'New "+elementClassName+"', 'Creating new "+elementClassName+"...')");
		}
		buf.putLine4("		new"+targetName+"(event);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessEditElement(Element element, String parentName, String targetName) {
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String targetNameUncapped = NameUtil.uncapName(targetName);		
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processEdit"+targetName+"(event)");
		buf.putLine4("  ** Opens "+targetName+" for editing.  Goes to the "+targetName+" Wizard page.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processEdit"+targetName+"(event) {");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		if (parentName != null) {
			buf.putLine4("		if ("+elementNameUncapped+"TreeState.nodeLabel != null) {");
			buf.putLine4("			var label = "+elementNameUncapped+"TreeState.nodeLabel;");
			buf.putLine4("			showProgress('Nam', '"+targetName+" Records'. 'Opening "+targetName+" for "+parentName+" \\\"' + label + '\\\"...')");
			buf.putLine4("		} else showProgress('Nam', '"+targetName+" Records', 'Opening "+targetName+"...');");
		} else {
			buf.putLine4("		showProgress('Nam', '"+targetName+" Records', 'Opening "+targetName+"...');");
			//buf.putLine4("	showProgress('Nam', 'New "+elementClassName+"', 'Creating new "+elementClassName+"...')");
		}
		buf.putLine4("		edit"+targetName+"(event);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessSaveElement(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processSave"+targetName+"(event)");
		buf.putLine4("  ** Saves selected "+targetName+" to server-side storage.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processSave"+targetName+"(event) {");
		buf.putLine4("	try {");
		buf.putLine4("		setCursorWait(event.target);"); 
		buf.putLine4("		setCursorWait(event.currentTarget);"); 
		buf.putLine4("		setCursorWait(#{rich:element('"+elementNameUncapped+"Tree')});");
		buf.putLine4("		showProgress('Nam', '"+targetName+" Records', 'Saving "+targetName+" record...')");
		buf.putLine4("		save"+targetName+"(event);");
		buf.putLine4("	} catch(e) {");
		buf.putLine4("		alert(e);");
		buf.putLine4("	}");		
		buf.putLine4("}");
		return buf.get();
	}

	protected String generateProcessRemoveElement(Element element, String targetName) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String targetNameUncapped = NameUtil.uncapName(targetName);
		
		Buf buf = new Buf();
		buf.putLine4("	");
		buf.putLine4("	");
		buf.putLine4("<!--");
		buf.putLine4("  ** processRemove"+targetName+"(event)");
		buf.putLine4("  ** Prompts user to remove "+targetName+" record.  Removes "+targetName+" record from system.");
		buf.putLine4("  -->");
		buf.putLine4("	");
		buf.putLine4("function processRemove"+targetName+"(event) {");
		buf.putLine4("	setCursorWait(event.source);"); 
		buf.putLine4("	var nodeLabel = get"+elementClassName+"TreeNodeLabel();");
		buf.putLine4("	var warningTitle = 'Remove \"' + nodeLabel + '\" "+targetName+" from system';");
		buf.putLine4("	var removeEvent = '"+targetNameUncapped+"EventManager.remove"+targetName+"';");
		//buf.putLine4("	//var removeEvent = typeUncapped+'EventManager.fireRemoveEvent';");
		//buf.putLine4("	//var removeEvent = 'nam.model.'+typeUncapped+'.'+type+'RemoveEvent';");
		buf.putLine4("	popupWarningPrompt('Nam', warningTitle, 'Do you wish to continue?', removeEvent, '"+elementNameUncapped+"Tree');");
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
