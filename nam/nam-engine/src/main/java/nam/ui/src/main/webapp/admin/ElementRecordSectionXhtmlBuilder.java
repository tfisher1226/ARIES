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


public class ElementRecordSectionXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementRecordSectionXhtmlBuilder(GenerationContext context) {
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

		if (!member.equals("Overview") &&
			!member.equals("Identification") &&
			!member.equals("Configuration") &&
			!member.equals("Documentation"))
				sectionName = NameUtil.toPlural(memberNameCapped);
		
		String fileName = elementName + "Record_"+sectionName+"Section.xhtml";

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
		buf.putLine1("<!-- wizard body -->");
		buf.putLine1("<aries:panel class=\"wizardBody\">");
		buf.putLine1("");
		//buf.putLine1("	<!-- section includes -->");
		//buf.putLine1("	");
		buf.putLine1("	<!-- wizard title -->"); 
		buf.putLine1("	<ui:insert name=\"wizardTitle\" />");
		buf.putLine1("	");
		buf.putLine1("	<!-- wizard messages -->"); 
		buf.putLine1("	<ui:insert name=\"wizardMessages\" />");
		buf.putLine1("	");
		buf.putLine1("	<!-- wizard section -->");
		buf.putLine1("	<aries:panel class=\"wizardSection\">");
		buf.putLine1("		");	
		buf.putLine1("		<!-- variables -->"); 
		buf.putLine1("		<ui:param name=\"labelWidth\" value=\"100\" />");
		buf.putLine1("		");
		buf.putLine1("		<!-- structures -->"); 
		//buf.putLine1("		<ui:param name=\"user\" value=\"#{adminManager.user}\" />");
		buf.putLine1("		<ui:param name=\"manager\" value=\"#{"+elementName+"InfoManager}\" />"); 
		buf.putLine1("		");
		buf.putLine1("		<aries:spacer height=\"6\" />");		
		buf.putLine1("	</aries:panel>");
		buf.putLine1("</aries:panel>");
		return buf.get();
	}
	
}
