package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.ViewUtil;
import nam.ui.Relation;
import nam.ui.View;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementManagementSectionXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementManagementSectionXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element, String member) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String memberNameCapped = NameUtil.capName(member);
		String memberNameUncapped = NameUtil.uncapName(member);
		String sectionName = memberNameCapped;
		if (!member.equals("Overview"))
			sectionName = NameUtil.toPlural(memberNameCapped);
		
		String fileName = elementName + "View_"+sectionName+"Section.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		String elementFileContent = getElementFileContent(element, member);
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
	
	public String getElementFileContent(Element element, String member) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClass = ModelLayerHelper.getElementClassName(element);
		String elementClassPlural = NameUtil.toPlural(elementClass);
		String elementPackage = ModelLayerHelper.getElementPackageName(element);
		String elementPath = elementPackage.replace(".", "/");
		
		View view = context.getModule().getView();
		if (member.equals("Overview")) {
			String containerName = null;
			Element memberElement = element;
			Relation relation = ViewUtil.getContainedByRelation(view, elementClass);
			if (relation != null) {
				List<String> children = relation.getContainer();
				String container = children.iterator().next();
				//TODO NEED TO USE PACKAGE NAME HERE
				//TODO make package come from external file
				//containerName = "nam.model." + container;
				containerName = container;
			} else {
				//TODO NEED TO USE PACKAGE NAME HERE
				//TODO make this default come from external file
				//containerName = "nam.model.Project";
				containerName = "Project";
			}
			
			String target = elementName;
			Element containerElement = context.getElementByName(containerName);
			Assert.notNull(containerElement, "Element not found: "+containerName);
			return getElementFileContent(element, containerElement, memberElement, target);
		}
		
		Relation relation = ViewUtil.getContainedByRelation(view, elementClass);
		if (relation != null) {
			List<String> containers = relation.getContainer();
			Iterator<String> iterator = containers.iterator();
			while (iterator.hasNext()) {
				String container = iterator.next();
				if (container.equalsIgnoreCase(member)) {
					Element memberElement = element;
					String containerName = member;
					String target = elementName;
					//String containerName = "nam.model." + member;
					Element containerElement = context.getElementByName(containerName);
					Assert.notNull(containerElement, "Element not found: "+containerName);
					return getElementFileContent(element, containerElement, memberElement, target);
				}
			}
		}
		
		String target = member;
		Element containerElement = element;
		//TODO add package to external file
		String memberName = member;
		//String memberName = "nam.model." + member;
		Type memberElement = context.getElementByName(memberName);
		if (memberElement == null)
			memberElement = context.getEnumerationByName(memberName);
		Assert.notNull(memberElement, "Element not found: "+memberName);
		return getElementFileContent(element, containerElement, memberElement, target);
	}
	
	public String getElementFileContent(Element element, Element container, Type member, String target) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		//String elementClass = ModelLayerHelper.getElementClassName(element);
		//String elementClassPlural = NameUtil.toPlural(elementClass);
		//String elementPackage = ModelLayerHelper.getElementPackageName(element);
		//String elementPath = elementPackage.replace(".", "/");
		String targetUncapped = NameUtil.uncapName(target);
		
		String containerName = ModelLayerHelper.getElementNameUncapped(container);
		String containerClass = ModelLayerHelper.getElementClassName(container);
		String containerClassPlural = NameUtil.toPlural(containerClass);
		String containerPackage = ModelLayerHelper.getElementPackageName(container);
		String containerPath = containerPackage.replace(".", "/");
		boolean containerHasSubTypes = ElementUtil.hasSubTypes(container);
		
		Buf buf = new Buf();
		buf.putLine1("<!-- section body -->");
		buf.putLine1("<h:panelGrid class=\"sectionBody\">");
		buf.putLine1("");
		buf.putLine1("	<!-- page includes -->");
		buf.putLine1("	");
		buf.putLine1("	<!-- section title -->"); 
		buf.putLine1("	<ui:insert name=\"sectionTitle\" />");
		buf.putLine1("	");
		buf.putLine1("	<!-- section messages"); 
		buf.putLine1("	<ui:insert name=\"sectionMessages\" />");
		buf.putLine1("	-->"); 
		buf.putLine1("	");
		buf.putLine1("	<aries:spacer height=\"10\" />");
		buf.putLine1("	<aries:formPane");
		buf.putLine1("		columns=\"2\""); 
		buf.putLine1("		width=\"auto\"");
		buf.putLine1("		height=\"auto\"");
		buf.putLine1("		style=\"\">");
		buf.putLine1("		");
		buf.putLine1("		<!-- variables -->");
		buf.putLine1("		<ui:param name=\"render\" value=\""+elementName+"ManagementViewer\" />");
		buf.putLine1("		");
		buf.putLine1("		<aries:panel style=\"\">");
		buf.putLine1("			<ui:include src=\"/common/sectionLabel.xhtml\">");
		buf.putLine1("				<ui:param name=\"icon\" value=\"/icons/nam/"+containerClass+"16.gif\" />");
		buf.putLine1("				<ui:param name=\"label\" value=\""+containerClassPlural+"\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("			");		
		buf.putLine1("			<aries:spacer height=\"8\" />");
		buf.putLine1("			<h:panelGroup layout=\"block\" styleClass=\"tableBorder\">");
		buf.putLine1("				");	
		buf.putLine1("				<!-- "+containerName+" refresh scope -->");		
		buf.putLine1("				<ui:param name=\"scope\" value=\""+containerName+"List\" />");
		buf.putLine1("				<ui:param name=\"target\" value=\""+targetUncapped+"\" />");
		buf.putLine1("				");	
		buf.putLine1("				<!-- "+containerName+" table section -->");		
		buf.putLine1("				<ui:include src=\"/"+containerPath+"/"+containerName+"/"+containerName+"ListActions.xhtml\" />");
		buf.putLine1("				<ui:include src=\"/"+containerPath+"/"+containerName+"/"+containerName+"NameListToolbar.xhtml\" />");
		buf.putLine1("				<ui:include src=\"/"+containerPath+"/"+containerName+"/"+containerName+"NameListTable.xhtml\">");	
		if (containerHasSubTypes) {
			buf.putLine1("					<ui:param name=\"manager\" value=\"#{"+containerName+"ListManager}\" />");
		}
		buf.putLine1("					<ui:param name=\"addSelectorColumn\" value=\"true\" />");
		buf.putLine1("				</ui:include>");
		buf.putLine1("			</h:panelGroup>");
		buf.putLine1("		</aries:panel>");
		buf.putLine1("		");	

		String memberName = ModelLayerHelper.getElementNameUncapped(member);
		String memberClass = ModelLayerHelper.getElementClassName(member);
		String memberClassPlural = NameUtil.toPlural(memberClass);
		String memberPackage = ModelLayerHelper.getElementPackageName(member);
		String memberPath = memberPackage.replace(".", "/");
		boolean memberHasSubTypes = ElementUtil.hasSubTypes(member);

		buf.putLine1("		<aries:panel style=\"margin-left: 20px\">");
		buf.putLine1("			<ui:include src=\"/common/sectionLabel.xhtml\">");
		buf.putLine1("				<ui:param name=\"icon\" value=\"/icons/nam/"+memberClass+"16.gif\" />");
		buf.putLine1("				<ui:param name=\"label\" value=\""+memberClassPlural+"\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("			");
		buf.putLine1("			<aries:spacer height=\"8\" />");
		buf.putLine1("			<h:panelGroup layout=\"block\" styleClass=\"tableBorder\">");
		buf.putLine1("				");	
		buf.putLine1("				<!-- "+memberName+" refresh action -->");		
		buf.putLine1("				<ui:param name=\"scope\" value=\""+containerName+"Selection\" />");
		buf.putLine1("				");	
		buf.putLine1("				<!-- "+memberName+" table section -->");		
		buf.putLine1("				<ui:include src=\"/"+memberPath+"/"+memberName+"/"+memberName+"ListActions.xhtml\" />");
		buf.putLine1("				<ui:include src=\"/"+memberPath+"/"+memberName+"/"+memberName+"NameListToolbar.xhtml\" />");
		if (memberHasSubTypes) {
			buf.putLine1("				<ui:include src=\"/"+memberPath+"/"+memberName+"/"+memberName+"NameListTable.xhtml\">");
			buf.putLine1("					<ui:param name=\"manager\" value=\"#{"+memberName+"ListManager}\" />");
			buf.putLine1("				</ui:include>");
		} else {
			buf.putLine1("				<ui:include src=\"/"+memberPath+"/"+memberName+"/"+memberName+"NameListTable.xhtml\" />");
		}
		buf.putLine1("			</h:panelGroup>");
		buf.putLine1("		</aries:panel>");
		buf.putLine1("	</aries:formPane>");
		buf.putLine1("</h:panelGrid>");
		return buf.get();
	}
	
}
