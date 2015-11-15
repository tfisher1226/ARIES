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


public class ElementInfoDialogXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementInfoDialogXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "InfoDialog.xhtml";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String applicationName = "Nam";
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		buf.putLine("	");
		buf.putLine("	<a4j:jsFunction"); 
		buf.putLine("	 	name=\"launch"+elementClassName+"Dialog\"");
		buf.putLine("		execute=\"@this\"");
		buf.putLine("		immediate=\"true\""); 
		buf.putLine("		bypassUpdates=\"true\"");
		buf.putLine("		limitRender=\"true\"");
		buf.putLine("		action=\"#{"+elementNameUncapped+"InfoManager.activate}\"");
		buf.putLine("		onbegin=\"setCursorWait(); showProgress('"+applicationName+"', '"+elementClassName+" Records', 'Preparing requested record...')\"");
		buf.putLine("		oncomplete=\"setCursorDefault(eventSource); hideProgress(); show"+elementClassName+"Dialog()\"");
		buf.putLine("		render=\""+elementNameUncapped+"Module, "+elementNameUncapped+"Dialog\">");
		buf.putLine("		");
		buf.putLine("		<a4j:param name=\"targetDomain\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetDomain}\" />");
		buf.putLine("		<a4j:param name=\"targetField\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetField}\" />");
		buf.putLine("		<a4j:param name=\"targetInstance\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetInstance}\" />");
		buf.putLine("		<a4j:param name=\"targetService\" assignTo=\"#{"+elementNameUncapped+"InfoManager.targetService}\" />");
		buf.putLine("		<a4j:param name=\"immediate\" assignTo=\"#{"+elementNameUncapped+"InfoManager.immediate}\" value=\"false\" />");
		buf.putLine("	</a4j:jsFunction>");
		buf.putLine("	");
		buf.putLine("	<a4j:outputPanel"); 
		buf.putLine("		id=\""+elementNameUncapped+"Module\">");
		buf.putLine("		");
		buf.putLine("		<a4j:region"); 
		buf.putLine("			rendered=\"#{"+elementNameUncapped+"InfoManager.visible}\">");
		buf.putLine("			");
		//buf.putLine("		<aries:form");
		//buf.putLine("			id=\"#{section}Form\">");
		//buf.putLine("			");
		buf.putLine("			<aries:dialog");
		buf.putLine("				id=\""+elementNameUncapped+"Dialog\"");
		buf.putLine("				action=\"save"+elementClassName+"\"");
		buf.putLine("				render=\"#{"+elementNameUncapped+"InfoManager.renderList} "+elementNameUncapped+"ListPane\">");
		buf.putLine("				");				
		buf.putLine("				<!-- Dialog Content -->");
		buf.putLine("				<ui:include src=\""+elementNameUncapped+"InfoPane.xhtml\" />");
		buf.putLine("			</aries:dialog>");
		//buf.putLine("			</aries:form>");
		buf.putLine("		</aries:region>");
		buf.putLine("	</a4j:outputPanel>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
}
