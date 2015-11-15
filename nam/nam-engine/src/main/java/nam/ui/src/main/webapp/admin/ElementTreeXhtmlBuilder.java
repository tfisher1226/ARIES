package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementTreeXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementTreeXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "Tree.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		String elementFileContent = getElementFileContent(element);
		String xhtmlFileContent = getXhtmlFileContent(elementFileContent);
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
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClass = ModelLayerHelper.getElementClassName(element);
		
		Buf buf = new Buf();
		buf.putLine1("<a4j:region>");
		buf.putLine1("	<aries:tree");
		buf.putLine1("		id=\""+elementName+"Tree\"");
		buf.putLine1("		manager=\"#{"+elementName+"TreeManager}\">");
		buf.putLine1("		");
		buf.putLine1("		<ui:include src=\"../modelTreeNodes.xhtml\">");
		buf.putLine1("			<ui:param name=\"ontoggle\" value=\"processToggle"+elementClass+"TreeNode('#{item.id}')\" />");
		buf.putLine1("			<ui:param name=\"onmousedown\" value=\"process"+elementClass+"TreeMouseDown(event, '#{rowKey}', '#{item.id}', '#{item.area}', '#{item.type}', '#{item.label}')\" />");
		buf.putLine1("			<ui:param name=\"ondblclick\" value=\"process"+elementClass+"TreeDoubleClick(event, '#{rowKey}', '#{item.id}', '#{item.area}', '#{item.type}', '#{item.label}')\" />");
		buf.putLine1("			<ui:param name=\"renderListXX\" value=\""+elementName+"TreeMenu, "+elementName+"TreeToolbar\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</aries:tree>");
		buf.putLine1("</a4j:region>");
		return buf.get();
	}
	
}
