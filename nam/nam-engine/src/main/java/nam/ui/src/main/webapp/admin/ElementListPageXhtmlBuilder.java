package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementListPageXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListPageXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildElementFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListPage.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		String elementFileContent = getElementFileContent(element);
		String xhtmlFileContent = getXhtmlFileContent(elementFileContent);
		modelFile.setTextContent(xhtmlFileContent);
		return modelFile;
	}

	public String getXhtmlFileContent(String xhtmlContent) throws Exception {
		Buf buf = new Buf();
		buf.put(generateCompositionXhtmlOpen());
		buf.put(generateCompositionXhtmlHeader("main.xhtml"));
		buf.putLine();
		buf.put(xhtmlContent);
		buf.put(generateCompositionXhtmlClose());
		return buf.get();
	}
	
	public String getElementFileContent(Element element) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClass = ModelLayerHelper.getElementClassName(element);
		String elementPackage = ModelLayerHelper.getElementPackageName(element);
		String elementPath = elementPackage.replace(".", "/");
		
		Buf buf = new Buf();
		buf.putLine1("<!-- page includes -->");
		buf.putLine1("<ui:define name=\"pageIncludes\">");
		buf.putLine1("	<ui:include src=\""+elementName+"Actions.xhtml\" />");
		buf.putLine1("	<ui:include src=\""+elementName+"ListActions.xhtml\" />");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page title -->");
		buf.putLine1("<ui:define name=\"pageTitle\">");
		buf.putLine1("	<span class=\"pageTitle2\">");
		buf.putLine1("		<ui:include src=\"/common/pageTitle.xhtml\">");
		buf.putLine1("			<ui:param name=\"manager\" value=\"#{"+elementName+"PageManager}\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page controls -->");
		buf.putLine1("<ui:define name=\"pageControls\">");
		buf.putLine1("	<ui:include src=\"/common/pageControls.xhtml\" />");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page breadcrumbs -->");
		buf.putLine1("<ui:define name=\"pageBreadcrumbs\">");
		buf.putLine1("	<span class=\"pageBreadcrumbs\">");
		buf.putLine1("		<ui:include src=\"/common/breadcrumbs.xhtml\">");
		buf.putLine1("			<ui:param name=\"breadcrumbs\" value=\"#{"+elementName+"PageManager.getBreadcrumbs('"+elementName+"List')}\" />");
		buf.putLine1("		</ui:include>");
		buf.putLine1("	</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- page views -->");
		buf.putLine1("<ui:define name=\"pageViews\">");
		buf.putLine1("	<span class=\"pageViews\">");
		buf.putLine1("		<ui:include src=\"/common/pageViews.xhtml\">");
		buf.putLine1("			<ui:param name=\"label\" value=\""+elementClass+"\" />");
		buf.putLine1("			<ui:param name=\"manager\" value=\"#{"+elementName+"PageManager}\" />");
		buf.putLine1("			<ui:param name=\"overviewPage\" value=\"initialize"+elementClass+"ManagementPage\" />");
		buf.putLine1("			<ui:param name=\"listPage\" value=\"initialize"+elementClass+"ListPage\" />");
		if (ElementUtil.isHierarchical(element))
			buf.putLine1("			<ui:param name=\"treePage\" value=\"initialize"+elementClass+"ListPage\" />");
		buf.putLine1("		</ui:include>");
        buf.putLine1("		</span>");
		buf.putLine1("</ui:define>");
		buf.putLine1("");
		buf.putLine1("<!-- content -->");
		buf.putLine1("<ui:define name=\"content\">");
		buf.putLine1("	<div class=\"content2\">");
		buf.putLine1("		");
		buf.putLine1("		<aries:panel");
		buf.putLine1("			width=\"auto\"");
		buf.putLine1("			height=\"auto\"");
		buf.putLine1("			backgroundColor=\"inherit\">");
		buf.putLine1("			");
		buf.putLine1("			<aries:formPane"); 
		buf.putLine1("				width=\"auto\"");
		buf.putLine1("				height=\"auto\"");
		buf.putLine1("				margin=\"20px\"");
		buf.putLine1("				marginTop=\"2px\">");
		buf.putLine1("				");
		buf.putLine1("				<!-- container -->");
		buf.putLine1("				<aries:borderPane");
		buf.putLine1("					width=\"auto\"");
		buf.putLine1("					height=\"auto\">");
		buf.putLine1("					");
		buf.putLine1("					<!-- "+elementName+" list -->");
		buf.putLine1("					<aries:outputPane");
		buf.putLine1("						id=\""+elementName+"ListPane\">");
		buf.putLine1("						");
		buf.putLine1("						<ui:include src=\""+elementName+"ListToolbar.xhtml\" />");
		buf.putLine1("						<ui:include src=\""+elementName+"ListTable.xhtml\" />");
		buf.putLine1("					</aries:outputPane>");
		buf.putLine1("				</aries:borderPane>");
		buf.putLine1("				");
		buf.putLine1("				<!--");  
		buf.putLine1("				<a4j:log level=\"ALL\" popup=\"false\" width=\"400\" height=\"200\"/>"); 
		buf.putLine1("				-->");
		buf.putLine1("			</aries:formPane>");
		buf.putLine1("		</aries:panel>");
		buf.putLine1("	</div>");
		buf.putLine1("</ui:define>");
		return buf.get();
	}
	
}
