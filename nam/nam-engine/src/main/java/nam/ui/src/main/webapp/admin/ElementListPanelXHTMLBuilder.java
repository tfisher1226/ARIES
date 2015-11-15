package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Grouping;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ElementUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementListPanelXHTMLBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListPanelXHTMLBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ElementListPanel.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementName = NameUtil.uncapName(element.getName());
		List<Grouping> groups = ElementUtil.getGroups(element);
		String fieldListTabTitle = groups.size() > 0 ? "Miscellaneous" : "Fields";
		
		Buf buf = new Buf();
		buf.putLine1("<aries:layeredPane");
		buf.putLine1("	name=\""+elementName+"ListPanel\">");
		buf.putLine();
		buf.putLine1("	<aries:label"); 
		buf.putLine1("		height=\"30px\""); 
		buf.putLine1("		marginLeft=\"8px\"");
		buf.putLine1("		text=\""+element.getName()+" Element List View\" />");
		buf.putLine();
		buf.putLine1("	<aries:tabPane>");
		
		//loop over groups
		Iterator<Grouping> iterator = groups.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Grouping grouping = iterator.next();
			String groupName = NameUtil.uncapName(grouping.getName());

			if (i > 0)
				buf.putLine();
			buf.putLine1("		<aries:tab");
			buf.putLine1("			header=\""+grouping.getName()+"\"");
			buf.putLine1("			icon=\"/icons/common/Default16.gif\">");
			buf.putLine1("			<aries:panel height=\"#{globals.screenHeight-200}px\">");
			buf.putLine1("				<ui:include src=\""+groupName+"Panel.xhtml\" />");
			buf.putLine1("			</aries:panel>");
			buf.putLine1("		</aries:tab>");
		}
		
		if (groups.size() > 0)
			buf.putLine();
		buf.putLine1("		<aries:tab");
		buf.putLine1("			header=\""+fieldListTabTitle+"\"");
		buf.putLine1("			icon=\"/icons/common/Default16.gif\">");
		buf.putLine1("			<aries:panel height=\"#{globals.screenHeight-200}px\">");
		buf.putLine1("				<ui:include src=\""+elementName+"FieldListPanel.xhtml\" />");
		buf.putLine1("			</aries:panel>");
		buf.putLine1("		</aries:tab>");
		buf.putLine1("	</aries:tabPane>");
		buf.putLine1("</aries:layeredPane>");
		return buf.get();
	}
	
}
