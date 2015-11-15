package nam.model.pod;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Pod;
import nam.model.Project;
import nam.model.util.PodUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("podWizard")
@SuppressWarnings("serial")
public class PodWizard extends AbstractDomainElementWizard<Pod> implements Serializable {
	
	@Inject
	private PodDataManager podDataManager;
	
	@Inject
	private PodPageManager podPageManager;
	
	@Inject
	private PodEventManager podEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Pod";
	}
	
	@Override
	public String getUrlContext() {
		return podPageManager.getPodWizardPage();
	}
	
	@Override
	public void initialize(Pod pod) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(podPageManager.getSections());
		super.initialize(pod);
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
		podPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		podPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		podPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		podPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Pod pod = getInstance();
		podDataManager.savePod(pod);
		podEventManager.fireSavedEvent(pod);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Pod pod = getInstance();
		//TODO take this out soon
		if (pod == null)
			pod = new Pod();
		podEventManager.fireCancelledEvent(pod);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Pod pod = selectionContext.getSelection("pod");
		String name = pod.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("podWizard");
			display.error("Pod name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
