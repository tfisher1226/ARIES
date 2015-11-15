package nam.model.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Model;
import nam.model.util.ModelUtil;


@SessionScoped
@Named("modelSelectManager")
public class ModelSelectManager extends AbstractSelectManager<Model, ModelListObject> implements Serializable {
	
	@Inject
	private ModelDataManager modelDataManager;
	
	@Inject
	private ModelHelper modelHelper;
	
	
	@Override
	public String getClientId() {
		return "modelSelect";
	}
	
	@Override
	public String getTitle() {
		return "Model Selection";
	}
	
	@Override
	protected Class<Model> getRecordClass() {
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
	
	protected ModelHelper getModelHelper() {
		return BeanContext.getFromSession("modelHelper");
	}
	
	protected ModelListManager getModelListManager() {
		return BeanContext.getFromSession("modelListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshModelList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Model> recordList) {
		ModelListManager modelListManager = getModelListManager();
		DataModel<ModelListObject> dataModel = modelListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshModelList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Model> refreshRecords() {
		try {
			Collection<Model> records = modelDataManager.getModelList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Model> modelList) {
		Collections.sort(modelList, new Comparator<Model>() {
			public int compare(Model model1, Model model2) {
				String text1 = ModelUtil.toString(model1);
				String text2 = ModelUtil.toString(model2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
