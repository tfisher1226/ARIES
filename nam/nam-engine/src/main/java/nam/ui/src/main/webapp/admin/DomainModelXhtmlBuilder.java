package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class DomainModelXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public DomainModelXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information) throws Exception {
		String projectPath = information.getName().toLowerCase();
		String domainName = NameUtil.toCamelCase(information.getName());
		String filePrefix = NameUtil.uncapName(domainName);
		String folderName = projectPath + "/section";
		String fileName = filePrefix + "ModelPane.xhtml";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(information));
		return modelFile;
	}
	
	public String getFileContent(Information information) {
		String domainName = NameUtil.uncapName(information.getName());
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		
		buf.putLine("	<aries:layeredPane");
		buf.putLine("		name=\""+domainName+"ModelPane\">");
		buf.putLine("		");
		buf.putLine("		<aries:label"); 
		buf.putLine("			height=\"30\""); 
		buf.putLine("			marginLeft=\"8px\"");
		buf.putLine("			value=\""+domainName+"-model View\" />");
		buf.putLine("		");
		buf.putLine("		<aries:tabPane>");
		buf.putLine("			<aries:tab");
		buf.putLine("				label=\"Elements\"");
		buf.putLine("				icon=\"/icons/common/Default16.gif\">");
		buf.putLine("				");
		buf.putLine("				<aries:panel"); 
		buf.putLine("					width=\"#{width-2}\"");
		buf.putLine("					height=\"#{height-56}\"");
		buf.putLine("					backgroundColor=\"inherit\">");
		buf.putLine("					");
		buf.putLine("					<aries:text");
		buf.putLine("						margin=\"10px\"");
		buf.putLine("						backgroundColor=\"inherit\">");
		buf.putLine("						");
		buf.putLine("						<p>");
		buf.putLine("						The <b>"+domainName+"-model</b> module contains the following elements.");
		buf.putLine("						</p>");
		buf.putLine("					</aries:text>");
		buf.putLine("					");
		buf.putLine("					<aries:formPane"); 
		buf.putLine("						width=\"auto\"");
		buf.putLine("						height=\"auto\"");
		//buf.putLine("						width=\"#{width-42}\"");
		//buf.putLine("						height=\"#{height-42}\"");
		buf.putLine("						margin=\"10px\"");
		buf.putLine("						backgroundColor=\"inherit\">");
		buf.putLine("						");

		buf.putLine("						<aries:spacer height=\"8\" />");
		buf.putLine("						<aries:indentPane>"); 
		buf.putLine("							<aries:label type=\"H4\" value=\"Elements\" />");

		List<Element> elements = InformationUtil.getElements(information);
		ElementUtil.sortTypesByName(elements);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isRoot(element))
				continue;
			
			String elementClassName = ModelLayerHelper.getElementClassName(element);
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
			String elementNameLabel = NameUtil.splitStringUsingSpacesCapped(elementNameCapped);
				
			//buf.putLine("							<aries:bullet type=\"H5\" value=\""+elementNameLabel+" List\" onclick=\"show"+elementClassName+"ListDialog()\" />");
			buf.putLine("							");							
			buf.putLine("							<aries:bullet"); 
			buf.putLine("								type=\"H5\""); 
			buf.putLine("								value=\""+elementNameLabel+" List\""); 
			buf.putLine("								onmouseup=\"launch"+elementClassName+"ListDialog('', '', '')\" />");
			
			/*
			<!-- 
			<aries:bullet 
				type=\"H5\" 
				text=\"User List\" 
				manager=\"#{mainUserListManager}\"
				action=\"show\"
		   		onclick=\"setCursorWait(this); showProgress('', 'User Service', 'Refreshing User List...')\"
				oncomplete=\"setCursorDefault(event.source); hideProgress(); launchUserListDialog('userList', '', 'UserList')\"
		   		render=\"UserListModule, UserListDialog\" />
				-->
				*/
		}
		
		buf.putLine("						</aries:indentPane>");
		buf.putLine("					</aries:formPane>");
		buf.putLine("				</aries:panel>");
		buf.putLine("			</aries:tab>");
		buf.putLine("			"); 
		buf.putLine("			<aries:tab");
		buf.putLine("				label=\"Protection\"");
		buf.putLine("				icon=\"/icons/common/Default16.gif\">");
		buf.putLine("				");				
		buf.putLine("				<aries:panel");
		buf.putLine("					width=\"#{width-2}\"");
		buf.putLine("					height=\"#{height-56}\">");
		buf.putLine("					");
		buf.putLine("					<aries:text");
		buf.putLine("						margin=\"10px\"");
		buf.putLine("						backgroundColor=\"inherit\">");
		buf.putLine("						");
		buf.putLine("						<p>");
		buf.putLine("						The following protection mechanisms are configured for the <b>admin-model</b> module.");
		buf.putLine("						</p>");
		buf.putLine("						<br/>");
		buf.putLine("					</aries:text>");
		buf.putLine("				</aries:panel>");
		buf.putLine("			</aries:tab>");
		buf.putLine("		</aries:tabPane>");
		buf.putLine("	</aries:layeredPane>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
	
//	List<Namespace> namespaces = InformationUtil.getNamespaces(information);
//	Iterator<Namespace> iterator = namespaces.iterator();
//	while (iterator.hasNext()) {
//		Namespace namespace = iterator.next();
//		String targetNamespace = namespace.getUri();
//		
//		Project project = context.getProject();
//		List<Service> services = ProjectUtil.getServices(project);
//		Iterator<Service> iterator2 = services.iterator();
//		while (iterator2.hasNext()) {
//			Service service = iterator2.next();
//			if (!service.getNamespace().equals(targetNamespace))
//				continue;
//			
//			String serviceClassName = ServiceLayerHelper.getServiceClassName(service);
//			String serviceNameUncapped = ServiceLayerHelper.getServiceNameUncapped(service);
//			String serviceNameCapped = ServiceLayerHelper.getServiceNameCapped(service);
//			String serviceNameLabel = NameUtil.splitStringUsingSpacesCapped(serviceNameCapped);

}
