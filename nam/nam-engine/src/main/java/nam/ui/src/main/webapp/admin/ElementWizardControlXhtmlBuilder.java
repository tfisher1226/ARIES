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


public class ElementWizardControlXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementWizardControlXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "WizardControl.xhtml";

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
		buf.putLine1("<aries:controlPane>");
		buf.putLine1("	<aries:controlbar>");
		buf.putLine1("		<aries:toolbarGroup"); 
		buf.putLine1("			location=\"left\">");
		buf.putLine1("			");
		buf.putLine1("			<!-- help button -->");
		buf.putLine1("			<aries:toolButton");
		buf.putLine1("				id=\""+elementName+"WizardHelpButton\"");
		buf.putLine1("				icon=\"/icons/help/Help24.gif\"");
		buf.putLine1("				tooltip=\"Show Help for "+elementClass+" Wizard\"");
		buf.putLine1("				manager=\"#{globals}\"");
		buf.putLine1("				action=\"doNothing\"");
		buf.putLine1("				onclick=\"showAlert('Development in progress', 'Placeholder for Help Window', 'Simple and brief help documentation will be provided here');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault();\" />");
		buf.putLine1("		</aries:toolbarGroup>");
		buf.putLine1("		");
		buf.putLine1("		<aries:toolbarGroup"); 
		buf.putLine1("			location=\"right\">");
		buf.putLine1("			");
		buf.putLine1("			<!-- refresh button -->");
		buf.putLine1("			<aries:dialogButton");
		buf.putLine1("				default=\"false\"");
		buf.putLine1("				rendered=\"true\"");
		buf.putLine1("				id=\""+elementName+"WizardRefreshButton\"");
		buf.putLine1("				icon=\"/icons/common/Refresh16.gif\"");
		buf.putLine1("				iconDisabled=\"/icons/common/RefreshDisabled16.gif\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"refresh\"");
		buf.putLine1("				onclick=\"showProgress('Nam', '#{"+elementName+"Wizard.title}', 'Re-rendering current page...');\"");
		buf.putLine1("				oncomplete=\"hideProgress();\"");
		buf.putLine1("				render=\""+elementName+"Wizard\"");
		buf.putLine1("				style=\"padding: 2px 4px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("			");
		buf.putLine1("			<!-- populate button -->");
		buf.putLine1("			<aries:dialogButton");
		buf.putLine1("				label=\"Populate\"");
		buf.putLine1("				default=\"false\"");
		buf.putLine1("				mode=\"ajax\"");
		buf.putLine1("				execute=\"@form\"");
		buf.putLine1("				id=\""+elementName+"WizardPopulateButton\"");
		buf.putLine1("				icon=\"/icons/common/Import16.gif\"");
		buf.putLine1("				iconDisabled=\"/icons/common/ImportDisabled16.gif\"");
		buf.putLine1("				rendered=\"#{"+elementName+"Wizard.populateEnabled}\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"populateDefaultValues\"");
		buf.putLine1("				onclick=\"setCursorWait(this); showProgress('Nam', '#{title}', 'Processing the request...');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine1("				render=\"wizardMessages\"");
		//buf.putLine1("				render=\""+elementName+"Messages\"");
		buf.putLine1("				style=\"padding: 2px 8px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("			");
		buf.putLine1("			<!-- back button -->");
		buf.putLine1("			<aries:dialogButton");
		buf.putLine1("				label=\"Back\"");
		buf.putLine1("				default=\"false\"");
		buf.putLine1("				mode=\"ajax\"");
		buf.putLine1("				execute=\"@this\"");
		buf.putLine1("				id=\""+elementName+"WizardPreviousButton\"");
		buf.putLine1("				icon=\"/icons/common/Back16.gif\"");
		buf.putLine1("				iconDisabled=\"/icons/common/BackDisabled16.gif\"");
		buf.putLine1("				rendered=\"#{"+elementName+"Wizard.backVisible}\"");
		buf.putLine1("				enabled=\"#{"+elementName+"Wizard.backEnabled}\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"back\"");
		buf.putLine1("				onclick=\"setCursorWait(this); showProgress('Nam', 'New "+elementClass+"', 'Moving to previous section...');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine1("				render=\"wizardMessages\"");
		//buf.putLine1("				render=\""+elementName+"Messages\"");
		buf.putLine1("				style=\"padding: 2px 8px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("			");
		buf.putLine1("			<!-- next button -->");
		buf.putLine1("			<aries:dialogButton");
		buf.putLine1("				label=\"Next\"");
		buf.putLine1("				default=\"true\"");
		buf.putLine1("				mode=\"ajax\"");
		buf.putLine1("				execute=\"@form\"");
		buf.putLine1("				id=\""+elementName+"WizardNextButton\"");
		buf.putLine1("				icon=\"/icons/common/Forward16.gif\"");
		buf.putLine1("				iconDisabled=\"/icons/common/ForwardDisabled16.gif\"");
		buf.putLine1("				rendered=\"#{"+elementName+"Wizard.nextVisible}\"");
		buf.putLine1("				enabled=\"#{"+elementName+"Wizard.nextEnabled}\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"next\"");
		buf.putLine1("				onclick=\"setCursorWait(this); showProgress('Nam', 'New "+elementClass+"', 'Moving to next section...');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine1("				render=\"wizardMessages\"");
		//buf.putLine1("				render=\""+elementName+"Messages\"");
		buf.putLine1("				style=\"padding: 2px 8px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("			");
		buf.putLine1("			<!-- finish button -->");
		buf.putLine1("			<aries:dialogButton");
		buf.putLine1("				label=\"Finish\"");
		buf.putLine1("				default=\"false\"");
		buf.putLine1("				mode=\"ajax\"");
		buf.putLine1("				execute=\"@form\"");
		buf.putLine1("				id=\""+elementName+"WizardFinishButton\"");
		buf.putLine1("				icon=\"/icons/common/ThumbsUp16.gif\"");
		buf.putLine1("				iconDisabled=\"/icons/common/ThumbsUpDisabled16.gif\"");
		buf.putLine1("				rendered=\"#{"+elementName+"Wizard.finishVisible}\"");
		buf.putLine1("				enabled=\"#{"+elementName+"Wizard.finishEnabled}\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"finish\"");
		buf.putLine1("				onclick=\"setCursorWait(this); showProgress('Nam', 'New "+elementClass+"', 'Validating and submitting new "+elementClass+"...');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine1("				render=\"wizardMessages\"");
		//buf.putLine1("				render=\""+elementName+"Messages\"");
		buf.putLine1("				style=\"padding: 2px 8px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("			");
		buf.putLine1("			<!-- save button -->");
		buf.putLine1("			<aries:dialogButton");
		buf.putLine1("				label=\"Save\"");
		buf.putLine1("				default=\"false\"");
		buf.putLine1("				mode=\"ajax\"");
		buf.putLine1("				execute=\"@form\"");
		buf.putLine1("				id=\""+elementName+"WizardSaveButton\"");
		buf.putLine1("				icon=\"/icons/common/Save16.gif\"");
		buf.putLine1("				iconDisabled=\"/icons/common/SaveDisabled16.gif\"");
		buf.putLine1("				rendered=\"#{"+elementName+"Wizard.saveVisible}\"");
		buf.putLine1("				enabled=\"#{"+elementName+"Wizard.saveEnabled}\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"finish\"");
		buf.putLine1("				onclick=\"setCursorWait(this); showProgress('Nam', '"+elementClass+"', 'Validating and submitting "+elementClass+" changes...');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine1("				render=\"wizardMessages\"");
		//buf.putLine1("				render=\""+elementName+"Messages\"");
		buf.putLine1("				style=\"padding: 2px 8px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("			");
		buf.putLine1("			<!-- cancel button -->");
		buf.putLine1("			<aries:dialogButton"); 
		buf.putLine1("				label=\"Cancel\"");
		buf.putLine1("				mode=\"ajax\"");
		buf.putLine1("				execute=\"@all\"");
		buf.putLine1("				id=\""+elementName+"WizardCancelButton\"");	
		buf.putLine1("				tooltip=\"Cancel creation of new "+elementClass+"\"");
		buf.putLine1("				rendered=\"#{securityGuard.checkManager}\"");
		buf.putLine1("				manager=\"#{"+elementName+"Wizard}\"");
		buf.putLine1("				action=\"cancel\"");
		buf.putLine1("				onclick=\"setCursorWait(this); showProgress('Nam', '"+elementClass+" Management', 'Cancelling creation of "+elementClass+"...');\"");
		buf.putLine1("				oncomplete=\"setCursorDefault(); hideProgress()\"");
		buf.putLine1("				style=\"padding: 2px 8px\"");
		buf.putLine1("				offset=\"true\" />");
		buf.putLine1("		</aries:toolbarGroup>");
		buf.putLine1("	</aries:controlbar>"); 
		buf.putLine1("</aries:controlPane>");
		return buf.get();
	}
	
}
