package nam.model.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Component;
import nam.model.Operation;
import nam.model.Project;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;


@SessionScoped
@Named("componentWizard")
@SuppressWarnings("serial")
public class ComponentWizard extends AbstractDomainElementWizard<Component> implements Serializable {
	
	@Inject
	private ComponentDataManager componentDataManager;
	
	@Inject
	private ComponentPageManager componentPageManager;
	
	@Inject
	private ComponentEventManager componentEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Component";
	}
	
	@Override
	public String getUrlContext() {
		return componentPageManager.getComponentWizardPage();
	}
	
	@Override
	public void initialize(Component component) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(componentPageManager.getSections());
		super.initialize(component);
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
		componentPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		componentPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		componentPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		componentPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Component component = getInstance();
		componentDataManager.saveComponent(component);
		componentEventManager.fireSavedEvent(component);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Component component = getInstance();
		//TODO take this out soon
		if (component == null)
			component = new Component();
		componentEventManager.fireCancelledEvent(component);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Component component = selectionContext.getSelection("component");
		String name = component.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("componentWizard");
			display.error("Component name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		Application application = selectionContext.getSelection("application");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);

		int componentCount = 1;
		String baseType = "Object"+componentCount;
		String interfaceName = "Component"+componentCount;
		String className = "ComponentImpl"+componentCount;
		String namespace = application.getNamespace();
		String version = "0.0.1.SNAPSHOT";
		String description = "This is a skeleton Component with initial default settings";
		
		TransactionScope transactionScope = TransactionScope.PROCESS;
		TransactionUsage transactionUsage = TransactionUsage.REQUIRED;
		Transacted transacted = new Transacted();
		transacted.setLocal(false);
		transacted.setScope(transactionScope);
		transacted.setUse(transactionUsage);
		
		String element = "Object";
		Collection<String> fields = new ArrayList<String>();
		Collection<Operation> operations = new ArrayList<Operation>();
		Collection<String> annotations = new ArrayList<String>();
		
		component.setLabel(nameCapped);
		component.setBaseType(baseType);
		component.setCreationDate(new Date());
		component.setLastUpdate(new Date());
		component.setClassName(className);
		component.setPackageName(application.getGroupId());
		component.setInterfaceName(interfaceName);
		component.setNamespace(namespace);
		component.setTransacted(transacted);
		component.setVersion(version);
		component.setElement(element);
		component.setFields(fields);
		component.setOperations(operations);
		component.setAnnotations(annotations);
		component.setDescription(description);
		component.setRef(null);
		return getUrl();
	}
	
}
