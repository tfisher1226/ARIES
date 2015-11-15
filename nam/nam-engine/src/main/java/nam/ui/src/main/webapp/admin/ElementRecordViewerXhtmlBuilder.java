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


public class ElementRecordViewerXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementRecordViewerXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "RecordViewer.xhtml";

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
		buf.putLine1("<a4j:region>");
		buf.putLine1("	<h:panelGroup"); 
		buf.putLine1("		id=\""+elementName+"RecordViewer\"");
		buf.putLine1("		class=\"viewer\">");
		buf.putLine1("		");
		buf.putLine1("		<aries:formPane");
		buf.putLine1("			width=\"100%\"");
		buf.putLine1("			columns=\"2\"");
		buf.putLine1("			marginTop=\"0px\"");
		buf.putLine1("			backgroundColor=\"inherit\">");
		buf.putLine1("			");
		buf.putLine1("			<aries:panel"); 
		buf.putLine1("				width=\"100%\"");
		buf.putLine1("				height=\"#{helper.wizard_middleSection_height}\"");
		buf.putLine1("				borderColor=\"#{userSkin.borderColor}\"");
		buf.putLine1("				borderWidth=\"1px\"");
		buf.putLine1("				backgroundColor=\"inherit\">");
		buf.putLine1("				");
		buf.putLine1("				<!-- PANEL -->"); 
		buf.putLine1("				<ui:param name=\"sectionType\" value=\"#{"+elementName+"PageManager.sectionType}\" />");
		buf.putLine1("				<ui:param name=\"sectionName\" value=\"#{"+elementName+"PageManager.sectionName}\" />");
		buf.putLine1("				<ui:param name=\"sectionFile\" value=\"#{sectionType}Record_#{sectionName}Section.xhtml\" />");
		buf.putLine1("				<ui:include src=\"../#{sectionType}/#{sectionFile}\" />");
		buf.putLine1("			</aries:panel>");
		buf.putLine1("			");
		buf.putLine1("			<!-- HELP -->");
		buf.putLine1("			<ui:include src=\"/common/helpSidebar.xhtml\">");
		buf.putLine1("				<ui:param name=\"domain\" value=\""+elementName+"\" />");
		buf.putLine1("				<ui:param name=\"section\" value=\"#{"+elementName+"PageManager.sectionName}\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("		</aries:formPane>");
		buf.putLine1("	</h:panelGroup>"); 
		buf.putLine1("</a4j:region>");
		return buf.get();
	}
	
}
