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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


//@ManagedBean
//@SessionScoped
public class AreaNavigator implements Serializable {

	//private static final String MODULE_VIEW_PARAMETER = "area";

	private static final String AREA_VIEW_PARAMETER = "area";

	private static final String SECTION_VIEW_PARAMETER = "section";

	private static final String SEPARATOR = "/";

	private static final String CONTENTS_FOLDER = "contents/";

	private static final String CONTENT_POSTFIX = "-content";

	//@ManagedProperty(value = "#{navigationParser.groupList}")
	private List<GroupDescriptor> groups;

	private ModuleDescriptor currentArea;

	private SectionDescriptor currentSection;

	private String sectionId;

	private String areaId;


	public String getArea() {
		return areaId;
	}

	public void setArea(String areaId) {
		this.areaId = areaId;
	}

	public String getSection() {
		return sectionId;
	}

	public void setSection(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getActivePage() {
		if (currentSection != null)
			return currentSection.getId();
		if (currentArea != null)
			return currentArea.getId();
		return null;
	}

	public List<GroupDescriptor> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDescriptor> groups) {
		this.groups = groups;
	}

	public void getFilteredGroups() {

	}

	//@PostConstruct
	public void init() {
		currentArea = null;
		currentSection = null;
		groups = new ArrayList<GroupDescriptor>(Collections2.filter(groups, new Predicate<GroupDescriptor>() {
			public boolean apply(GroupDescriptor input) {
				return input.hasEnabledItems();
			}
		}));
	}

	public ModuleDescriptor getCurrentArea() {
		String areaId = getViewParameter(AREA_VIEW_PARAMETER);
		if (currentArea == null || !currentArea.getId().equals(areaId)) {
			if (areaId != null) {
				currentArea = findAreaById(areaId);
				currentSection = null;
			}
		}
		return currentArea;
	}

	public SectionDescriptor getCurrentSection() {
		String id = getViewParameter(SECTION_VIEW_PARAMETER);
		if (currentSection == null || !currentSection.getId().equals(id)) {
			if (id != null) {
				currentSection = getCurrentArea().getTabById(id);
			}
			if (currentSection == null) {
				currentSection = getCurrentArea().getSections().iterator().next();
			}
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

	public ModuleDescriptor findAreaById(String id) {
		Iterator<GroupDescriptor> iterator = groups.iterator();
		while (iterator.hasNext()) {
			GroupDescriptor group = iterator.next();
			Iterator<ModuleDescriptor> dit = group.getModules().iterator();
			while (dit.hasNext()) {
				ModuleDescriptor locDemo = (ModuleDescriptor) dit.next();
				if (locDemo.getId().equals(id)) {
					return locDemo;
				}
			}
		}
		return null;
	}

	public String getAreaURI() {
		FacesContext context = FacesContext.getCurrentInstance();

		NavigationHandler handler = context.getApplication().getNavigationHandler();

		if (handler instanceof ConfigurableNavigationHandler) {
			ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) handler;

			areaId = getCurrentArea().getId();
			//sectionId = getCurrentSection().getId();

			NavigationCase navCase = navigationHandler.getNavigationCase(context, null, "/bookshop2" + SEPARATOR + areaId + SEPARATOR + areaId);
			//NavigationCase navCase = new NavigationCase("/index.xhtml", null, null, null, "/bookshop2/area1/area1.xhtml", null, false, true);
			return navCase.getToViewId(context);
		}

		return null;
	}
	
	public String getSectionURI() {
		FacesContext context = FacesContext.getCurrentInstance();

		NavigationHandler handler = context.getApplication().getNavigationHandler();

		if (handler instanceof ConfigurableNavigationHandler) {
			ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) handler;

			areaId = getCurrentArea().getId();
			sectionId = getCurrentSection().getId();

			NavigationCase navCase = navigationHandler.getNavigationCase(context, null, "/bookshop2" + SEPARATOR + areaId + SEPARATOR + sectionId);
			if (navCase != null)
				return navCase.getToViewId(context);
			return getAreaURI();
		}

		return null;
	}

	/**
	 * @return actual sample inclusion src Consider that: 1) all the samples should be placed in "samples" subfolder of the
	 *         actual sample 2) all the samples pages should use the same name as main sample page with "-sample" prefix
	 */
	public String getAreaIncludeURI() {
		String areaURI = getAreaURI();
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
