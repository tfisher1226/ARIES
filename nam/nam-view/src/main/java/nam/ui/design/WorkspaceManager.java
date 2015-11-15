package nam.ui.design;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

import nam.model.Application;
import nam.model.Project;
import nam.model.Workspace;
import nam.model.application.ApplicationTreeManager;
import nam.model.component.ComponentTreeManager;
import nam.model.configuration.ConfigurationTreeManager;
import nam.model.information.InformationNamespaceTreeManager;
import nam.model.information.InformationOutlineTreeManager;
import nam.model.messaging.MessagingNamespaceTreeManager;
import nam.model.messaging.MessagingOutlineTreeManager;
import nam.model.namespace.ApplicationNamespaceTreeManager;
import nam.model.persistence.PersistenceNamespaceTreeManager;
import nam.model.persistence.PersistenceOutlineTreeManager;
import nam.model.util.ProjectUtil;
import nam.model.util.WorkspaceUtil;
import nam.ui.workspace.ModelFileTreeManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.RuntimeContext;
import org.aries.jaxb.JAXBWriter;
import org.aries.runtime.BeanContext;
import org.aries.ui.Helper;
import org.aries.ui.RecordManager;
import org.aries.ui.event.Refresh;
import org.aries.ui.util.SeamConversationHelper;
import org.aries.util.NameUtil;
import org.aries.util.ReflectionUtil;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import admin.Preferences;
import admin.User;
import admin.client.userService.UserService;
import aries.generation.engine.GenerationContext;


@Startup
@AutoCreate
@Scope(ScopeType.SESSION)
@Name("workspaceManager")
@SuppressWarnings("serial")
public class WorkspaceManager implements Serializable {

	private static Log log = LogFactory.getLog(WorkspaceManager.class);

	private User user;

	private Project project;

	private String currentModel;

	private Workspace workspace;

	private boolean helpSectionVisible;
	
	//@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	//@Inject
	private ModelFileTreeManager modelFileTreeManager;

	//@Inject
	private ApplicationTreeManager applicationTreeManager;

	//@Inject
	private ApplicationNamespaceTreeManager applicationNamespaceTreeManager;

	//@Inject
	private InformationNamespaceTreeManager informationNamespaceTreeManager;

	//@Inject
	private InformationOutlineTreeManager informationOutlineTreeManager;

	//@Inject
	private MessagingNamespaceTreeManager messagingNamespaceTreeManager;

	//@Inject
	private MessagingOutlineTreeManager messagingOutlineTreeManager;

	//@Inject
	private PersistenceNamespaceTreeManager persistenceNamespaceTreeManager;

	//@Inject
	private PersistenceOutlineTreeManager persistenceOutlineTreeManager;

	//@Inject
	private ComponentTreeManager componentTreeManager;

	//@Inject
	private ConfigurationTreeManager configurationTreeManager;

	//@Out(required = false, value="nam.configurations")
	//private List<Configuration> configurations;
	
	////@Inject
	protected SeamConversationHelper seamConversationHelper;

	private Map<String, Boolean> enabledSections;

	private List<Project> projects;
	
	
	public WorkspaceManager() {
		enabledSections = new HashMap<String, Boolean>();
		modelFileTreeManager = BeanContext.getFromSession("modelFileTreeManager");
		applicationTreeManager = BeanContext.getFromSession("applicationTreeManager");
		applicationNamespaceTreeManager = BeanContext.getFromSession("applicationNamespaceTreeManager");
		informationNamespaceTreeManager = BeanContext.getFromSession("informationNamespaceTreeManager");
		informationOutlineTreeManager = BeanContext.getFromSession("informationOutlineTreeManager");
		messagingNamespaceTreeManager = BeanContext.getFromSession("messagingNamespaceTreeManager");
		messagingOutlineTreeManager = BeanContext.getFromSession("messagingOutlineTreeManager");
		persistenceNamespaceTreeManager = BeanContext.getFromSession("persistenceNamespaceTreeManager");
		persistenceOutlineTreeManager = BeanContext.getFromSession("persistenceOutlineTreeManager");
		componentTreeManager = BeanContext.getFromSession("componentTreeManager");
	}
	
	public User getUser() {
		return user;
	}

	public List<Project> getProjects() {
		return projects;
	}
	
	public Collection<Application> getApplications() {
		Collection<Application> applications = ProjectUtil.getApplications(projects);
		return applications;
	}

	public GenerationContext getContext() {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		String contextId = (String) servletContext.getAttribute("contextId");
		GenerationContext context = getContext(contextId);
		return context;
	}

	public GenerationContext getContext(String contextId) {
		return BeanContext.getFromSession(contextId);
	}

	public String getContextId() {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		String contextId = (String) servletContext.getAttribute("contextId");
		return contextId;
	}

	public WorkspaceEventManager getWorkspaceEventManager() {
		return BeanContext.getFromSession("workspaceEventManager");
	}
	
	public void handleRefresh(@Observes @Refresh List<Project> projects) {
		getWorkspaceEventManager().fireRefreshedEvent(projects);
	}

	public String getCurrentModel() {
		return currentModel;
	}

	public void setCurrentModel(String currentModel) {
		this.currentModel = currentModel;
	}

	//@Create
	public String refresh() {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		currentModel = (String) servletContext.getAttribute("modelFileName");
		
		String contextId = getContextId();
		//GenerationContext context = BeanContext.getFromSession(contextId + ".context");
		projects = BeanContext.getFromSession(contextId + ".projects");
		getSelectionContext().setSelection("projectList", projects);

		//models = getModels();
		workspace = initializeWorkspace();
		//project = initializeProject();
		//modelTreeManager.setData(models);
		//modelTableManager.setData(models);
		
		getWorkspaceEventManager().fireRefreshEvent(new Object());
		
		//modelFileTreeManager.refresh(projects);
		//applicationTreeManager.refresh(projects);
		//applicationNamespaceTreeManager.refresh(projects);
		//informationNamespaceTreeManager.refresh(projects);
		//informationOutlineTreeManager.refresh(projects);
		//messagingNamespaceTreeManager.refresh(projects);
		//messagingOutlineTreeManager.refresh(projects);
		//persistenceNamespaceTreeManager.refresh(projects);
		//persistenceOutlineTreeManager.refresh(projects);
		//componentTreeManager.refresh(projects);
		
		//configurations = ProjectUtil.getConfigurations(projects);
		
		//refreshInformationViewState();
		//refreshApplicationViewState();
		//refreshMessagingViewState();
		//refreshPersistenceViewState();
		//getPageManager().reset();
		return null;
	}

	public Workspace initializeWorkspace() {
		//TODO Workspace workspace = modelService.getWorkspaceByUserId(user.getUserId()); 
		if (workspace == null) {
			workspace = new Workspace();
			workspace.setUser(user.getUserName());
			workspace.setName("Workspace for "+user.getUserName());
			//TODO modelService.saveWorkspace(workspace);
		}
		return workspace;
	}
	
	public Project initializeProject() {
		List<Project> projects = WorkspaceUtil.getProjects(workspace);
		if (projects.size() > 0) {
			Project project = projects.get(0);
			return project;
		}
		
		Application application = new Application();
		application.setName("placeholder");
		application.setGroupId("placeholder");
		application.setArtifactId("placeholder");

		Project project = new Project();
		project.setName("placeholder");
		ProjectUtil.getApplications(project).add(application);
		return project;
	}

	@Observer("nam.userAuthenticated")
	public void handleUserAuthenticated() {
		this.user = BeanContext.get("org.aries.user");
		Preferences preferences = user.getPreferences();
		Helper helper = BeanContext.getFromSession("helper");
		
		if (preferences == null) {
			preferences = new Preferences();
			user.setPreferences(preferences);
		}
		
		Map<String, String> workState = preferences.getWorkState();
		String helpSectionVisibleText = workState.get("helpSectionVisible");
		if (helpSectionVisibleText != null)
			helpSectionVisible = Boolean.parseBoolean(helpSectionVisibleText);
		else helpSectionVisible = true;
		if (helpSectionVisible)
			helper.setContentWidth("1000");
		else helper.setContentWidth("740");
	}

//	public void handleUserAuthenticated(@Observes UserAuthenticatedEvent event) {
//		this.user = event.getUser();
//	}
	
	@Observer("nam.userLoggedIn")
	public void handleUserLoggedIn() {
		refresh();
		PageManager pageManager = BeanContext.getFromSession("pageManager");
		pageManager.initializeMainPage();
	}

//	@Observer("nam.applicationsChanged, nam.configurationsChanged, nam.modulesChanged")
//	public void handleApplicationTreeChanged() {
//		applicationTreeManager.refresh(projects);
//		applicationNamespaceTreeManager.refresh(projects);
//		//TODO configurations = ProjectUtil.getConfigurations(project);
//	}
//	
//	@Observer({"nam.namespacesChanged", "nam.elementsChanged", "nam.fieldsChanged"})
//	public void handleInformationTreeChanged() {
//		informationNamespaceTreeManager.refresh(projects);
//		informationOutlineTreeManager.refresh(projects);
//	}
//
//	@Observer("nam.messagingTreeChanged")
//	public void handleMessagingTreeChanged() {
//		messagingNamespaceTreeManager.refresh(projects);
//		messagingOutlineTreeManager.refresh(projects);
//	}
//
//	@Observer("nam.informationTreeChanged")
//	public void handlePersistenceTreeChanged() {
//		persistenceNamespaceTreeManager.refresh(projects);
//		persistenceOutlineTreeManager.refresh(projects);
//	}

	@Observer("nam.componentLabelsChanged")
	public void handleComponentLabelsChanged() {
		refresh();
	}
	

	private boolean applicationTreeState = true;
	private boolean informationTreeState = true;
	private boolean messagingTreeState = true;
	private boolean persistenceTreeState = true;

	public void toggleApplicationViewState() { applicationTreeState = toggleViewState(applicationNamespaceTreeManager, applicationTreeManager, applicationTreeState); }
	public void toggleInformationViewState() { informationTreeState = toggleViewState(informationNamespaceTreeManager, informationOutlineTreeManager, informationTreeState); }
	public void toggleMessagingViewState() { messagingTreeState = toggleViewState(messagingNamespaceTreeManager, messagingOutlineTreeManager, messagingTreeState); }
	public void togglePersistenceViewState() { persistenceTreeState = toggleViewState(persistenceNamespaceTreeManager, persistenceOutlineTreeManager, persistenceTreeState); }

	public void refreshApplicationViewState() { refreshViewState(applicationNamespaceTreeManager, applicationTreeManager, applicationTreeState); }
	public void refreshInformationViewState() { refreshViewState(informationNamespaceTreeManager, informationOutlineTreeManager, informationTreeState); }
	public void refreshMessagingViewState() { refreshViewState(messagingNamespaceTreeManager, messagingOutlineTreeManager, messagingTreeState); }
	public void refreshPersistenceViewState() { refreshViewState(persistenceNamespaceTreeManager, persistenceOutlineTreeManager, persistenceTreeState); }

	protected boolean toggleViewState(AbstractDomainTreeManager namespaceManager, AbstractDomainTreeManager outlineManager, boolean viewState) {
		namespaceManager.setVisible(viewState);
		outlineManager.setVisible(!viewState);
		return !viewState;
	}

	protected void refreshViewState(AbstractDomainTreeManager namespaceManager, AbstractDomainTreeManager outlineManager, boolean viewState) {
		if (viewState)
			namespaceManager.refresh();
		if (!viewState)
			outlineManager.refresh();
	}

	
	public Boolean isEnabledSection(String name) {
		Boolean value = enabledSections.get(name);
		return value;
	}

	public void setEnabledSection(String name) {
		enabledSections.clear();
		enabledSections.put(name, Boolean.TRUE);
	}

	public boolean isHelpSectionVisible() {
		return helpSectionVisible;
	}

	public void showHelpSection() {
		helpSectionVisible = true;
		Helper helper = BeanContext.getFromSession("helper");
		helper.setContentWidth("1000");
		Preferences preferences = user.getPreferences();
		Map<String, String> workState = preferences.getWorkState();
		workState.put("helpSectionVisible", "true");
		UserService userService = BeanContext.getFromSession(UserService.ID);
		userService.saveUser(user);
	}

	public void hideHelpSection() {
		helpSectionVisible = false;
		Helper helper = BeanContext.getFromSession("helper");
		helper.setContentWidth("740");
		Preferences preferences = user.getPreferences();
		Map<String, String> workState = preferences.getWorkState();
		workState.put("helpSectionVisible", "false");
		UserService userService = BeanContext.getFromSession(UserService.ID);
		userService.saveUser(user);
	}

	public void saveProject() {
		try {
			//TODO modelService.saveModel(project);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}
	


	
	public String getSelectedType() {
		return getSelectionContext().getSelectedType();
	}

	public String getSelectedAction() {
		return getSelectionContext().getSelectedAction();
	}

	public SelectionContext getSelectionContext() {
		SelectionContext selectionContext = BeanContext.getFromSession("selectionContext");
		return selectionContext;
	}
	
	public RecordManager<?> getRecordManager(String elementType) {
		String elementTypeUncapped = NameUtil.uncapName(elementType);
		RecordManager<?> recordManager = BeanContext.getFromSession(elementTypeUncapped + "InfoManager");
		return recordManager;
	}

//	public PageManager<?> getPageManager(String elementType) {
//		String elementTypeUncapped = NameUtil.uncapName(elementType);
//		PageManager<?> pageManager = BeanContext.getFromSession(elementTypeUncapped + "PageManager");
//		return pageManager;
//	}

	public String viewObject() {
		String elementType = getSelectedType();
		if (elementType.contains("_")) {
			int indexOf = elementType.indexOf("_");
			if (indexOf != -1) {
				elementType = elementType.substring(indexOf + 1);
				elementType = elementType.toLowerCase();
			}
		}
		RecordManager<?> recordManager = getRecordManager(elementType);
		recordManager.viewRecord();
		return null;
	}

	public String newObject() {
		String elementType = getSelectedType();
		RecordManager<?> recordManager = getRecordManager(elementType);
		String url = recordManager.newRecord();
		return url;
	}

	public String editObject() {
		String elementType = getSelectedType();
		RecordManager<?> recordManager = getRecordManager(elementType);
		String url = recordManager.editRecord();
		return url;
	}
	
	public String selectObject() {
		String elementType = getSelectedType();
		RecordManager<?> recordManager = getRecordManager(elementType);
		String url = null; //recordManager.editRecord();
		return url;
	}
	
	//TODO externalize this
	//TODO add verification checks
	public String executeAction() {
		String selectedAction = getSelectedAction();
		getSelectionContext().clearSelectedAction();
		if (selectedAction != null && !selectedAction.equals("null")) {
			int indexOf = selectedAction.indexOf(".");
			String beanName = selectedAction.substring(0, indexOf);
			String methodName = selectedAction.substring(indexOf+1);
			Object beanInstance = BeanContext.getFromSession(beanName);
			if (beanInstance != null) {
				String url = (String) ReflectionUtil.invokeMethod(beanInstance, methodName, null);
				return url;
			}
		}
		return null;
	}
	
	
	protected void exportProject(Project model) {
		try {
			JAXBWriter helper = null; //TODO JAXBSessionCache.INSTANCE.getWriter();
			//TODO helper.setClasses(TypeMap.INSTANCE.getClasses());
			//TODO String xmlText = helper.marshal(model);
			String xmlText = null;

			File file = new File("c:/temp/file.xml");
			FileUtils.writeStringToFile(file, xmlText);
			
//		} catch (JAXBException e) {
//			String message = "Error: "+e.getMessage();
//			log.error(message);
//			throw new RuntimeException(message);
			
		} catch (Exception e) {
			String message = "Error: "+e.getMessage();
			log.error(message);
			throw new RuntimeException(message);
		}
	}

}
