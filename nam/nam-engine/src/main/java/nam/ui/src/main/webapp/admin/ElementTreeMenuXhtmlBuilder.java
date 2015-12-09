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


public class ElementTreeMenuXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementTreeMenuXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "TreeMenu.xhtml";

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
		buf.putLine1("<aries:contextMenu");
		buf.putLine1("	id=\""+elementName+"TreeMenu\">");
		buf.putLine1("	");
		buf.putLine1("	<c:if test=\"#{securityGuard.checkManager}\">");
		buf.putLine1("		<aries:contextMenuItem");
		buf.putLine1("			mode=\"client\"");
		buf.putLine1("			disabled=\"false\"");
		buf.putLine1("			value=\"New Module...\"");
		buf.putLine1("			icon=\"/icons/nam/NewModule16.gif\"");
		buf.putLine1("			tooltip=\"Create new Module record for selected "+elementClass+"...\"");
		buf.putLine1("			onclick=\"processNewElement(event, 'Module', 'modulePageManager.initializeModuleSourceSelectionPage')\" />");
		buf.putLine1("		");
		buf.putLine1("		<aries:contextMenuItem");
		buf.putLine1("			mode=\"client\"");
		buf.putLine1("			disabled=\"false\"");
		buf.putLine1("			value=\"View "+elementClass+"...\"");
		buf.putLine1("			icon=\"/icons/common/View16.gif\"");
		buf.putLine1("			tooltip=\"View selected "+elementClass+" record...\"");
		buf.putLine1("			onclick=\"processViewElement(event, '"+elementClass+"')\" />");
		buf.putLine1("		");
		buf.putLine1("		<aries:contextMenuItem");
		buf.putLine1("			mode=\"client\"");
		buf.putLine1("			disabled=\"false\"");
		buf.putLine1("			value=\"Edit "+elementClass+"...\"");
		buf.putLine1("			icon=\"/icons/common/Edit16.gif\"");
		buf.putLine1("			tooltip=\"Edit selected "+elementClass+" record...\"");
		buf.putLine1("			onclick=\"processEditElement(event, '"+elementClass+"')\" />");
		buf.putLine1("		");
		buf.putLine1("		<aries:contextMenuItem"); 
		buf.putLine1("			mode=\"client\"");
		buf.putLine1("			disabled=\"false\"");
		buf.putLine1("			value=\"Remove "+elementClass+"\"");
		buf.putLine1("			icon=\"/icons/common/Remove16.gif\"");
		buf.putLine1("			tooltip=\"Remove "+elementClass+" record from the system\"");
		buf.putLine1("			onclick=\"processRemoveElement(event, '"+elementClass+"')\" />");
		buf.putLine1("	</c:if>");
		buf.putLine1("</aries:contextMenu>");
		return buf.get();
	}
	
}
