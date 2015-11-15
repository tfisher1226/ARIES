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


public class ElementWizardPageXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementWizardPageXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "WizardPage.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		String elementFileContent = getElementFileContent(element);
		String xhtmlFileContent = getXhtmlFileContent(elementFileContent);
		modelFile.setTextContent(xhtmlFileContent);
		return modelFile;
	}

	public String getXhtmlFileContent(String xhtmlContent) throws Exception {
		Buf buf = new Buf();
		buf.put(generateCompositionXhtmlOpen());
		buf.put(generateCompositionXhtmlHeader("main.xhtml"));
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
		buf.putLine1("<!-- page includes -->");
		buf.putLine1("<ui:define name=\"pageIncludes\">");
		buf.putLine1("	<ui:include src=\""+elementName+"Actions.xhtml\" />");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page title -->");
		buf.putLine1("<ui:define name=\"pageTitle\">");
		buf.putLine1("	<span class=\"pageTitle\">");
		buf.putLine1("		<ui:include src=\"/common/pageTitle.xhtml\">");
		buf.putLine1("			<ui:param name=\"manager\" value=\"#{"+elementName+"PageManager}\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page controls -->");
		buf.putLine1("<ui:define name=\"pageControls\">");
		buf.putLine1("	<ui:include src=\"/common/pageControls.xhtml\" />");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page breadcrumbs -->");
		buf.putLine1("<ui:define name=\"pageBreadcrumbs\">");
		buf.putLine1("	<span class=\"pageBreadcrumbs\">");
		buf.putLine1("		<ui:include src=\"/common/breadcrumbs.xhtml\">");
		buf.putLine1("			<ui:param name=\"breadcrumbs\" value=\"#{"+elementName+"PageManager.getBreadcrumbs('"+elementName+"')}\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- navigator -->");
		buf.putLine1("<ui:define name=\"navigator\">");
		buf.putLine1("	<span class=\"navigator\">");
		buf.putLine1("		<ui:include src=\""+elementName+"WizardNavigator.xhtml\" />");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- wizard breadcrumbs -->");
		buf.putLine1("<ui:define name=\"sectionBreadcrumbs\">");
		buf.putLine1("	<span class=\"sectionBreadcrumbs\">");
		buf.putLine1("		<ui:include src=\"/common/breadcrumbs.xhtml\">");
		buf.putLine1("			<ui:param name=\"breadcrumbs\" value=\"#{"+elementName+"PageManager.getBreadcrumbs('"+elementName+"Wizard')}\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- wizard title -->"); 
		buf.putLine1("<ui:define name=\"wizardTitle\">");
		buf.putLine1("	<span class=\"wizardTitle\">");
		buf.putLine1("		<ui:include src=\"/common/wizardTitle.xhtml\">");
		buf.putLine1("			<ui:param name=\"manager\" value=\"#{"+elementName+"PageManager}\" />");
		buf.putLine1("			<ui:param name=\"menu\" value=\"/"+elementPath+"/"+elementName+"/"+elementName+"WizardMenu.xhtml\" />");
		buf.putLine1("		</ui:include>");
        buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- wizard messages -->"); 
		buf.putLine1("<ui:define name=\"wizardMessages\">");
		buf.putLine1("	<span class=\"wizardMessages\">");
		buf.putLine1("		<ui:include src=\"/common/wizardMessages.xhtml\">");
		buf.putLine1("			<ui:param name=\"domain\" value=\""+elementName+"Wizard\" />");
		buf.putLine1("			<ui:param name=\"message\" value=\"Enter "+elementClass+" #{"+elementName+"Wizard.section}\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- content -->");
		buf.putLine1("<ui:define name=\"content\">");
		buf.putLine1("	<div class=\"content\"");
		buf.putLine1("		id=\""+elementName+"Wizard\">");
		buf.putLine1("		");
		buf.putLine1("		<aries:formPane");
		buf.putLine1("			columns=\"2\">");
		buf.putLine1("			");
		buf.putLine1("			<!-- wizard content -->");			
		buf.putLine1("			<aries:panel"); 
		buf.putLine1("				width=\"#{helper.wizard_middleSection_width - 16}\"");
		buf.putLine1("				height=\"#{helper.wizard_middleSection_height}\"");
		buf.putLine1("				borderWidth=\"1px\"");
		buf.putLine1("				borderColor=\"#{userSkin.borderColor}\"");
		buf.putLine1("				backgroundColor=\"white\">");
		buf.putLine1("				");
		buf.putLine1("				<!-- variables -->");
		buf.putLine1("				<ui:param name=\"fontSize\" value=\"13\" />");
		buf.putLine1("				<ui:param name=\"fontWeight\" value=\"normal\" />");
		buf.putLine1("				<ui:param name=\"width\" value=\"#{helper.wizard_middleSection_width - 4}\" />");
		buf.putLine1("				<ui:param name=\"height\" value=\"#{helper.wizard_middleSection_height}\" />");
		buf.putLine1("				");
		buf.putLine1("				<!-- wizard panel -->"); 
		buf.putLine1("				<ui:include src=\""+elementName+"Record_#{"+elementName+"Wizard.section}Section.xhtml\">");
		buf.putLine1("					<ui:param name=\"section\" value=\""+elementName+"\" />");
		buf.putLine1("				</ui:include>");
		buf.putLine1("				");
		buf.putLine1("				<!-- wizard control -->");
		buf.putLine1("				<ui:include src=\""+elementName+"WizardControl.xhtml\" />");
		buf.putLine1("			</aries:panel>");
		buf.putLine1("			");					
		buf.putLine1("			<!-- help -->");
		buf.putLine1("			<ui:include src=\"/common/helpSidebar.xhtml\">");
		buf.putLine1("				<ui:param name=\"layout\" value=\"other\" />");
		buf.putLine1("				<ui:param name=\"domain\" value=\""+elementName+"\" />");
		buf.putLine1("				<ui:param name=\"section\" value=\"#{"+elementName+"Wizard.section}\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("		</aries:formPane>");
		buf.putLine1("	</div>");
		buf.putLine1("</ui:define>");
		return buf.get();
	}
	
}
