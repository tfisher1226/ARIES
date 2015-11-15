package nam.model.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Model;
import nam.model.Project;
import nam.model.util.ModelUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("modelInfoManager")
public class ModelInfoManager extends AbstractNamRecordManager<Model> implements Serializable {
	
	@Inject
	private ModelWizard modelWizard;
	
	@Inject
	private ModelDataManager modelDataManager;
	
	@Inject
	private ModelPageManager modelPageManager;
	
	@Inject
	private ModelEventManager modelEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ModelHelper modelHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ModelInfoManager() {
		setInstanceName("model");
	}
	
	
	public Model getModel() {
		return getRecord();
	}
	
	public Model getSelectedModel() {
		return selectionContext.getSelection("model");
	}
	
	@Override
	public Class<Model> getRecordClass() {
		return Model.class;
	}
	
	@Override
	public boolean isEmpty(Model model) {
		return modelHelper.isEmpty(model);
	}
	
	@Override
	public String toString(Model model) {
		return modelHelper.toString(model);
	}
	
	@Override
	public void initialize() {
		Model model = selectionContext.getSelection("model");
		if (model != null)
			initialize(model);
	}
	
	protected void initialize(Model model) {
		ModelUtil.initialize(model);
		modelWizard.initialize(model);
		setContext("model", model);
	}
	
	public void handleModelSelected(@Observes @Selected Model model) {
		selectionContext.setSelection("model",  model);
		modelPageManager.updateState(model);
		modelPageManager.refreshMembers();
		setRecord(model);
	}
	
	@Override
	public String newRecord() {
		return newModel();
	}
	
	public String newModel() {
		try {
			Model model = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("model",  model);
			String url = modelPageManager.initializeModelCreationPage(model);
			modelPageManager.pushContext(modelWizard);
			initialize(model);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Model create() {
		Model model = ModelUtil.create();
		return model;
	}
	
	@Override
	public Model clone(Model model) {
		model = ModelUtil.clone(model);
		return model;
	}
	
	@Override
	public String viewRecord() {
		return viewModel();
	}
	
	public String viewModel() {
		Model model = selectionContext.getSelection("model");
		String url = viewModel(model);
		return url;
	}
	
	public String viewModel(Model model) {
		try {
			String url = modelPageManager.initializeModelSummaryView(model);
			modelPageManager.pushContext(modelWizard);
			initialize(model);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editModel();
	}
	
	public String editModel() {
		Model model = selectionContext.getSelection("model");
		String url = editModel(model);
		return url;
	}
	
	public String editModel(Model model) {
		try {
			//model = clone(model);
			selectionContext.resetOrigin();
			selectionContext.setSelection("model",  model);
			String url = modelPageManager.initializeModelUpdatePage(model);
			modelPageManager.pushContext(modelWizard);
			initialize(model);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveModel() {
		Model model = getModel();
		if (validateModel(model)) {
			if (isImmediate())
				persistModel(model);
			outject("model", model);
		}
	}
	
	public void persistModel(Model model) {
		saveModel(model);
	}
	
	public void saveModel(Model model) {
		try {
			saveModelToSystem(model);
			modelEventManager.fireAddedEvent(model);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveModelToSystem(Model model) {
		modelDataManager.saveModel(model);
	}
	
	public void handleSaveModel(@Observes @Add Model model) {
		saveModel(model);
	}
	
	public void addModel(Model model) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichModel(Model model) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Model model) {
		return validateModel(model);
	}
	
	public boolean validateModel(Model model) {
		Validator validator = getValidator();
		boolean isValid = ModelUtil.validate(model);
		Display display = getFromSession("display");
		display.setModule("modelInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveModel() {
		display = getFromSession("display");
		display.setModule("modelInfo");
		Model model = selectionContext.getSelection("model");
		if (model == null) {
			display.error("Model record must be selected.");
		}
	}
	
	public String handleRemoveModel(@Observes @Remove Model model) {
		display = getFromSession("display");
		display.setModule("modelInfo");
		try {
			display.info("Removing Model "+ModelUtil.getLabel(model)+" from the system.");
			removeModelFromSystem(model);
			selectionContext.clearSelection("model");
			modelEventManager.fireClearSelectionEvent();
			modelEventManager.fireRemovedEvent(model);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeModelFromSystem(Model model) {
		if (modelDataManager.removeModel(model))
			setRecord(null);
	}
	
	public void cancelModel() {
		BeanContext.removeFromSession("model");
		modelPageManager.removeContext(modelWizard);
	}
	
}
