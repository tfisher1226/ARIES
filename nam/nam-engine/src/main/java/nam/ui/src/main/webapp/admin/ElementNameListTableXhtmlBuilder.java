package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementNameListTableXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementNameListTableXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "NameListTable.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String managerName = elementNameUncapped + "ListManager"; 
		boolean hasSubTypes = ElementUtil.hasSubTypes(element);
		if (hasSubTypes)
			managerName = "manager";
		String prefix = getNamePrefix(element);
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:f=\"http://xmlns.jcp.org/jsf/core\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		buf.putLine("	");
		buf.putLine("	<c:if test=\"#{empty addSelectorColumn}\">");
		buf.putLine("		<c:set var=\"addSelectorColumn\" value=\"#{false}\"/>");
		buf.putLine("	</c:if>");
		buf.putLine("	");
		buf.putLine("	<!-- table panel -->");
		buf.putLine("	<aries:outputPane");
		buf.putLine("		id=\""+prefix+"NameListTablePanel\">");
		buf.putLine("		");
		buf.putLine("		<!-- context menu -->");
		buf.putLine("		<ui:include src=\""+elementNameUncapped+"ListMenu.xhtml\">");
		buf.putLine("			<ui:param name=\"tableId\" value=\""+prefix+"NameListTable\" />");
		buf.putLine("		</ui:include>");
		buf.putLine("		");
		buf.putLine("		<!-- table -->");
		buf.putLine("		<aries:table");
		buf.putLine("			id=\""+prefix+"NameListTable\"");
		buf.putLine("			value=\"#{"+managerName+".dataModel}\""); 
		buf.putLine("			rowCount=\"#{"+managerName+".dataModel.rowCount}\"");
		buf.putLine("			visible=\"#{"+managerName+".dataModel.rowCount > 0}\"");
		buf.putLine("			onrowclick=\"process"+elementClassName+"ListMouseDown(event, '#{index}', '#{rowItem.key}', '#{rowItem.label}')\"");
		buf.putLine("			onrowdblclick=\"process"+elementClassName+"ListDoubleClick(event, '#{index}', '#{rowItem.key}', '#{rowItem.label}')\"");
		buf.putLine("			onchange=\"process"+elementClassName+"ListSelectionChanged(event, '#{index}', '#{rowItem.key}', '#{rowItem.label}', '#{target}')\"");
		if (hasSubTypes)
			buf.putLine("			style=\"display: '#{tableDisplay}'; width: auto; max-width: auto; border-top-width: 0px; border-left-width: 0px\"");
		else buf.putLine("			style=\"width: auto; max-width: auto; border-top-width: 0px; border-left-width: 0px\"");
		buf.putLine("			styleClass=\"noTableHeader\"");
		buf.putLine("			tablePanelStyleClass=\"none\"");
		buf.putLine("			rowClasses=\"none\">");
		buf.putLine("			");
		buf.putLine("			<!-- checkbox -->");
		buf.putLine("			<aries:checkBoxColumn");
		buf.putLine("				rendered=\"#{addSelectorColumnn}\" />");
		buf.putLine("			");
		buf.putLine("			<!-- "+elementNameUncapped+" name -->");
		buf.putLine("			<aries:textColumn header=\""+elementClassName+"\">");
		buf.putLine("				<aries:formPane columns=\"2\">");
		buf.putLine("					<h:graphicImage library=\"graphics\" name=\"#{rowItem.icon}\" />");
		buf.putLine("					<h:outputText value=\"#{rowItem."+elementNameUncapped+".name}\" styleClass=\"tableColumn\" />");
		buf.putLine("				</aries:formPane>");
		buf.putLine("			</aries:textColumn>");
		buf.putLine("		</aries:table>");
		buf.putLine("	</aries:outputPane>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}

	protected String getNamePrefix(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		boolean hasSubTypes = ElementUtil.hasSubTypes(element);
		String prefix = null;
		if (hasSubTypes)
			prefix = "#{type}" + elementClassName;
		else prefix = elementNameUncapped;
		return prefix;
	}
	
}
