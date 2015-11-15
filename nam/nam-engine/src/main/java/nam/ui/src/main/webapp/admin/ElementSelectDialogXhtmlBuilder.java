package nam.ui.src.main.webapp.admin;

import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementSelectDialogXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementSelectDialogXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Type type) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(type);
		String folderName = ModelLayerHelper.getElementWebappFolder(type);
		String fileName = elementName + "SelectDialog.xhtml";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(type));
		return modelFile;
	}
	
	public String getFileContent(Type type) {
		String typeClassName = ModelLayerHelper.getElementClassName(type);
		String typeNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String typeNameCapped = ModelLayerHelper.getElementNameCapped(type);
		
		int panelWidth = 560;
		int panelHeight = 240;
		
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
		buf.putLine("	 	name=\"launch"+typeClassName+"SelectDialog\"");
		buf.putLine("		execute=\"@this\"");
		buf.putLine("		immediate=\"true\""); 
		buf.putLine("		bypassUpdates=\"true\"");
		buf.putLine("		limitRender=\"true\"");
		buf.putLine("		action=\"#{"+typeNameUncapped+"SelectManager.show}\"");
		buf.putLine("		onbegin=\"setCursorWait(this); showProgress('', '"+typeClassName+" Records', 'Retrieving current records...')\"");
		buf.putLine("		oncomplete=\"setCursorDefault(this); hideProgress(); show"+typeClassName+"SelectDialog()\"");
		buf.putLine("		render=\""+typeNameUncapped+"SelectModule, "+typeNameUncapped+"SelectDialog\">");
		buf.putLine("		");
		buf.putLine("		<a4j:param name=\"targetDomain\" assignTo=\"#{"+typeNameUncapped+"SelectManager.targetDomain}\" />");
		buf.putLine("		<a4j:param name=\"targetField\" assignTo=\"#{"+typeNameUncapped+"SelectManager.targetField}\" />");
		buf.putLine("		<a4j:param name=\"targetInstance\" assignTo=\"#{"+typeNameUncapped+"SelectManager.targetInstance}\" />");
		buf.putLine("	</a4j:jsFunction>");
		buf.putLine("	");
		buf.putLine("	<a4j:outputPanel"); 
		buf.putLine("		id=\""+typeNameUncapped+"Module\">");
		buf.putLine("		");
		buf.putLine("		<a4j:region"); 
		buf.putLine("			rendered=\"#{"+typeNameUncapped+"SelectManager.visible}\">");
		buf.putLine("			");
		buf.putLine("			<aries:dialog");
		buf.putLine("				id=\""+typeClassName+"Dialog\">");
		//buf.putLine("				action=\"submit\"");
		buf.putLine("				render=\"#{"+typeNameUncapped+"SelectManager.renderList} "+typeNameUncapped+"SelectPane\">");
		buf.putLine("				");
		buf.putLine("				<!-- border -->");
		buf.putLine("				<aries:borderPane");
		buf.putLine("					width=\""+panelWidth+"\"");
		buf.putLine("					height=\""+panelHeight+"\">");
		buf.putLine("					");
		buf.putLine("					<!-- dialog content -->");
		buf.putLine("					<ui:include src=\""+typeNameUncapped+"SelectPane.xhtml\" />");
		buf.putLine("				</aries:borderPane>");
		buf.putLine("			</aries:dialog>");
		buf.putLine("		</a4j:region>");
		buf.putLine("	</a4j:outputPanel>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
}
