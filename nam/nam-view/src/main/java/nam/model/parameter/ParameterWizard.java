package nam.model.parameter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Parameter;
import nam.model.Project;
import nam.model.util.ParameterUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("parameterWizard")
@SuppressWarnings("serial")
public class ParameterWizard extends AbstractDomainElementWizard<Parameter> implements Serializable {
	
	@Inject
	private ParameterDataManager parameterDataManager;
	
	@Inject
	private ParameterPageManager parameterPageManager;
	
	@Inject
	private ParameterEventManager parameterEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Parameter";
	}
	
	@Override
	public String getUrlContext() {
		return parameterPageManager.getParameterWizardPage();
	}
	
	@Override
	public void initialize(Parameter parameter) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(parameterPageManager.getSections());
		super.initialize(parameter);
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
		parameterPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		parameterPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		parameterPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		parameterPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Parameter parameter = getInstance();
		parameterDataManager.saveParameter(parameter);
		parameterEventManager.fireSavedEvent(parameter);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Parameter parameter = getInstance();
		//TODO take this out soon
		if (parameter == null)
			parameter = new Parameter();
		parameterEventManager.fireCancelledEvent(parameter);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Parameter parameter = selectionContext.getSelection("parameter");
		String name = parameter.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("parameterWizard");
			display.error("Parameter name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
