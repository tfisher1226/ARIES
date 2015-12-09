package nam.ui.design;

import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import nam.ui.SecurityManager;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizard;
import org.aries.ui.AbstractWizardPage;
import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


@SuppressWarnings("serial")
public abstract class AbstractDomainElementWizard<T> extends AbstractWizard<T> {

	private static final String SECTION_PARAMETER = "section";
	
	protected abstract String getUrlContext();
	
	
	private String section;
	
//	@In(required = true)
//	private Events events;

//	@In(required = false)
//	protected Project project;

//	@In(required = false)
//	protected Workspace workspace;

//	@In(required = true)
//	protected WorkspaceManager workspaceManager;
	
	
	public int getIndex() {
		int index = getPageIndex(getSection());
		return index;
	}
	
	//TODO optimize here - this is called many times
	public String getSection() {
		String newSection = getViewParameter(SECTION_PARAMETER);
		if (!StringUtils.isEmpty(newSection) && !newSection.equals("undefined"))
			section = NameUtil.capName(newSection);
		if (StringUtils.isEmpty(section) || section.equalsIgnoreCase("null"))
			section = getPage().getName().replace(" ", "");
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
	
	public void addPages(List<AbstractWizardPage<T>> sections) {
		clearPages();
		Iterator<AbstractWizardPage<T>> iterator = sections.iterator();
		while (iterator.hasNext()) {
			AbstractWizardPage<T> section = iterator.next();
			if (section != null) {
				addPage(section);
			}
		}
		reset();
	}

	public void assignPages(List<AbstractWizardPage<T>> sections) {
		clearPages();
		Iterator<AbstractWizardPage<T>> iterator = sections.iterator();
		boolean startHasBeenFound = false;
		while (iterator.hasNext()) {
			AbstractWizardPage<T> section = iterator.next();
			if (section != null) {
				String name = section.getName();
				//TODO CHANGE THIS !!!!
				if (name.equals("Identity") || name.equals("Identification") || name.equals("Overview"))
					startHasBeenFound = true;
				if (startHasBeenFound)
					addPage(section);
			}
		}
		reset();
	}

	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}

	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}

	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public boolean isPopulateEnabled() {
		return super.isPopulateEnabled();
	}

	@Override
	public boolean isSaveEnabled() {
		return super.isSaveEnabled();
	}
	
	@Override
	public String refresh() {
		return super.refresh();
	}
	
	public String showPage() {
		return getUrl();
	}

	public String getUrl() {
		//if (!page.isValid())
		//	return null;
		String section = getSection();
		if (!StringUtils.isEmpty(section)) {
			setPage(section);
			//if (page != null)
			//	String url = page.getUrl();
			//TODO shall we use this url for section below?
			return getUrlContext() + "?section=" + section;
		}
		return getUrlContext();
	}

	@Override
	public String first() {
		page = getPage(0);
		return getUrlContext() + "?section=" + page.getUrl();
	}
	
	@Override
	public String back() {
		String url = page.back();
		if (url != null)
			return url;
		return getUrlContext() + "?section=" + super.back();
	}

//	public String doBack() {
//		//Display display = BeanContext.getFromSession("display");
//		//display.setModule("applicationMessages");
//		//display.error("Invalid");
//		//if (isValid())
//			//section = sections.get(getIndex() - 1);
//		//return "/nam/design/application/newApplication.xhtml?section="+section;
//		return null;
//	}
	
	@Override
	public String next() {
		if (page.isValid()) {
			String url = page.next();
			if (url != null)
				return url;
			return getUrlContext() + "?section=" + super.next();
		}
		return null;

		//Messages messages = BeanContext.getFromSession("messages");
		//messages.error("applicationMessages", "ERROR");
//		if (isValid())
//			section = sections.get(getIndex() + 1);
//		return super.next();
//		return null;
	}
	
	//TODO Make this private - should only happen once per request - no EL should call this
	public boolean isValid() {
		//Messages messages = BeanContext.getFromSession("messages");
		//boolean valid = !messages.isMessagesExist("applicationMessages");
		return page.isValid();
		//return valid;
	}
	
	@Override
	public String finish() {
		//Display display = BeanContext.getFromSession("display");
		//display.setModule("applicationMessages");
		//display.error("Invalid");
		//TODO saveApplication();
		if (page.isValid())
			return getUrlContext() + "?section=" + super.finish();
		return null;
	}
	
	public void saveProject() {
		getWorkspaceManager().saveProject();
		//TODO events.raiseEvent(getRefreshEvent());
	}

	
	public PageManager getPageManager() {
		return getFromSession("pageManager");
	}
	
	public SecurityManager getSecurityManager() {
		return getFromSession("securityManager");
	}
	
	public WorkspaceManager getWorkspaceManager() {
		return getFromSession("workspaceManager");
	}
	
	protected SelectionContext getSelectionContext() {
		return getFromSession("selectionContext");
	}

	@SuppressWarnings("unchecked")
	public <Bean> Bean getFromSession(String beanName) {
		Bean bean = (Bean) BeanContext.getFromSession(beanName);
		return bean;
	}

	protected GenerationContext getContext() {
		GenerationContext context = BeanContext.getFromSession("context");
		return context;
	}
	
	protected String getContextKey() {
		String contextId = getWorkspaceManager().getContextId();
		String contextKey = contextId + ".projects";
		return contextKey;
	}
	
}
