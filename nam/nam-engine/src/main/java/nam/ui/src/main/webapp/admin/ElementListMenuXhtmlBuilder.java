package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ElementUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementListMenuXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListMenuXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListMenu.xhtml";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String prefix = getNamePrefix(element);
		boolean dialogBased = false;

		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		buf.putLine("	");
		buf.putLine("	<aries:region>");
		buf.putLine("		<aries:outputPane");
		buf.putLine("			id=\""+prefix+"ListContextMenuPane\">");
		buf.putLine("			");
		buf.putLine("			<aries:contextMenu");
		buf.putLine("				mode=\"client\"");
		buf.putLine("				id=\""+prefix+"ListContextMenu\"");
		buf.putLine("				target=\"#{tableId}\">");
		
		//VIEW
		if (dialogBased) {
			buf.putLine("				");
			buf.putLine("				<aries:contextMenuItem");
			buf.putLine("					mode=\"ajax\""); 
			buf.putLine("					disabled=\"false\"");
			buf.putLine("					execute=\"@this\"");
			buf.putLine("					immediate=\"true\"");
			buf.putLine("					bypassUpdates=\"true\"");
			buf.putLine("					limitRender=\"true\"");
			buf.putLine("					value=\"Open "+elementClassName+"...\""); 
			buf.putLine("					icon=\"/icons/common/ProjectOpen16.gif\"");
			buf.putLine("					manager=\"#{"+elementNameUncapped+"ListManager}\"");
			buf.putLine("					action=\"view"+elementClassName+"\"");
			buf.putLine("					onclick=\"setCursorWait(); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" display...')\"");
			buf.putLine("					oncomplete=\"setCursorDefault(this); hideProgress(); show"+elementClassName+"Dialog()\" />");
			//buf.putLine("				</aries:contextMenuItem>");

		} else {
			buf.putLine("				");
			buf.putLine("				<aries:contextMenuItem");
			buf.putLine("					mode=\"client\"");
			buf.putLine("					disabled=\"false\"");
			buf.putLine("					value=\"View "+elementClassName+"...\"");
			buf.putLine("					icon=\"/icons/common/Open16.gif\"");
			buf.putLine("					tooltip=\"View selected "+elementClassName+" record...\"");
			buf.putLine("					onclick=\"processViewElement(event, '"+elementClassName+"')\" />");
			//buf.putLine("				</aries:contextMenuItem>");
		}
		
		//EDIT
		if (dialogBased) {
			buf.putLine("				");
			buf.putLine("				<aries:contextMenuItem");
			buf.putLine("					mode=\"ajax\""); 
			buf.putLine("					disabled=\"false\"");
			buf.putLine("					execute=\"@this\"");
			buf.putLine("					immediate=\"true\"");
			buf.putLine("					bypassUpdates=\"true\"");
			buf.putLine("					limitRender=\"true\"");
			buf.putLine("					value=\"Edit "+elementClassName+"...\""); 
			buf.putLine("					icon=\"/icons/common/Edit16.gif\"");
			buf.putLine("					manager=\"#{"+elementNameUncapped+"ListManager}\"");
			buf.putLine("					action=\"edit"+elementClassName+"\"");
			buf.putLine("					onclick=\"setCursorWait(); showProgress('Nam', '"+elementClassName+" Records', 'Preparing "+elementClassName+" display...')\"");
			buf.putLine("					oncomplete=\"setCursorDefault(this); hideProgress(); show"+elementClassName+"Dialog()\" />");
			//buf.putLine("				</aries:contextMenuItem>");

		} else {
			buf.putLine("				");
			buf.putLine("				<aries:contextMenuItem");
			buf.putLine("					mode=\"client\"");
			buf.putLine("					disabled=\"false\"");
			buf.putLine("					value=\"Edit "+elementClassName+"...\"");
			buf.putLine("					icon=\"/icons/common/Edit16.gif\"");
			buf.putLine("					tooltip=\"Edit selected "+elementClassName+" record...\"");
			buf.putLine("					onclick=\"processEditElement(event, '"+elementClassName+"')\" />");
			//buf.putLine("				</aries:contextMenuItem>");
		}
		
		//NEW
		if (dialogBased) {
			buf.putLine("				");
			buf.putLine("				<aries:contextMenuItem");
			buf.putLine("					mode=\"ajax\""); 
			buf.putLine("					disabled=\"false\"");
			buf.putLine("					execute=\"@this\"");
			buf.putLine("					immediate=\"true\"");
			buf.putLine("					bypassUpdates=\"true\"");
			buf.putLine("					limitRender=\"true\"");
			buf.putLine("					value=\"New "+elementClassName+"...\""); 
			buf.putLine("					icon=\"/icons/common/New16.gif\"");
			buf.putLine("					manager=\"#{"+elementNameUncapped+"ListManager}\"");
			buf.putLine("					action=\"new"+elementClassName+"\"");
			buf.putLine("					onclick=\"setCursorWait(); showProgress('Nam', '"+elementClassName+" Records', 'Preparing New "+elementClassName+" record...')\"");
			buf.putLine("					oncomplete=\"setCursorDefault(this); hideProgress(); show"+elementClassName+"Dialog()\"");
			buf.putLine("					enabled=\"#{securityGuard.canDelete('"+elementNameUncapped+"')}\" />");
			//buf.putLine("				</aries:contextMenuItem>");
		
		} else {
			buf.putLine("				");
			buf.putLine("				<c:if test=\"#{securityGuard.checkManager}\">");
			buf.putLine("					<aries:contextMenuItem");
			buf.putLine("						mode=\"client\"");
			buf.putLine("						disabled=\"false\"");
			buf.putLine("						value=\"New "+elementClassName+"...\"");
			buf.putLine("						icon=\"/icons/common/New16.gif\"");
			buf.putLine("						tooltip=\"Create new "+elementClassName+" for selected Project...\"");
			buf.putLine("						onclick=\"processNewElement(event, '"+elementClassName+"')\" />");
			//buf.putLine("					</aries:contextMenuItem>");
			buf.putLine("				</c:if>");
		}

		//NEW
		if (dialogBased) {
			buf.putLine("				");
			buf.putLine("<aries:contextMenuItem");
			buf.putLine("	mode=\"client\"");
			buf.putLine("	execute=\"@none\"");
			buf.putLine("	immediate=\"true\"");
			buf.putLine("	value=\"Remove "+elementClassName+"...\""); 
			buf.putLine("	icon=\"/icons/common/Remove16.gif\"");
			buf.putLine("	enabled=\"#{securityGuard.canDelete('"+elementNameUncapped+"')}\"");
			buf.putLine("	onclick=\"checkRemove"+elementClassName+"()\">");
			buf.putLine("	");
			buf.putLine("	<aries:tooltip value=\"Remove selected "+elementClassName+" record\"/>");
			buf.putLine("</aries:contextMenuItem>");
		
		} else {
			buf.putLine("				");
			buf.putLine("				<c:if test=\"#{securityGuard.checkManager}\">");
			buf.putLine("					<aries:contextMenuItem"); 
			buf.putLine("						mode=\"client\"");
			buf.putLine("						disabled=\"false\"");
			buf.putLine("						value=\"Remove "+elementClassName+"\"");
			buf.putLine("						icon=\"/icons/common/Remove16.gif\"");
			buf.putLine("						tooltip=\"Remove "+elementClassName+" record from the system\"");
			buf.putLine("						onclick=\"processRemoveElement(event, '"+elementClassName+"')\">");
			buf.putLine("					</aries:contextMenuItem>");
			buf.putLine("				</c:if>");
		}

		buf.putLine("			</aries:contextMenu>");
		buf.putLine("		</aries:outputPane>");
		buf.putLine("	</aries:region>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
	protected String getNamePrefix(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		boolean hasSubTypes = ElementUtil.hasSubTypes(element);
		String prefix = null;
		if (hasSubTypes)
			prefix = "#{type}" + elementClassName;
		else prefix = elementNameUncapped;
		return prefix;
	}
	
}
