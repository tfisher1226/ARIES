package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementListToolbarXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListToolbarXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListToolbar.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:f=\"http://xmlns.jcp.org/jsf/core\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		buf.putLine("	<aries:toolbar");
		buf.putLine("		type=\"table\"");
		buf.putLine("		id=\""+elementNameUncapped+"ListToolbar\">");
		buf.putLine("		");
		buf.putLine("		<aries:toolbarGroup");
		buf.putLine("			location=\"left\">");
		buf.putLine("		");
		buf.putLine("			<aries:toolButton");
		buf.putLine("				id=\""+elementNameUncapped+"ListNewButton\"");
		buf.putLine("				value=\"New "+elementClassName+"...\"");
		buf.putLine("				icon=\"/icons/common/New16.gif\"");
		buf.putLine("				iconDisabled=\"/icons/common/NewDisabled16.gif\"");
		buf.putLine("				tooltip=\"Create new "+elementClassName+" Record\"");
		buf.putLine("				enabled=\"#{securityGuard.canCreate('"+elementNameUncapped+"')}\"");
		buf.putLine("				mode=\"client\"");
		buf.putLine("				execute=\"@none\"");
		buf.putLine("				onmouseup=\"processNewElement(event, '"+elementClassName+"')\"");
		buf.putLine("				offset=\"true\" />");
		buf.putLine("			");
		buf.putLine("			<aries:toolButton");
		buf.putLine("				id=\""+elementNameUncapped+"ListEditButton\"");
		buf.putLine("				value=\"Edit "+elementClassName+"...\"");
		buf.putLine("				icon=\"/icons/common/Edit16.gif\"");
		buf.putLine("				iconDisabled=\"/icons/common/EditDisabled16.gif\"");
		buf.putLine("				tooltip=\"Open selected "+elementClassName+" record for edit...\"");
		buf.putLine("				enabled=\"#{"+elementNameUncapped+"ListManager.hasSelection() and securityGuard.canUpdate('"+elementNameUncapped+"')}\"");
		buf.putLine("				mode=\"client\"");
		buf.putLine("				execute=\"@none\"");
		buf.putLine("				onmouseup=\"processEditElement(event, '"+elementClassName+"')\"");
		buf.putLine("				offset=\"true\" />");
		buf.putLine("			");
		buf.putLine("			<aries:toolButton");
		buf.putLine("				id=\""+elementNameUncapped+"ListRemoveButton\"");
		buf.putLine("				value=\"Remove "+elementClassName+"...\"");
		buf.putLine("				icon=\"/icons/common/Remove16.gif\"");
		buf.putLine("				iconDisabled=\"/icons/common/RemoveDisabled16.gif\"");
		buf.putLine("				tooltip=\"Remove selected "+elementClassName+" record from system...\"");
		buf.putLine("				enabled=\"#{"+elementNameUncapped+"ListManager.hasSelection() and securityGuard.canDelete('"+elementNameUncapped+"')}\"");
		buf.putLine("				mode=\"client\"");
		buf.putLine("				execute=\"@none\"");
		buf.putLine("				onmouseup=\"processRemoveElement(event, '"+elementClassName+"')\"");
		buf.putLine("				offset=\"true\" />");
//		buf.putLine("			");
//		buf.putLine("			<aries:toolButton");
//		buf.putLine("				id=\""+elementNameUncapped+"ListRemoveButton\"");
//		buf.putLine("				value=\""+elementClassName+"...\"");
//		buf.putLine("				icon=\"/icons/common/Remove16.gif\"");
//		buf.putLine("				iconDisabled=\"/icons/common/RemoveDisabled16.gif\"");
//		buf.putLine("				tooltip=\"Remove selected "+elementClassName+" Record\"");
//		buf.putLine("				enabled=\"#{"+elementNameUncapped+"ListManager.hasSelection() and securityGuard.canDelete('"+elementNameUncapped+"')}\"");
//		buf.putLine("				mode=\"ajax\"");
//		buf.putLine("				execute=\"@this\"");
//		buf.putLine("				immediate=\"true\"");
//		buf.putLine("				bypassUpdates=\"true\"");
//		buf.putLine("				limitRender=\"true\"");
//		buf.putLine("				manager=\"#{"+elementNameUncapped+"ListManager}\"");
//		buf.putLine("				action=\"remove"+elementClassName+"\"");
//		buf.putLine("				onclickXX=\"alert('#{domain}')\"");
//		buf.putLine("				onclick=\"popupPrompt('"+elementClassName+" List', 'Remove selected "+elementClassName+" record from System', 'Do you wish to continue?', 'org.aries.remove"+elementClassName+"', '#{section}"+elementClassName+"ListPane')\"");
//		buf.putLine("				rendered=\"#{empty domain}\"");
//		buf.putLine("				offset=\"true\" />");
//		buf.putLine("			");
//		buf.putLine("			<aries:toolButton");
//		buf.putLine("				id=\""+elementNameUncapped+"ListSelectButton\"");
//		buf.putLine("				value=\""+elementClassName+"...\"");
//		buf.putLine("				icon=\"/icons/common/Search16.gif\"");
//		buf.putLine("				iconDisabled=\"/icons/common/SearchDisabled16.gif\"");
//		buf.putLine("				tooltip=\"Select "+elementClassName+" Record(s)\"");
//		buf.putLine("				enabled=\"#{securityGuard.canOpen('"+elementNameUncapped+"')}\"");
//		buf.putLine("				mode=\"client\"");
//		buf.putLine("				execute=\"@none\"");
//		buf.putLine("				immediate=\"true\"");
//		buf.putLine("				bypassUpdates=\"true\"");
//		buf.putLine("				limitRender=\"true\"");
//		buf.putLine("				onclickXX=\"alert('#{domain}')\"");
//		buf.putLine("				onclick=\"show#{domain}"+elementClassName+"SelectDialog()\"");
//		buf.putLine("				render=\"#{domain}"+elementClassName+"SelectDialog\"");
//		buf.putLine("				rendered=\"#{domain eq '"+elementClassName+"Dialog'}\"");
//		buf.putLine("				offset=\"true\" />");
		buf.putLine("		</aries:toolbarGroup>");
		buf.putLine("		");
		buf.putLine("		<aries:toolbarGroup");
		buf.putLine("			location=\"right\">");
		buf.putLine("			");			
		buf.putLine("			<aries:toolButton");
		buf.putLine("				id=\""+elementNameUncapped+"ListRefreshButton\"");
		buf.putLine("				icon=\"/icons/common/Refresh16.gif\"");
		buf.putLine("				iconDisabled=\"/icons/common/RefreshDisabled16.gif\"");
		buf.putLine("				tooltip=\"Re-read "+elementClassName+" information from server\"");
		buf.putLine("				enabled=\"#{securityGuard.canOpen('"+elementNameUncapped+"')}\"");
		buf.putLine("				mode=\"client\"");
		buf.putLine("				execute=\"@none\"");
		buf.putLine("				onmouseup=\"refresh"+elementClassName+"List(event)\"");
		buf.putLine("				offset=\"true\" />");
		buf.putLine("		</aries:toolbarGroup>");
		buf.putLine("	</aries:toolbar>");
		buf.putLine("</ui:composition>");
		
		/*
			<!--  
			<aries:toolButton
				id="#{domain}UserListNewUserButton"
				value="User..."
				tooltip="Create new User Record"
				icon="/icons/common/Role16.gif"
				iconDisabled="/icons/common/NewDisabled16.gif"
				enabled="#{securityGuard.canCreate('user')}"
				mode="ajax"
				execute="@this"
				immediate="true"
				bypassUpdates="true"
				limitRender="true"
				manager="#{itemManager}"
				action="newUser"
				onclickXX="alert('#{userInfoDialogs}')"
				onclick="setCursorWait(); showProgress('', 'User Records', 'Creating new record...')"
				oncomplete="setCursorDefault(this); hideProgress(); launch#{domain}UserDialog()"
				render="#{domain}UserDialog, #{domain}UserPersonNameDialog, #{domain}UserEmailAddressDialog, #{domain}UserPhoneNumberDialog, #{domain}UserStreetAddressDialog, #{domain}UserRoleSelectDialog, #{domain}UserPermissionInfoDialog, #{domain}UserPermissionInfoActionSelectDialog"
				offset="true" />
				-->
				
			<!--  
			<aries:toolButton
				id="#{domain}UserListEditUserButton"
				value="User..."
				tooltip="View/Edit selected User Record"
				icon="/icons/common/Edit16.gif"
				iconDisabled="/icons/common/EditDisabled16.gif"
				enabled="#{"+elementNameUncapped+"ListManager.hasSelection() and securityGuard.canOpen('user')}"
				mode="ajax"
				execute="@this"
				immediate="true"
				bypassUpdates="true"
				limitRender="true"
				manager="#{"+elementNameUncapped+"ListManager}"
				action="editUser"
				onclickXX="alert('#{domain}')"
				onclick="setCursorWait(); showProgress('', 'User Records', 'Finding selected User information...')"
				oncomplete="setCursorDefault(this); hideProgress(); show#{domain}UserDialog()"
				render="#{domain}UserDialog, #{domain}UserPersonNameDialog, #{domain}UserEmailAddressDialog, #{domain}UserPhoneNumberDialog, #{domain}UserStreetAddressDialog, #{domain}UserRoleSelectDialog, #{domain}UserPermissionInfoDialog, #{domain}UserPermissionInfoActionSelectDialog"
				rendered="#{empty domain}"
				offset="true" />
				-->
				
			<!-- 
			<a4j:region renderRegionOnly="true" selfRendered ="true">
			<h:commandButton value="Refresh">
				<f:ajax execute="@this" immediate="true" listener="#{"+elementNameUncapped+"ListManager.refresh}" render="UserListPane" />
			</h:commandButton>
			</a4j:region>
			-->
			
			<!--  
			<a4j:commandButton
				id="#{domain}UserListRefreshButtonXXX"
				disabled="#{!securityGuard.canOpen('user')}"
				tooltip="Re-read information from server"
				mode="ajax"
				execute="@this"
				immediate="true"
				bypassUpdates="true"
				limitRender="true"
		   		action="#{"+elementNameUncapped+"ListManager.refresh}"
		   		onclickXX="alert('#{domain}')"
		   		onclick="setCursorWait(this); showProgress('', 'User Service', 'Refreshing User List...')"
				oncomplete="setCursorDefault(this); hideProgress()"
		   		render="#{domain}UserListPane"
				rendered="#{true}"/>
				-->
			
			<!--  
			<aries:toolButton
				id="#{domain}UserListRefreshButton"
				icon="/icons/common/Refresh16.gif"
				iconDisabled="/icons/common/RefreshDisabled16.gif"
				enabled="#{securityGuard.canOpen('user')}"
				tooltip="Re-read information from server"
				mode="ajax"
				execute="@this"
				immediate="true"
				bypassUpdates="true"
				limitRender="true"
		   		manager="#{"+elementNameUncapped+"ListManager}"
		   		action="refresh" 
		   		onclickXX="alert('#{domain}')"
		   		onclick="setCursorWait(this); showProgress('', 'User Service', 'Refreshing User List...')"
				oncomplete="setCursorDefault(this); hideProgress()"
		   		render="#{domain}UserListPane"
				rendered="#{true}"
				linkClass="text16 link"/>
				-->
		 */
		return buf.get();
	}
	
}
