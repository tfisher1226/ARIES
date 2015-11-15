package nam.model.operation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Operation;
import nam.model.Project;
import nam.model.util.OperationUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("operationWizard")
@SuppressWarnings("serial")
public class OperationWizard extends AbstractDomainElementWizard<Operation> implements Serializable {
	
	@Inject
	private OperationDataManager operationDataManager;
	
	@Inject
	private OperationPageManager operationPageManager;
	
	@Inject
	private OperationEventManager operationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Operation";
	}
	
	@Override
	public String getUrlContext() {
		return operationPageManager.getOperationWizardPage();
	}
	
	@Override
	public void initialize(Operation operation) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(operationPageManager.getSections());
		super.initialize(operation);
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
		operationPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		operationPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		operationPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		operationPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Operation operation = getInstance();
		operationDataManager.saveOperation(operation);
		operationEventManager.fireSavedEvent(operation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Operation operation = getInstance();
		//TODO take this out soon
		if (operation == null)
			operation = new Operation();
		operationEventManager.fireCancelledEvent(operation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Operation operation = selectionContext.getSelection("operation");
		String name = operation.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("operationWizard");
			display.error("Operation name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
