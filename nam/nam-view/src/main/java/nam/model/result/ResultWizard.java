package nam.model.result;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Result;
import nam.model.util.ResultUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("resultWizard")
@SuppressWarnings("serial")
public class ResultWizard extends AbstractDomainElementWizard<Result> implements Serializable {
	
	@Inject
	private ResultDataManager resultDataManager;
	
	@Inject
	private ResultPageManager resultPageManager;
	
	@Inject
	private ResultEventManager resultEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Result";
	}
	
	@Override
	public String getUrlContext() {
		return resultPageManager.getResultWizardPage();
	}
	
	@Override
	public void initialize(Result result) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(resultPageManager.getSections());
		super.initialize(result);
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
		resultPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		resultPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		resultPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		resultPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Result result = getInstance();
		resultDataManager.saveResult(result);
		resultEventManager.fireSavedEvent(result);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Result result = getInstance();
		//TODO take this out soon
		if (result == null)
			result = new Result();
		resultEventManager.fireCancelledEvent(result);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Result result = selectionContext.getSelection("result");
		String name = result.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("resultWizard");
			display.error("Result name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
