package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementPanelXHTMLBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementPanelXHTMLBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ElementPanel.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		String elementFileContent = getElementFileContent(element);
		String xhtmlFileContent = getXhtmlFileContent(elementFileContent);
		modelFile.setTextContent(xhtmlFileContent);
		return modelFile;
	}

	public ModelFile buildTypeFile(Information information, Type type) throws Exception {
		String projectPath = information.getName().toLowerCase();
		String elementName = NameUtil.uncapName(type.getName());
		String fileName = elementName + "TypePanel.xhtml";
		String folderName = projectPath + "/elements";
		//String folderName = "/admin/elements";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		String typeFileContent = getTypeFileContent(type);
		String xhtmlFileContent = getXhtmlFileContent(typeFileContent);
		modelFile.setTextContent(xhtmlFileContent);
		return modelFile;
	}
	
	public String getXhtmlFileContent(String xhtmlContent) throws Exception {
		Buf buf = new Buf();
		buf.put(generateCompositionXhtmlOpen());
		buf.put(generateCompositionXhtmlHeader());
		buf.putLine();
		buf.put(xhtmlContent);
		buf.put(generateCompositionXhtmlClose());
		return buf.get();
	}
	
	public String getElementFileContent(Element element) {
		String elementName = NameUtil.uncapName(element.getName());
		
		Buf buf = new Buf();
		buf.putLine1("<aries:layeredPane");
		buf.putLine1("	name=\""+elementName+"Panel\">");
		buf.putLine();
		buf.putLine1("	<aries:label"); 
		buf.putLine1("		height=\"30px\""); 
		buf.putLine1("		marginLeft=\"8px\"");
		buf.putLine1("		text=\""+element.getName()+" Element View\" />");
		buf.putLine();
		buf.putLine1("	<aries:tabPane>");
		buf.putLine1("		<ui:include src=\""+elementName+"ListTab.xhtml\" />");
		buf.putLine1("		<ui:include src=\""+elementName+"StructureTab.xhtml\" />");
		buf.putLine1("	</aries:tabPane>");
		buf.putLine1("</aries:layeredPane>");
		return buf.get();
	}
	
	public String getTypeFileContent(Type type) {
		String typeName = NameUtil.uncapName(type.getName());
		
		Buf buf = new Buf();
		buf.putLine1("<aries:layeredPane");
		buf.putLine1("	name=\""+typeName+"Panel\">");
		buf.putLine();
		buf.putLine1("	<aries:label"); 
		buf.putLine1("		height=\"30px\""); 
		buf.putLine1("		marginLeft=\"8px\"");
		buf.putLine1("		text=\""+type.getName()+" Type View\" />");
		buf.putLine();
		buf.putLine1("	<aries:tabPane>");
		buf.putLine1("		<ui:include src=\""+typeName+"ListTab.xhtml\" />");
		buf.putLine1("	</aries:tabPane>");
		buf.putLine1("</aries:layeredPane>");
		return buf.get();
	}
	
}
