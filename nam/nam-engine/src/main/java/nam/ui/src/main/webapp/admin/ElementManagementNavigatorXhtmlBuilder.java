package nam.ui.src.main.webapp.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementManagementNavigatorXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementManagementNavigatorXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ManagementNavigator.xhtml";

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
		buf.putLine1("<aries:panel");
		buf.putLine1("	width=\"auto\"");
		buf.putLine1("	margin=\"0px\"");
		buf.putLine1("	backgroundColor=\"inherit\">");
		buf.putLine1("	");
		buf.putLine1("	<aries:formPane");
		buf.putLine1("		width=\"auto\"");
		buf.putLine1("		borderWidth=\"0px\"");
		buf.putLine1("		borderColor=\"#aaa\"");
		buf.putLine1("		columnClass=\"formColumnAlignLeft\"");
		buf.putLine1("		backgroundColor=\"inherit\">");
		
		List<String> subsections = new ArrayList<String>();
		Iterator<String> iterator = subsections.iterator();
		while (iterator.hasNext()) {
			String subSection = iterator.next();
			String subSectionCapped = NameUtil.capName(subSection);
			String subSectionUncapped = NameUtil.uncapName(subSection);
			
			buf.putLine1("		");
			buf.putLine1("		<aries:spacer height=\"10\" />");
			buf.putLine1("		<ui:include src=\"/nam/navigationButton.xhtml\">");
			buf.putLine1("			<ui:param name=\"label\" value=\""+subSectionCapped+"\" />");
			buf.putLine1("			<ui:param name=\"icon\" value=\"/icons/nam/"+subSectionCapped+"16.gif\" />");
			buf.putLine1("			<ui:param name=\"id\" value=\"application"+subSectionCapped+"Button\" />");
			buf.putLine1("			<ui:param name=\"manager\" value=\"#{"+subSectionUncapped+"PageManager}\" />");
			buf.putLine1("			<ui:param name=\"action\" value=\"initialize"+subSectionCapped+"ManagementPage\" />");
			buf.putLine1("			<ui:param name=\"onmouseup\" value=\"setCursorWait(event.source); showProgress('Nam', '"+subSectionCapped+" Records', 'Preparing "+subSectionCapped+" information...')\" />");
			buf.putLine1("		</ui:include>");
		}

		buf.putLine1("	</aries:formPane>");
        buf.putLine1("</aries:panel>");
		return buf.get();
	}
	
}
