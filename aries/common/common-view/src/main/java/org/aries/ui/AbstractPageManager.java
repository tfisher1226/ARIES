package org.aries.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;


public abstract class AbstractPageManager<T> implements PageManager<T> {

	private String pageIcon;
	
	private String pageTitle;
	
	private String sectionIcon;
	
	private String sectionType;
	
	private String sectionTitle;
	
	private String sectionName;
	
	protected List<AbstractWizardPage<T>> sections;

	protected Map<String, List<Breadcrumb>> breadcrumbs;

	protected Stack<AbstractWizard<?>> activeContexts;
	
	protected String currentContext; 
	
	protected String newContext; 
	
	
	public AbstractPageManager() {
		initializeBreadcrumbs();
		activeContexts = new Stack<AbstractWizard<?>>();
		currentContext = "none";
	}
	
	public String getPageIcon() {
		return pageIcon;
	}

	public void setPageIcon(String pageIcon) {
		this.pageIcon = pageIcon;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getSectionIcon() {
		return sectionIcon;
	}

	public void setSectionIcon(String sectionIcon) {
		this.sectionIcon = sectionIcon;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = NameUtil.uncapName(sectionType);
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public List<AbstractWizardPage<T>> getSections() {
		return sections;
	}
	
	public void initializeSections() {
		sections = new ArrayList<AbstractWizardPage<T>>();
	}

	public void reset() {
		breadcrumbs.clear();
		activeContexts.clear();
	}

//	public void setNewSection(String sectionKey) {
//		setSectionTitle("Applications");
//		setSectionType("application");
//		setSectionName("Overview");
//		setSectionIcon("/icons/nam/Application16.gif");
//	}
	
	public void initializeBreadcrumbs() {
		breadcrumbs = new HashMap<String, List<Breadcrumb>>();
	}

	public void clearBreadcrumbs(String key) {
		breadcrumbs.put(key, null);
	}

	public void clearActiveContexts() {
		activeContexts.clear();
	}

	public List<Breadcrumb> getBreadcrumbs(String key) {
		List<Breadcrumb> list = breadcrumbs.get(key);
		return list;
	}
	
	public void addBreadcrumb(String key, Breadcrumb breadcrumb) {
		List<Breadcrumb> list = breadcrumbs.get(key);
		if (list == null) {
			list = new ArrayList<Breadcrumb>();
			breadcrumbs.put(key, list);
		}
		list.add(breadcrumb);
	}

	public void addBreadcrumb(String key, String name, String action) {
		Breadcrumb breadcrumb = new Breadcrumb(name, action);
		addBreadcrumb(key, breadcrumb);
	}

	
	public Iterator<AbstractWizard<?>> getAllContexts() {
		return activeContexts.iterator();
	}

	public AbstractWizard<?> topContext() {
		return activeContexts.firstElement();
	}

	public void pushContext(AbstractWizard<?> wizard) {
		if (!activeContexts.contains(wizard)) {
			
			//pop any intermediate context
			List<AbstractWizard<?>> contexts = new ArrayList<AbstractWizard<?>>(activeContexts);
			Iterator<AbstractWizard<?>> iterator = contexts.iterator();
			while (iterator.hasNext()) {
				AbstractWizard<?> wizard2 = iterator.next();
				if (wizard2.getName().equals(currentContext))
					break;
				activeContexts.remove(wizard2);
			}
			
			activeContexts.push(wizard);
			setCurrentContext(wizard.getName());
		}
	}

	public void removeContext(AbstractWizard<?> wizard) {
		if (activeContexts.contains(wizard))
			activeContexts.remove(wizard);
		List<AbstractWizard<?>> contexts = new ArrayList<AbstractWizard<?>>(activeContexts);
		Iterator<AbstractWizard<?>> iterator = contexts.iterator();
		while (iterator.hasNext()) {
			AbstractWizard<?> wizard2 = iterator.next();
			activeContexts.remove(wizard2);
			if (wizard.equals(wizard2)) {
				break;
			}
		}
	}
	
	public String getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(String currentContext) {
//		if (currentContext == null)
//			System.out.println();
		this.currentContext = currentContext;
	}

	public String getNewContext() {
		return newContext;
	}

	public void setNewContext(String newContext) {
		this.newContext = newContext;
		this.currentContext = newContext;
//		boolean done = false;
//		List<AbstractWizard<?>> contexts = new ArrayList<AbstractWizard<?>>(activeContexts);
//		Iterator<AbstractWizard<?>> iterator = contexts.iterator();
//		while (iterator.hasNext()) {
//			AbstractWizard<?> wizard = iterator.next();
//			activeContexts.remove(wizard);
//			if (wizard.getName().equals(newContext)) {
//				break;
//			}
//		}
	}

//	public WorkspaceManager getWorkspaceManager() {
//		return getFromSession("workspaceManager");
//	}

//	protected EventMulticaster getEventMulticaster() {
//		return getFromSession("eventMulticaster");
//	}

	@SuppressWarnings("unchecked")
	public <Bean> Bean getFromSession(String beanName) {
		Bean bean = (Bean) BeanContext.getFromSession(beanName);
		return bean;
	}
	
//	protected String getContextKey() {
//		String contextId = getWorkspaceManager().getContextId();
//		String contextKey = contextId + ".projects";
//		return contextKey;
//	}
	
	
	
	private static final String SECTION_PARAMETER = "section";
	
	private String section;
	
	
	public String getSection() {
		String newSection = getViewParameter(SECTION_PARAMETER);
		if (!StringUtils.isEmpty(newSection) && !newSection.equals("undefined"))
			section = NameUtil.capName(newSection);
//		if (StringUtils.isEmpty(section) || section.equalsIgnoreCase("null"))
//			section = getPage().getName();
//		if (section.equals("Null") || section.equals("applications"))
//			System.out.println(section);
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	protected String getViewParameter(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String param = (String) externalContext.getRequestParameterMap().get(name);
		if (param != null && param.trim().length() > 0)
			return param;
		return null;
	}
	
}
