package nam.ui.workspace;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import nam.model.Project;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceManager;

import org.aries.Assert;
import org.aries.RuntimeContext;
import org.aries.launcher.Initializer;
import org.aries.launcher.Launcher;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractViewManager;
import org.aries.util.properties.PropertyManager;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("modelFileUploadManager")
@SuppressWarnings("serial")
public class ModelFileUploadManager extends AbstractViewManager implements Serializable {

	private String domain = "aries.org";
	
	private String application = "nam";
	
	private String module = "nam-view";

	@Inject
	private WorkspaceManager workspaceManager;
	
	@Inject
	private SelectionContext selectionContext;


	public ModelFileUploadManager() {
	}
	
	public void initializeModel(String filePath) throws Exception {
		try {
			log.info("Initializing using: "+filePath);
			Initializer initializer = new Initializer(domain, application, module);
			Collection<Project> projects = initializer.initializeModel(filePath);
			GenerationContext context = initializer.getContext();
			saveContext(context, projects);
			saveModel(filePath);
		} catch (Exception e) {
			log.error("Error", e);
		}
	}
	
	protected void saveContext(GenerationContext context, Collection<Project> projects) {
		String contextId = getContextId();
		selectionContext.setSelection(contextId+".projects", projects);
		selectionContext.setSelection(contextId+".context", context);
	}

	protected void saveModel(String filePath) {
		workspaceManager.setCurrentModel(filePath);
		workspaceManager.refresh();
	}

	protected String getContextId() {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		String contextId = (String) servletContext.getAttribute("contextId");
		return contextId;
	}
	
}

