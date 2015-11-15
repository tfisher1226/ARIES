package bookshop2.ui2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.aries.runtime.BeanContext;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


@ManagedBean(name = "navigationDriver")
@SessionScoped
//@Named()
public class NavigationDriver implements Serializable {

	private static final String GROUP_VIEW_PARAMETER = "group";

	private static final String MODULE_VIEW_PARAMETER = "module";

	private static final String SECTION_VIEW_PARAMETER = "section";

	private static final String SEPARATOR = "/";

	private static final String CONTENTS_FOLDER = "contents/";

	private static final String CONTENT_POSTFIX = "-content";

	@ManagedProperty(value = "#{navigationParser.groupList}")
	private List<GroupDescriptor> groups;

	private GroupDescriptor currentGroup;

	private ModuleDescriptor currentModule;

	private SectionDescriptor currentSection;

	private String groupId;

	private String moduleId;

	private String sectionId;


	public String getGroup() {
		return groupId;
	}

	public void setGroup(String groupId) {
		this.groupId = groupId;
	}

	public String getModule() {
		return moduleId;
	}

	public void setModule(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSection() {
		return sectionId;
	}

	public void setSection(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getActivePage() {
		if (currentGroup == null)
			currentGroup = getCurrentGroup();
		if (currentModule == null)
			currentModule = getCurrentModule();
		if (currentSection == null)
			currentSection = getCurrentSection();
		if (currentSection != null)
			return currentSection.getId();
		if (currentModule != null)
			return currentModule.getId();
		if (currentGroup != null)
			return currentGroup.getId();
		return null;
	}

	public String getActiveLevel() {
		if (currentSection != null)
			return "section";
		if (currentModule != null)
			return "module";
		return "group";
	}
	
	public List<GroupDescriptor> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDescriptor> groups) {
		this.groups = groups;
	}

	public void getFilteredGroups() {

	}

	@PostConstruct
	public void initialize() {
		clearState();
		if (groups != null)
			groups = new ArrayList<GroupDescriptor>(Collections2.filter(groups, new Predicate<GroupDescriptor>() {
				public boolean apply(GroupDescriptor input) {
					return input.hasEnabledItems();
				}
			}));
		BeanContext.set("navigationDriver", this);
	}

	public void clearState() {
		currentGroup = null;
		currentModule = null;
		currentSection = null;
	}

	public GroupDescriptor getCurrentGroup() {
		String groupId = getViewParameter(GROUP_VIEW_PARAMETER);
		if (currentGroup == null || !currentGroup.getId().equals(groupId)) {
			if (groupId != null) {
				currentGroup = findGroupById(groupId);
				//currentModule = null;
				//currentSection = null;
			}
		}
		return currentGroup;
	}

	public ModuleDescriptor getCurrentModule() {
		String moduleId = getViewParameter(MODULE_VIEW_PARAMETER);
		if (currentModule == null || !currentModule.getId().equals(moduleId)) {
			if (moduleId != null) {
				currentModule = findModuleById(moduleId);
				//currentSection = null;
			}
		}
		return currentModule;
	}

	public SectionDescriptor getCurrentSection() {
		if (currentSection == null && currentModule == null)
			return null;
		String id = getViewParameter(SECTION_VIEW_PARAMETER);
		if (currentSection == null || !currentSection.getId().equals(id)) {
			if (id != null) {
				currentSection = getCurrentModule().getTabById(id);
			}
			//if (currentSection == null) {
			//	currentSection = getCurrentModule().getSections().iterator().next();
			//}
		}
		return currentSection;
	}

	private String getViewParameter(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String param = (String) externalContext.getRequestParameterMap().get(name);
		if (param != null && param.trim().length() > 0)
			return param;
		return null;
	}

	public GroupDescriptor findGroupById(String groupId) {
		Iterator<GroupDescriptor> iterator = groups.iterator();
		while (iterator.hasNext()) {
			GroupDescriptor group = iterator.next();
			if (group.getId().equals(groupId)) {
				return group;
			}
		}
		return null;
	}
	
	public ModuleDescriptor findModuleById(String moduleId) {
		Iterator<GroupDescriptor> iterator = groups.iterator();
		while (iterator.hasNext()) {
			GroupDescriptor group = iterator.next();
			Iterator<ModuleDescriptor> iterator2 = group.getModules().iterator();
			while (iterator2.hasNext()) {
				ModuleDescriptor module = (ModuleDescriptor) iterator2.next();
				if (module.getId().equals(moduleId)) {
					return module;
				}
			}
		}
		return null;
	}

	public String getModuleURI() {
		FacesContext context = FacesContext.getCurrentInstance();

		NavigationHandler handler = context.getApplication().getNavigationHandler();

		if (handler instanceof ConfigurableNavigationHandler) {
			ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) handler;

			groupId = getCurrentGroup().getId();
			moduleId = getCurrentModule().getId();
			//sectionId = getCurrentSection().getId();

			NavigationCase navCase = navigationHandler.getNavigationCase(context, null, SEPARATOR + groupId + SEPARATOR + moduleId + SEPARATOR + "index.xhtml");
			//NavigationCase navCase = new NavigationCase("/index.xhtml", null, null, null, "/bookshop2/group1/group1.xhtml", null, false, true);
			//if (navCase == null)
			//	System.out.println();
			return navCase.getToViewId(context);
		}

		return null;
	}
	
	public String getSectionURI() {
		FacesContext context = FacesContext.getCurrentInstance();

		NavigationHandler handler = context.getApplication().getNavigationHandler();

		if (handler instanceof ConfigurableNavigationHandler) {
			ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) handler;

			groupId = getCurrentGroup().getId();
			moduleId = getCurrentModule().getId();
			sectionId = getCurrentSection().getId();

			NavigationCase navCase = navigationHandler.getNavigationCase(context, null, SEPARATOR + groupId + SEPARATOR + moduleId + SEPARATOR + sectionId);
			if (navCase != null)
				return navCase.getToViewId(context);
			return getModuleURI();
		}

		return null;
	}

	/**
	 * @return actual sample inclusion src Consider that: 1) all the samples should be placed in "samples" subfolder of the
	 *         actual sample 2) all the samples pages should use the same name as main sample page with "-sample" prefix
	 */
	public String getModuleIncludeURI() {
		String moduleURI = getModuleURI();
		String sectionURI = getSectionURI();
		String currentSectionId = currentSection.getId();
		StringBuffer sectionURIBuffer = new StringBuffer(sectionURI);
		int folderOffset = sectionURIBuffer.lastIndexOf(currentSectionId);
        int fileNameOffset = sectionURIBuffer.lastIndexOf(currentSectionId) + currentSectionId.length() + currentSectionId.length() + 1;
		//int fileNameOffset = sectionURIBuffer.lastIndexOf(currentSectionId) + currentSectionId.length() + CONTENT_POSTFIX.length() + 1;
        String result = new StringBuffer(sectionURI).insert(folderOffset, currentSectionId+"/").insert(fileNameOffset, CONTENT_POSTFIX).toString();
		//String result = new StringBuffer(sectionURI).insert(folderOffset, currentSectionId).insert(fileNameOffset, CONTENT_POSTFIX).toString();
		return result;
	}
	
	/**
	 * @return actual sample inclusion src Consider that: 1) all the samples should be placed in "samples" subfolder of the
	 *         actual sample 2) all the samples pages should use the same name as main sample page with "-sample" prefix
	 */
	public String getSectionIncludeURI() {
		String sectionURI = getSectionURI();
		String currentSectionId = currentSection.getId();
		StringBuffer sectionURIBuffer = new StringBuffer(sectionURI);
		int folderOffset = sectionURIBuffer.lastIndexOf(currentSectionId);
        int fileNameOffset = sectionURIBuffer.lastIndexOf(currentSectionId) + currentSectionId.length() + currentSectionId.length() + 1;
		//int fileNameOffset = sectionURIBuffer.lastIndexOf(currentSectionId) + currentSectionId.length() + CONTENT_POSTFIX.length() + 1;
        String result = new StringBuffer(sectionURI).insert(folderOffset, currentSectionId+"/").insert(fileNameOffset, CONTENT_POSTFIX).toString();
		//String result = new StringBuffer(sectionURI).insert(folderOffset, currentSectionId).insert(fileNameOffset, CONTENT_POSTFIX).toString();
		return result;
	}

}
