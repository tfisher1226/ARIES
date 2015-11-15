package nam.model.variable;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Variable;
import nam.model.util.VariableUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("variableWizard")
@SuppressWarnings("serial")
public class VariableWizard extends AbstractDomainElementWizard<Variable> implements Serializable {
	
	@Inject
	private VariableDataManager variableDataManager;
	
	@Inject
	private VariablePageManager variablePageManager;
	
	@Inject
	private VariableEventManager variableEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Variable";
	}
	
	@Override
	public String getUrlContext() {
		return variablePageManager.getVariableWizardPage();
	}
	
	@Override
	public void initialize(Variable variable) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(variablePageManager.getSections());
		super.initialize(variable);
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
		variablePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		variablePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		variablePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		variablePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Variable variable = getInstance();
		variableDataManager.saveVariable(variable);
		variableEventManager.fireSavedEvent(variable);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Variable variable = getInstance();
		//TODO take this out soon
		if (variable == null)
			variable = new Variable();
		variableEventManager.fireCancelledEvent(variable);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Variable variable = selectionContext.getSelection("variable");
		String name = variable.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("variableWizard");
			display.error("Variable name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
