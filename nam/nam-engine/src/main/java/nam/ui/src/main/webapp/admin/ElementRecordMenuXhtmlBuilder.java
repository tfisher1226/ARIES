package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementRecordMenuXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementRecordMenuXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "RecordMenu.xhtml";

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
		String elementPackage = ModelLayerHelper.getElementPackageName(element);
		String elementPath = elementPackage.replace(".", "/");
		
		Buf buf = new Buf();
		buf.putLine1("");
		buf.putLine1("<aries:toolbar type=\"\">");
		buf.putLine1("	<aries:toolbarGroup location=\"right\">");
		buf.putLine1("		<ui:include src=\"/common/addMenuButton.xhtml\">");
		buf.putLine1("			<ui:param name=\"id\" value=\"addMenuButton\" />");
		buf.putLine1("			<ui:param name=\"panel\" value=\"addMenuPanel\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	");
		buf.putLine1("		<h:panelGroup id=\"addMenuPanel\" style=\"display: none\">");
		buf.putLine1("			<ui:include src=\"/common/menuItem.xhtml\">");
		buf.putLine1("				<ui:param name=\"label\" value=\"Component\" />");
		buf.putLine1("				<ui:param name=\"icon\" value=\"/icons/nam/Component16.gif\" />");
		buf.putLine1("				<ui:param name=\"id\" value=\""+elementName+"ComponentButton\" />");
		buf.putLine1("				<ui:param name=\"manager\" value=\"#{componentInfoManager}\" />");
		buf.putLine1("				<ui:param name=\"action\" value=\"newComponent\" />");
		buf.putLine1("				<ui:param name=\"onbegin\" value=\"setCursorWait(event.source); hidePopupMenu(); showProgress('Nam', 'Component Records', 'Creating new Component...')\" />");
		buf.putLine1("				<ui:param name=\"oncomplete\" value=\"setCursorDefault(this); hideProgress()\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("		</h:panelGroup>");
		buf.putLine1("	</aries:toolbarGroup>");
		buf.putLine1("</aries:toolbar>");
		return buf.get();
	}
	
}
