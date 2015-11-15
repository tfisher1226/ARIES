package nam.model.process;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("processWizard")
@SuppressWarnings("serial")
public class ProcessWizard extends AbstractDomainElementWizard<Process> implements Serializable {
	
	@Inject
	private ProcessDataManager processDataManager;
	
	@Inject
	private ProcessPageManager processPageManager;
	
	@Inject
	private ProcessEventManager processEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Process";
	}
	
	@Override
	public String getUrlContext() {
		return processPageManager.getProcessWizardPage();
	}
	
	@Override
	public void initialize(Process process) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(processPageManager.getSections());
		super.initialize(process);
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
		processPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		processPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		processPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		processPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Process process = getInstance();
		processDataManager.saveProcess(process);
		processEventManager.fireSavedEvent(process);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Process process = getInstance();
		//TODO take this out soon
		if (process == null)
			process = new Process();
		processEventManager.fireCancelledEvent(process);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Process process = selectionContext.getSelection("process");
		String name = process.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("processWizard");
			display.error("Process name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
