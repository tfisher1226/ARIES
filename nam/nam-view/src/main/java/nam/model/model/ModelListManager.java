package nam.model.model;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Model;
import nam.model.util.ModelUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("modelListManager")
public class ModelListManager extends AbstractDomainListManager<Model, ModelListObject> implements Serializable {
	
	@Inject
	private ModelDataManager modelDataManager;
	
	@Inject
	private ModelEventManager modelEventManager;
	
	@Inject
	private ModelInfoManager modelInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "modelList";
	}
	
	@Override
	public String getTitle() {
		return "Model List";
	}
	
	@Override
	public Object getRecordKey(Model model) {
		return ModelUtil.getKey(model);
	}
	
	@Override
	public String getRecordName(Model model) {
		return ModelUtil.getLabel(model);
	}
	
	@Override
	protected Class<Model> getRecordClass() {
		return Model.class;
	}
	
	@Override
	protected Model getRecord(ModelListObject rowObject) {
		return rowObject.getModel();
	}
	
	@Override
	public Model getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ModelUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Model model) {
		super.setSelectedRecord(model);
		fireSelectedEvent(model);
	}
	
	protected void fireSelectedEvent(Model model) {
		modelEventManager.fireSelectedEvent(model);
	}
	
	public boolean isSelected(Model model) {
		Model selection = selectionContext.getSelection("model");
		boolean selected = selection != null && selection.equals(model);
		return selected;
	}
	
	@Override
	protected ModelListObject createRowObject(Model model) {
		ModelListObject listObject = new ModelListObject(model);
		listObject.setSelected(isSelected(model));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Model> createRecordList() {
		try {
			Collection<Model> modelList = modelDataManager.getModelList();
			if (modelList != null)
				return modelList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewModel() {
		return viewModel(selectedRecordKey);
	}
	
	public String viewModel(Object recordKey) {
		Model model = recordByKeyMap.get(recordKey);
		return viewModel(model);
	}
	
	public String viewModel(Model model) {
		String url = modelInfoManager.viewModel(model);
		return url;
	}
	
	public String editModel() {
		return editModel(selectedRecordKey);
	}
	
	public String editModel(Object recordKey) {
		Model model = recordByKeyMap.get(recordKey);
		return editModel(model);
	}
	
	public String editModel(Model model) {
		String url = modelInfoManager.editModel(model);
		return url;
	}
	
	public void removeModel() {
		removeModel(selectedRecordKey);
	}
	
	public void removeModel(Object recordKey) {
		Model model = recordByKeyMap.get(recordKey);
		removeModel(model);
	}
	
	public void removeModel(Model model) {
		try {
			if (modelDataManager.removeModel(model))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelModel(@Observes @Cancelled Model model) {
		try {
			//Object key = ModelUtil.getKey(model);
			//recordByKeyMap.put(key, model);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("model");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateModel(Collection<Model> modelList) {
		return ModelUtil.validate(modelList);
	}
	
	public void exportModelList(@Observes @Export String tableId) {
		//String tableId = "pageForm:modelListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
