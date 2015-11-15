package nam.ui.src.main.webapp;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class ViewerXHTMLGenerator extends AbstractXHTMLGenerator {

	public ViewerXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName().toLowerCase();
		String fileNamePrefix = context.getProjectNameCamelCased();
		String elementPanels = getElementPanels();

		setSourceFile("viewer.xhtml");
		setTargetFile("viewer.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp/"+projectName);

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1_title", projectName.toUpperCase());
		filterSet.addFilter("template1_context", fileNamePrefix);
		filterSet.addFilter("<!-- ${template1_application_element_panels} -->", elementPanels);
		
		generateFile(filterSet);
	}

	protected String getElementPanels() {
		Buf buf = new Buf();
		Project project = context.getProject();
		Module module = context.getModule();
		Set<Information> informationBlocks = ProjectUtil.getInformationBlocksFromModules(project);
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information informationBlock = iterator.next();
			buf.put(getViewerPanes(informationBlock, "Model", "Record"));
			buf.put(getViewerPanes(informationBlock, "Service", "Service"));
		}
		return buf.get();
	}

	protected String getViewerPanes(Information informationBlock, String panelName1, String panelName2) {
		String folderName = NameUtil.uncapName(informationBlock.getName());
		List<Element> elements = InformationUtil.getElements(informationBlock);
		
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine2("<!-- "+panelName1.toUpperCase()+" PANES for: "+informationBlock.getName()+" -->");
		buf.putLine2("<ui:include src=\""+folderName+"/section/"+folderName+panelName1+"Pane.xhtml\" />");

		Iterator<Element> iterator = elements.iterator();
		for (int i=0; iterator.hasNext();) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			if (!ElementUtil.isRoot(element))
				continue;
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
			buf.putLine2("<ui:include src=\""+folderName+"/section/"+elementNameUncapped+"/"+elementNameUncapped+panelName2+"Pane.xhtml\" />");
		}
		return buf.get();
	}
	
}
