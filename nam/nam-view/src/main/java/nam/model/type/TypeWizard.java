package nam.model.type;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Type;
import nam.model.util.TypeUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("typeWizard")
@SuppressWarnings("serial")
public class TypeWizard extends AbstractDomainElementWizard<Type> implements Serializable {
	
	@Inject
	private TypeDataManager typeDataManager;
	
	@Inject
	private TypePageManager typePageManager;
	
	@Inject
	private TypeEventManager typeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Type";
	}
	
	@Override
	public String getUrlContext() {
		return typePageManager.getTypeWizardPage();
	}
	
	@Override
	public void initialize(Type type) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(typePageManager.getSections());
		super.initialize(type);
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
		typePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		typePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		typePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		typePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Type type = getInstance();
		typeDataManager.saveType(type);
		typeEventManager.fireSavedEvent(type);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Type type = getInstance();
		//TODO take this out soon
		if (type == null)
			type = new Type();
		typeEventManager.fireCancelledEvent(type);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Type type = selectionContext.getSelection("type");
		String name = type.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("typeWizard");
			display.error("Type name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
