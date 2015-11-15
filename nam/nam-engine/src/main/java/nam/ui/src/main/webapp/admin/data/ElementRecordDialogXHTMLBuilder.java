package nam.ui.src.main.webapp.admin.data;

import nam.model.Element;
import nam.model.Namespace;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementRecordDialogXHTMLBuilder extends AbstractDataElementXHTMLBuilder {

	public ElementRecordDialogXHTMLBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelFile buildFile(Namespace namespace, Element element) throws Exception {
		//String packageName = NameUtil.getPackagePathFromNamespace(namespace.getUri());
		String projectPath = context.getProjectName().toLowerCase();
		String elementName = NameUtil.uncapName(element.getName());
		String fileName = elementName + "RecordDialog.xhtml";
		String folderName = projectPath + "/data";
		//String folderName = "/admin/elements";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}
	
	public String getFileContent(Element element) {
		String elementName = NameUtil.capName(element.getName());
		String elementNameUncapped = NameUtil.uncapName(element.getName());

		Buf buf = new Buf();
		buf.putLine1("<aries:region>");
		buf.putLine1("	<aries:dialog"); 
		buf.putLine1("		id=\"#{domain}"+elementName+"Dialog\"");
		buf.putLine1("		domain=\"#{domain}"+elementName+"Dialog\"");
		buf.putLine1("		action=\"save"+elementName+"\"");
		buf.putLine1("		manager=\"#{roleInfoManager}\"");
		buf.putLine1("		render=\"#{domain}"+elementName+"ListPane\">");
		buf.putLine1("		");
		buf.putLine1("		<!-- Imported Dialogs -->");
		buf.putLine1("		<ui:include src=\""+elementNameUncapped+"SelectDialog.xhtml\" />");
		buf.putLine1("		<ui:include src=\"../permission/permissionInfoDialog.xhtml\" />");
		buf.putLine1("		");
		buf.putLine1("		<!-- Dialog Content -->");
		buf.putLine1("		<ui:include src=\""+elementNameUncapped+"InfoPane.xhtml\" />");
		buf.putLine1("	</aries:dialog>");
		buf.putLine1("</aries:region>");
		return buf.get();
	}
	
}
