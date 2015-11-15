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


public class ElementListPaneXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListPaneXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListPane.xhtml";

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
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		buf.putLine("	<aries:outputPane");
		buf.putLine("		id=\""+elementNameUncapped+"ListPane\">");
		buf.putLine("		");
		//buf.putLine("		<ui:param name=\"element\" value=\""+elementClassName+"\" />");
		//buf.putLine("		<ui:param name=\"instanceName\" value=\""+elementNameUncapped+"\" />");
		//buf.putLine("		<ui:param name=\"unit\" value=\"#{domain}#{element}\" />");
		//buf.putLine("		");
		//buf.putLine("		<ui:include src=\""+elementNameUncapped+"Actions.xhtml\" />");
		buf.putLine("		<ui:include src=\""+elementNameUncapped+"ListToolbar.xhtml\" />");
		buf.putLine("		<ui:include src=\""+elementNameUncapped+"ListTable.xhtml\" />");
		buf.putLine("	</aries:outputPane>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
}
