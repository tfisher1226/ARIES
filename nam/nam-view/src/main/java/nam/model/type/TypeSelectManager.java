package nam.model.type;

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

import nam.model.Type;
import nam.model.util.TypeUtil;


@SessionScoped
@Named("typeSelectManager")
public class TypeSelectManager extends AbstractSelectManager<Type, TypeListObject> implements Serializable {
	
	@Inject
	private TypeDataManager typeDataManager;
	
	@Inject
	private TypeHelper typeHelper;
	
	
	@Override
	public String getClientId() {
		return "typeSelect";
	}
	
	@Override
	public String getTitle() {
		return "Type Selection";
	}
	
	@Override
	protected Class<Type> getRecordClass() {
		return Type.class;
	}
	
	@Override
	public boolean isEmpty(Type type) {
		return typeHelper.isEmpty(type);
	}
	
	@Override
	public String toString(Type type) {
		return typeHelper.toString(type);
	}
	
	protected TypeHelper getTypeHelper() {
		return BeanContext.getFromSession("typeHelper");
	}
	
	protected TypeListManager getTypeListManager() {
		return BeanContext.getFromSession("typeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Type> recordList) {
		TypeListManager typeListManager = getTypeListManager();
		DataModel<TypeListObject> dataModel = typeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Type> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Type> typeList = BeanContext.getFromConversation(instanceId);
		return typeList;
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
	public void sortRecords(List<Type> typeList) {
		Collections.sort(typeList, new Comparator<Type>() {
			public int compare(Type type1, Type type2) {
				String text1 = TypeUtil.toString(type1);
				String text2 = TypeUtil.toString(type2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
