package nam.model.parameter;

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

import nam.model.Parameter;
import nam.model.util.ParameterUtil;


@SessionScoped
@Named("parameterSelectManager")
public class ParameterSelectManager extends AbstractSelectManager<Parameter, ParameterListObject> implements Serializable {
	
	@Inject
	private ParameterDataManager parameterDataManager;
	
	@Inject
	private ParameterHelper parameterHelper;
	
	
	@Override
	public String getClientId() {
		return "parameterSelect";
	}
	
	@Override
	public String getTitle() {
		return "Parameter Selection";
	}
	
	@Override
	protected Class<Parameter> getRecordClass() {
		return Parameter.class;
	}
	
	@Override
	public boolean isEmpty(Parameter parameter) {
		return parameterHelper.isEmpty(parameter);
	}
	
	@Override
	public String toString(Parameter parameter) {
		return parameterHelper.toString(parameter);
	}
	
	protected ParameterHelper getParameterHelper() {
		return BeanContext.getFromSession("parameterHelper");
	}
	
	protected ParameterListManager getParameterListManager() {
		return BeanContext.getFromSession("parameterListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshParameterList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Parameter> recordList) {
		ParameterListManager parameterListManager = getParameterListManager();
		DataModel<ParameterListObject> dataModel = parameterListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshParameterList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Parameter> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Parameter> parameterList = BeanContext.getFromConversation(instanceId);
		return parameterList;
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
	public void sortRecords(List<Parameter> parameterList) {
		Collections.sort(parameterList, new Comparator<Parameter>() {
			public int compare(Parameter parameter1, Parameter parameter2) {
				String text1 = ParameterUtil.toString(parameter1);
				String text2 = ParameterUtil.toString(parameter2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
