package nam.model.variable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Variable;
import nam.model.util.VariableUtil;


@SessionScoped
@Named("variableSelectManager")
public class VariableSelectManager extends AbstractSelectManager<Variable, VariableListObject> implements Serializable {
	
	@Inject
	private VariableDataManager variableDataManager;
	
	
	@Override
	public String getClientId() {
		return "variableSelect";
	}
	
	@Override
	public String getTitle() {
		return "Variable Selection";
	}
	
	@Override
	protected Class<Variable> getRecordClass() {
		return Variable.class;
	}
	
	@Override
	public boolean isEmpty(Variable variable) {
		return getVariableHelper().isEmpty(variable);
	}
	
	@Override
	public String toString(Variable variable) {
		return getVariableHelper().toString(variable);
	}
	
	protected VariableHelper getVariableHelper() {
		return BeanContext.getFromSession("variableHelper");
	}
	
	protected VariableListManager getVariableListManager() {
		return BeanContext.getFromSession("variableListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshVariableList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Variable> recordList) {
		VariableListManager variableListManager = getVariableListManager();
		DataModel<VariableListObject> dataModel = variableListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshVariableList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Variable> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Variable> variableList = BeanContext.getFromConversation(instanceId);
		return variableList;
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
	public void sortRecords(List<Variable> variableList) {
		Collections.sort(variableList, new Comparator<Variable>() {
			public int compare(Variable variable1, Variable variable2) {
				String text1 = VariableUtil.toString(variable1);
				String text2 = VariableUtil.toString(variable2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
