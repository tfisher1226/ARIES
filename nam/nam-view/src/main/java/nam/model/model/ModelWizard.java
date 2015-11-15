package nam.model.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Model;
import nam.model.Project;
import nam.model.util.ModelUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("modelWizard")
@SuppressWarnings("serial")
public class ModelWizard extends AbstractDomainElementWizard<Model> implements Serializable {
	
	@Inject
	private ModelDataManager modelDataManager;
	
	@Inject
	private ModelPageManager modelPageManager;
	
	@Inject
	private ModelEventManager modelEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Model";
	}
	
	@Override
	public String getUrlContext() {
		return modelPageManager.getModelWizardPage();
	}
	
	@Override
	public void initialize(Model model) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(modelPageManager.getSections());
		super.initialize(model);
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
		modelPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		modelPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		modelPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		modelPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Model model = getInstance();
		modelDataManager.saveModel(model);
		modelEventManager.fireSavedEvent(model);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Model model = getInstance();
		//TODO take this out soon
		if (model == null)
			model = new Model();
		modelEventManager.fireCancelledEvent(model);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Model model = selectionContext.getSelection("model");
		String name = model.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("modelWizard");
			display.error("Model name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
