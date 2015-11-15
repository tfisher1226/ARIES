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
		buf.putLine1("		height=\"auto\">");
		buf.putLine1("		");	
		buf.putLine1("		<aries:panel>");
		buf.putLine1("			<ui:include src=\"/common/sectionLabel.xhtml\">");
		buf.putLine1("				<ui:param name=\"icon\" value=\"/icons/nam/Project16.gif\" />");
		buf.putLine1("				<ui:param name=\"label\" value=\"Projects\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("			");		
		buf.putLine1("			<aries:spacer height=\"8\" />");
		buf.putLine1("			<h:panelGroup layout=\"block\" styleClass=\"tableBorder\">");
		buf.putLine1("				<ui:include src=\"/"+elementPath+"/"+elementName+"/"+elementName+"ListActions.xhtml\">");
		buf.putLine1("					<ui:param name=\"render\" value=\""+elementName+"ManagementViewer\" />");
		buf.putLine1("					<ui:param name=\"lockOnSelect\" value=\"true\" />");
		buf.putLine1("				</ui:include>");
		buf.putLine1("				");						
		buf.putLine1("				<ui:include src=\"/"+elementPath+"/"+elementName+"/"+elementName+"NameListToolbar.xhtml\" />");
		buf.putLine1("				<ui:include src=\"/"+elementPath+"/"+elementName+"/"+elementName+"NameListTable.xhtml\" />");	
		buf.putLine1("			</h:panelGroup>");
		buf.putLine1("		</aries:panel>");
		buf.putLine1("		");
		
		elementClass = "TOKEN";
		elementName = "TOKEN";
		buf.putLine1("		<aries:panel marginLeft=\"20px\">");
		buf.putLine1("			<ui:include src=\"/common/sectionLabel.xhtml\">");
		buf.putLine1("				<ui:param name=\"icon\" value=\"/icons/nam/"+elementClass+"16.gif\" />");
		buf.putLine1("				<ui:param name=\"label\" value=\""+elementClassPlural+"\" />");
		buf.putLine1("			</ui:include>");
		buf.putLine1("			");
		buf.putLine1("			<aries:spacer height=\"8\" />");
		buf.putLine1("			<h:panelGroup layout=\"block\" styleClass=\"tableBorder\">");
		buf.putLine1("				<ui:include src=\""+elementName+"ListActions.xhtml\">");
		buf.putLine1("					<ui:param name=\"render\" value=\""+elementName+"ManagementViewer\" />");
		buf.putLine1("					<ui:param name=\"lockOnSelect\" value=\"false\" />");
		buf.putLine1("				</ui:include>");
		buf.putLine1("				");
		buf.putLine1("				<ui:include src=\""+elementName+"NameListToolbar.xhtml\" />");
		buf.putLine1("				<ui:include src=\""+elementName+"NameListTable.xhtml\" />");	
		buf.putLine1("			</h:panelGroup>");
		buf.putLine1("		</aries:panel>");
		buf.putLine1("	</aries:formPane>");
		buf.putLine1("</h:panelGrid>");
		return buf.get();
	}
	
}
