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


public class ElementWizardNavigatorXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementWizardNavigatorXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "WizardNavigator.xhtml";

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
		//buf.putLine1("  margin=\"0px\"");
		buf.putLine1("	style=\"border: 1px solid #bbb; border-radius: 5px; background-color: white\">");
        buf.putLine1("	");
		buf.putLine1("	<aries:formPane");
		buf.putLine1("		width=\"auto\"");
		buf.putLine1("		columnClass=\"formColumnAlignLeft\"");
		buf.putLine1("		style=\"width: 130px; background-color: inherit\">");
        buf.putLine1("		");
		buf.putLine1("		<c:set var=\"section\" value=\"#{"+elementName+"Wizard.section}\" /> ");
		buf.putLine1("		<c:set var=\"backgroundColor\" value=\"#cfe7f5\" /> ");
        buf.putLine1("		");
		buf.putLine1("		<aries:indentPane");
		buf.putLine1("			width=\"#{helper.wizard_leftSection_width}\"");
		buf.putLine1("			height=\"auto\"");
		buf.putLine1("			style=\"padding: 6px 10px; font-size: 13px; font-weight: bold; background-color: white; cursor: pointer\">");
		buf.putLine1("			#{"+elementName+"Wizard.newMode ? 'Steps' : 'Sections'}");
		buf.putLine1("		</aries:indentPane>");
        buf.putLine1("		");
		buf.putLine1("		<ui:repeat");
		buf.putLine1("			var=\"page\" ");
		buf.putLine1("			value=\"#{"+elementName+"Wizard.pages}\">");
		buf.putLine1("			");
		buf.putLine1("			<aries:wizardStep");
		buf.putLine1("				step=\"#{section}\"");
		buf.putLine1("				label=\"#{page.name}\"");
		buf.putLine1("				icon=\"#{page.icon}\"");
		buf.putLine1("				enabled=\"#{page.enabled}\"");
		buf.putLine1("				onclick=\"setCursorWait(this); show"+elementClass+"WizardPage('#{page.name}')\"");
		buf.putLine1("				styleClass=\"wizardMenuStep\" />");
		buf.putLine1("		</ui:repeat>");
		buf.putLine1("	</aries:formPane>");
        buf.putLine1("</aries:panel>");
		return buf.get();
	}
	
}
