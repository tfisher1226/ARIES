package nam.ui.src.main.webapp.pages;

import java.util.ArrayList;
import java.util.List;

import nam.model.Application;
import nam.model.Project;
import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ServiceListXhtmlBuilder extends AbstractFileBuilder {

	public ServiceListXhtmlBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() throws Exception {
		ModelFile modelXhtmlFile = new ModelFile();
		modelXhtmlFile.setTargetFile("serviceList.xhtml");
		modelXhtmlFile.setTargetFolder("src/main/webapp/pages");
		modelXhtmlFile.setTargetPath(context.getTargetPath() + "/" + modelXhtmlFile.getTargetFolder());
		modelXhtmlFile.setTextContent(getXhtmlContent(false));
		return modelXhtmlFile;
	}
	
	public String getXhtmlContent(boolean isTest) throws Exception {
		Project project = context.getProject();
		Application application = context.getApplication();
		
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine();
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:h=\"http://java.sun.com/jsf/html\"");
		buf.putLine("	xmlns:f=\"http://java.sun.com/jsf/core\"");
		buf.putLine("	xmlns:c=\"http://java.sun.com/jstl/core\"");	
		buf.putLine("	xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
		buf.putLine("	xmlns:s=\"http://jboss.com/products/seam/taglib\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:rich=\"http://richfaces.org/rich\"");
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		buf.putLine();
		buf.putLine1("<script type=\"text/javascript\">");
		buf.putLine1("	var userListTableHighlighter = new org.aries.view.table.UserSelectHighlighter('#fff', '#ddecff', '#eeeeff');");
		buf.putLine1("</script>");
		buf.putLine();
		buf.putLine1("<a4j:outputPanel id=\"userListTablePanel\">");
		buf.putLine1("	<ui:include src=\"userListMenu.xhtml\"/>");
		buf.putLine();
		buf.putLine1("	<rich:panel"); 
		buf.putLine1("		bodyClass=\"panelBody\"");
		buf.putLine1("		style=\"width: auto; height: auto; max-height: #{globals.screenHeight-198}px; padding: 0px; margin: 0px; border-bottom-width: 0px; overflow: auto; overflow-y: scroll\">");
		buf.putLine();
		buf.putLine1("		<h:panelGroup");
		buf.putLine1("			layout=\"block\"");
		buf.putLine1("			rendered=\"#{userListManager.userList.rowCount == 0}\"");
		buf.putLine1("			style=\"width: auto; height: 20px; padding-top: 4px; padding-left: 2px; border: 0px solid #C3BBB6;\">");
		buf.putLine1("			<h:outputText styleClass=\"label\" value=\"No records to display\" style=\"font-size: 12px\"/>");
		buf.putLine1("		</h:panelGroup>");
		buf.putLine();
		buf.putLines(1, generateTable());
		buf.putLine1("	</rich:panel>");
		buf.putLine1("</a4j:outputPanel>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}

	protected List<String> generateTable() {
		List<String> buf = new ArrayList<String>();
		buf.add("<a4j:region>");
		buf.add("	<rich:dataTable"); 
		buf.add("		width=\"100%\"");
		buf.add("		var=\"rowItem\"");
		buf.add("		rowKeyVar=\"index\""); 
		buf.add("		id=\"userListTable\"");
		buf.add("		value=\"#{userListManager.userList}\""); 
		buf.add("		rendered=\"#{userListManager.userList.rowCount > 0}\"");
		buf.add("		onRowMouseOver=\"userListTableHighlighter.processTableRowMouseOver(this)\"");
		buf.add("		onRowMouseOut=\"userListTableHighlighter.processTableRowMouseOut(this)\"");
		buf.add("		selectionMode=\"single\""); 
		buf.add("		sortMode=\"single\""); 
		buf.add("		rowClass=\"tableRow\"");
		buf.add("		headerClass=\"tableHeader\""); 
		buf.add("		activeClass=\"tableRowActive\"");
		buf.add("		selectedClass=\"tableRowSelected\"");
		buf.add("		styleClass=\"table\"");
		buf.add("		style=\"\">");
		buf.add("");		
		buf.add("		<rich:componentControl"); 
		buf.add("			event=\"onRowContextMenu\"");
		buf.add("			for=\"userListMenu\" operation=\"show\">");
		buf.add("			<f:param value=\"#{rowItem.record.firstName}\" name=\"firstName\"/>");
		buf.add("			<f:param value=\"#{rowItem.record.lastName}\" name=\"lastName\"/>");
		buf.add("		</rich:componentControl>");
		buf.add("");		
		buf.add("		<a4j:support"); 
		buf.add("			event=\"onRowDblClick\""); 
		buf.add("			action=\"#{userInfoManager.editUser}\"");
		buf.add("			onsubmit=\"setCursorWait(); showProgress('#{rowItem.record.lastName}, #{rowItem.record.firstName}', 'Preparing for edit...')\"");
		buf.add("			oncomplete=\"setCursorDefault(this); showUserDialog(); hideProgress()\">");
		buf.add("			<f:setPropertyActionListener target=\"#{userInfoManager.user}\" value=\"#{rowItem.record}\" />");
		buf.add("		</a4j:support>");
		buf.add("");		
		buf.add("		<a4j:support"); 
		buf.add("			event=\"onRowMouseDown\""); 
		buf.add("			requestDelay=\"0\"");
		buf.add("			immediate=\"true\"");
		buf.add("			ajaxSingle=\"false\"");
		buf.add("			bypassUpdates=\"true\"");
		buf.add("			limitToList=\"true\"");
		buf.add("			disableDefault=\"true\"");
		buf.add("			onsubmit=\"userListTableHighlighter.processTableRowMouseDown(this)\">");
		buf.add("			<f:setPropertyActionListener target=\"#{userInfoManager.user}\" value=\"#{rowItem.record}\" />");
		buf.add("		</a4j:support>");
		buf.add("");		
		buf.add("		<!-- INDEX -->");
		buf.add("		<rich:column"); 
		buf.add("			width=\"20px\"");
		buf.add("			sortable=\"true\"");
		buf.add("			sortBy=\"#{index}\"");
		buf.add("			filterBy=\"#{index}\"");
		buf.add("			filterEvent=\"onkeyup\">");
		buf.add("			<h:outputText value=\"#{index+1}\"/>");
		buf.add("		</rich:column>");
		buf.add("");		
		buf.add("		<!-- SERVICE NAME -->");
		buf.add("		<rich:column"); 
		buf.add("			width=\"200px\"");
		buf.add("			sortable=\"true\"");
		buf.add("			sortBy=\"#{userHelper.toNameString(rowItem.record)}\"");
		buf.add("			filterBy=\"#{userHelper.toNameString(rowItem.record)}\"");
		buf.add("			filterEvent=\"onkeyup\">");
		buf.add("			<f:facet name=\"header\">");
		buf.add("			<h:outputText value=\"Name\"/>");
		buf.add("			</f:facet>");
		buf.add("				<a4j:commandLink"); 
		buf.add("					immediate=\"true\""); 
		buf.add("					ajaxSingle=\"true\"");
		buf.add("					requestDelay=\"0\"");
		buf.add("					bypassUpdates=\"true\"");
		buf.add("					value=\"#{rowItem.record.lastName}, #{rowItem.record.firstName}\"");
		buf.add("					action=\"#{userListManager.editUser}\"");
		buf.add("					onclick=\"setCursorWait(this); showProgress('#{userHelper.toNameString(rowItem.record)}', 'Preparing for edit...')\"");
		buf.add("					oncomplete=\"setCursorDefault(this); showUserDialog(); Richfaces.hideModalPanel('progressDialog')\"");
		buf.add("					reRender=\"userDialog\">");
		buf.add("					<a4j:actionparam value=\"#{user}\" assignTo=\"#{personBean}\"/>");
		buf.add("				</a4j:commandLink>");
		buf.add("		</rich:column>");
		buf.add("	</rich:dataTable>");
		buf.add("</a4j:region>");
		return buf;
	}
	
}
