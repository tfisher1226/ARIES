package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementSelectPaneXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementSelectPaneXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "SelectPane.xhtml";
		
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
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		buf.putLine("	<aries:groupPane");
		buf.putLine("		width=\"380\"");
		buf.putLine("		height=\"140\"");
		buf.putLine("		backgroundColor=\"inherit\"");
		buf.putLine("		label=\""+elementClassName+" Selection\">");
		buf.putLine("		");
		buf.putLine("		<aries:formPane");
		buf.putLine("			columns=\"2\"");
		buf.putLine("			backgroundColor=\"inherit\">");
		buf.putLine("			");
		buf.putLine("			<h:selectManyCheckbox"); 
		buf.putLine("				layout=\"pageDirection\""); 
		buf.putLine("				converter=\""+elementNameUncapped+"Converter\"");
		buf.putLine("				value=\"#{manager.selectedItems1}\""); 
		buf.putLine("				enabledClass=\"\"");
		buf.putLine("				disabledClass=\"\"");
		buf.putLine("				unselectedClass=\"\"");
		buf.putLine("				onclickXX=\"alert('#{domain}')\"");
		buf.putLine("				styleClass=\"formCheckbox\""); 
		buf.putLine("				style=\"padding-top: 2px; padding-left: 6px; background-color: inherit\">");
		buf.putLine("				");
		buf.putLine("				<f:selectItems"); 
		buf.putLine("					var=\""+elementNameUncapped+"Item\"");  
		buf.putLine("					value=\"#{manager.itemList1}\"");
		buf.putLine("					itemLabel=\"#{"+elementNameUncapped+"Helper.toString("+elementNameUncapped+"Item)}\" />");
		buf.putLine("			</h:selectManyCheckbox>");
		buf.putLine("			");
		buf.putLine("			<h:selectManyCheckbox"); 
		buf.putLine("				layout=\"pageDirection\""); 
		buf.putLine("				converter=\""+elementNameUncapped+"Converter\"");
		buf.putLine("				value=\"#{manager.selectedItems2}\""); 
		buf.putLine("				styleClass=\"formCheckbox\""); 
		buf.putLine("				style=\"margin-left: 0px; padding-top: 2px; padding-left: 6px; vertical-align: bottom; background-color: inherit\">");
		buf.putLine("				");
		buf.putLine("				<f:selectItems"); 
		buf.putLine("					var=\""+elementNameUncapped+"Item\"");
		buf.putLine("					value=\"#{manager.itemList2}\""); 
		buf.putLine("					itemLabel=\"#{"+elementNameUncapped+"Helper.toString("+elementNameUncapped+"Item)}\" />");
		buf.putLine("			</h:selectManyCheckbox>");
		buf.putLine("		</aries:formPane>");
		buf.putLine("	</aries:groupPane>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
}
