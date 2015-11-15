package nam.model.namespace;

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

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;


@SessionScoped
@Named("namespaceSelectManager")
public class NamespaceSelectManager extends AbstractSelectManager<Namespace, NamespaceListObject> implements Serializable {
	
	@Inject
	private NamespaceDataManager namespaceDataManager;

	@Inject
	private NamespaceHelper namespaceHelper;
	
	
	@Override
	public String getClientId() {
		return "namespaceSelect";
	}
	
	@Override
	public String getTitle() {
		return "Namespace Selection";
	}
	
	@Override
	protected Class<Namespace> getRecordClass() {
		return Namespace.class;
	}
	
	@Override
	public boolean isEmpty(Namespace namespace) {
		return namespaceHelper.isEmpty(namespace);
	}
	
	@Override
	public String toString(Namespace namespace) {
		return namespaceHelper.toString(namespace);
	}
	
	protected NamespaceHelper getNamespaceHelper() {
		return BeanContext.getFromSession("namespaceHelper");
	}
	
	protected NamespaceListManager getNamespaceListManager() {
		return BeanContext.getFromSession("namespaceListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshNamespaceList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Namespace> recordList) {
		NamespaceListManager namespaceListManager = getNamespaceListManager();
		DataModel<NamespaceListObject> dataModel = namespaceListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshNamespaceList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Namespace> refreshRecords() {
		try {
			Collection<Namespace> records = namespaceDataManager.getNamespaceList();
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
	public void sortRecords(List<Namespace> namespaceList) {
		Collections.sort(namespaceList, new Comparator<Namespace>() {
			public int compare(Namespace namespace1, Namespace namespace2) {
				String text1 = NamespaceUtil.toString(namespace1);
				String text2 = NamespaceUtil.toString(namespace2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
