package nam.ui.src.main.webapp;

import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class MainXHTMLGenerator extends AbstractXHTMLGenerator {

	public MainXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		Application application = context.getApplication();
		String applicationName = application.getName();
		String applicationDialogs = getApplicationDialogs();

		setSourceFile("main.xhtml");
		setTargetFile("main.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1_title}", applicationName.toUpperCase());
		filterSet.addFilter("${template1_page_folder}", applicationName);
		filterSet.addFilter("<!-- ${template1_application_dialogs} -->", applicationDialogs);
		generateFile(filterSet);
	}

	protected String getApplicationDialogs() {
		//Module module = context.getModule();
		//Application application = context.getApplication();

		Buf buf = new Buf();
		buf.putLine2("<!-- COMMON DIALOGS -->");
		buf.putLine2("<aries:include file=\"/admin/common/personNameDialog.xhtml\" />");
		buf.putLine2("<aries:include file=\"/admin/common/emailAddressDialog.xhtml\" />");
		buf.putLine2("<aries:include file=\"/admin/common/phoneNumberDialog.xhtml\" />");
		buf.putLine2("<aries:include file=\"/admin/common/streetAddressDialog.xhtml\" />");		
		buf.putLine2("<aries:include file=\"/admin/common/inputDialog.xhtml\" manager=\"#{inputManager}\" type=\"Input\" />");

		Project project = context.getProject();
		buf.put(getApplicationDialogs(ProjectUtil.getSubProjects(project)));
		//buf.put(getApplicationDialogs(project));
		return buf.get();
	}

	protected String getApplicationDialogs(List<Project> subProjects) {
		Buf buf = new Buf();
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			buf.put(getApplicationDialogs(subProject));
		}
		return buf.get();
	}

	protected String getApplicationDialogs(Project project) {
		Buf buf = new Buf();
		
		List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			//make sure skip this one for now
			if (namespace.getUri().equals("http://www.aries.org/common"))
				continue;
			
			Information informationBlock = ExtensionsUtil.getInformationBlock(project, namespace);
			if (informationBlock == null)
				continue;
			
			buf.putLine2("");
			buf.putLine2("<!-- MANAGEMENT DIALOGS for "+namespace.getUri()+" -->");
			String packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
			List<Type> types = NamespaceUtil.getTypes(namespace);
			ElementUtil.sortTypesByName(types);
			Iterator<Type> iterator2 = types.iterator();
			while (iterator2.hasNext()) {
				Type type = iterator2.next();
				if (ElementUtil.isAbstract(type))
					continue;
				if (ElementUtil.isTransient(type))
					continue;
				//if (i++ > 0)
				//	buf.put3("");
				
				String elementNameCapped = ModelLayerHelper.getElementNameCapped(type);
				String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
				
				if (!ElementUtil.isEnumeration(type)) {
					buf.putLine2("<aries:include file=\"/"+packageName+"/section/"+elementNameUncapped+"/"+elementNameUncapped+"InfoDialog.xhtml\" manager=\"#{"+elementNameUncapped+"InfoManager}\" type=\""+elementNameCapped+"\" />");
					buf.putLine2("<aries:include file=\"/"+packageName+"/section/"+elementNameUncapped+"/"+elementNameUncapped+"ListDialog.xhtml\" manager=\"#{"+elementNameUncapped+"ListManager}\" type=\""+elementNameCapped+"List\" />");
				}
				
				if (ElementUtil.isRoot(type) || ElementUtil.isEnumeration(type))
					buf.putLine2("<aries:include file=\"/"+packageName+"/section/"+elementNameUncapped+"/"+elementNameUncapped+"SelectDialog.xhtml\" manager=\"#{"+elementNameUncapped+"SelectManager}\" type=\""+elementNameCapped+"\" />");
			}

			buf.putLine2("");
			buf.put(getNamespaceJavascript(namespace));
		}
		
		return buf.get();
	}
	
	protected String getNamespaceJavascript(Namespace namespace) {
		String localPart = NameUtil.getLocalPartFromNamespace(namespace.getUri());
		Application application = context.getApplication();
		String applicationName = application.getName();
		
		String panelNamePrefix = NameUtil.uncapName(localPart);
		String panelNamePrefixCapped = NameUtil.capName(localPart);
		String viewerNamePrefix = NameUtil.uncapName(applicationName);
		
		Buf buf = new Buf();
		buf.putLine2("<h:outputScript>");
		buf.putLine2("function show"+panelNamePrefixCapped+"ModelPane() {TogglePanelManager.toggleOnClient('pageForm:"+viewerNamePrefix+"ViewerPane', '"+panelNamePrefix+"ModelPane')}");
		buf.putLine2("function show"+panelNamePrefixCapped+"ServicePane() {TogglePanelManager.toggleOnClient('pageForm:"+viewerNamePrefix+"ViewerPane', '"+panelNamePrefix+"ServicePane')}");
		buf.putLine2("</h:outputScript>");
		return buf.get();
	}

}
