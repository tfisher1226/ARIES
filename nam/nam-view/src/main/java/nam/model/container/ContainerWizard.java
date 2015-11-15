package nam.model.container;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Container;
import nam.model.Project;
import nam.model.util.ContainerUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("containerWizard")
@SuppressWarnings("serial")
public class ContainerWizard extends AbstractDomainElementWizard<Container> implements Serializable {
	
	@Inject
	private ContainerDataManager containerDataManager;
	
	@Inject
	private ContainerPageManager containerPageManager;
	
	@Inject
	private ContainerEventManager containerEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Container";
	}
	
	@Override
	public String getUrlContext() {
		return containerPageManager.getContainerWizardPage();
	}
	
	@Override
	public void initialize(Container container) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(containerPageManager.getSections());
		super.initialize(container);
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
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		containerPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		containerPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		containerPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		containerPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Container container = getInstance();
		containerDataManager.saveContainer(container);
		containerEventManager.fireSavedEvent(container);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Container container = getInstance();
		//TODO take this out soon
		if (container == null)
			container = new Container();
		containerEventManager.fireCancelledEvent(container);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Container container = selectionContext.getSelection("container");
		String name = container.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("containerWizard");
			display.error("Container name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
