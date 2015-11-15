package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ElementUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementListDialogXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListDialogXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListDialog.xhtml";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}

	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		
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
		buf.putLine("		action=\"#{"+elementNameUncapped+"ListManager.show}\"");
		buf.putLine("		onbegin=\"setCursorWait(); showProgress('', '"+elementClassName+" Records', 'Retrieving current records...')\"");
		buf.putLine("		oncomplete=\"setCursorDefault(eventSource); hideProgress(); show"+elementClassName+"Dialog()\"");
		buf.putLine("		render=\""+elementClassName+"Module, "+elementClassName+"Dialog\">");
		buf.putLine("		");
		buf.putLine("		<a4j:param name=\"targetInstance\" assignTo=\"#{"+elementNameUncapped+"ListManager.targetInstance}\" />");
		buf.putLine("		<a4j:param name=\"targetDomain\" assignTo=\"#{"+elementNameUncapped+"ListManager.targetDomain}\" />");
		buf.putLine("		<a4j:param name=\"targetField\" assignTo=\"#{"+elementNameUncapped+"ListManager.targetField}\" />");
		buf.putLine("	</a4j:jsFunction>");
		buf.putLine("	");
		buf.putLine("	<a4j:outputPanel"); 
		buf.putLine("		id=\""+elementNameUncapped+"Module\">");
		buf.putLine("		");
		buf.putLine("		<a4j:region"); 
		buf.putLine("			rendered=\"#{"+elementNameUncapped+"InfoManager.visible}\">");
		buf.putLine("			");

		//buf.putLine("	<aries:region");
		//buf.putLine("		id=\""+elementClassName+"Module\"");
		//buf.putLine("		parent=\"#{section}\"");
		//buf.putLine("		domain=\""+elementClassName+"\"");
		//buf.putLine("		section=\""+elementClassName+"Dialog\"");
		////buf.putLine("		render=\""+elementClassName+"ListPane\"");
		//buf.putLine("		rendered=\"#{"+elementNameUncapped+"ListManager.visible}\">");
		//buf.putLine("		");
		//buf.putLine("		<aries:form");
		//buf.putLine("			id=\"#{section}Form\">");
		//buf.putLine("			");
		
		buf.putLine("			<aries:dialog");
		buf.putLine("				id=\""+elementNameUncapped+"Dialog\">");
		buf.putLine("				");
		buf.putLine("				<!-- Container -->");
		buf.putLine("				<aries:borderPane");
		buf.putLine("					width=\"560\"");
		buf.putLine("					height=\"240\">");
		buf.putLine("					");
		buf.putLine("					<!-- Content -->");
		buf.putLine("					<ui:include src=\""+elementNameUncapped+"ListPane.xhtml\">");
		buf.putLine("						<ui:param name=\"itemManager\" value=\"#{"+elementNameUncapped+"InfoManager}\" />");
		
		if (ElementUtil.isRoot(element)) {
			buf.putLine("						<ui:param name=\"listManager\" value=\"#{"+elementNameUncapped+"ListManager}\" />");
		} else {
			buf.putLine("						<ui:param name=\"listManager\" value=\"#{"+elementNameUncapped+"ListManager}\" />");
		}
		
		buf.putLine("					</ui:include>");
		buf.putLine("				</aries:borderPane>");
		buf.putLine("			</aries:dialog>");
		//buf.putLine("			</aries:form>");
		buf.putLine("		</aries:region>");
		buf.putLine("	</a4j:outputPanel>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
}
