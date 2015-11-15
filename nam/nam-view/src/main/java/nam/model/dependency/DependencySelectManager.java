package nam.model.dependency;

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

import nam.model.Dependency;
import nam.model.util.DependencyUtil;


@SessionScoped
@Named("dependencySelectManager")
public class DependencySelectManager extends AbstractSelectManager<Dependency, DependencyListObject> implements Serializable {
	
	@Inject
	private DependencyDataManager dependencyDataManager;
	
	@Inject
	private DependencyHelper dependencyHelper;
	
	
	@Override
	public String getClientId() {
		return "dependencySelect";
	}
	
	@Override
	public String getTitle() {
		return "Dependency Selection";
	}
	
	@Override
	protected Class<Dependency> getRecordClass() {
		return Dependency.class;
	}
	
	@Override
	public boolean isEmpty(Dependency dependency) {
		return dependencyHelper.isEmpty(dependency);
	}
	
	@Override
	public String toString(Dependency dependency) {
		return dependencyHelper.toString(dependency);
	}
	
	protected DependencyHelper getDependencyHelper() {
		return BeanContext.getFromSession("dependencyHelper");
	}
	
	protected DependencyListManager getDependencyListManager() {
		return BeanContext.getFromSession("dependencyListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshDependencyList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Dependency> recordList) {
		DependencyListManager dependencyListManager = getDependencyListManager();
		DataModel<DependencyListObject> dataModel = dependencyListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshDependencyList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Dependency> refreshRecords() {
		try {
			Collection<Dependency> records = dependencyDataManager.getDependencyList();
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
	public void sortRecords(List<Dependency> dependencyList) {
		Collections.sort(dependencyList, new Comparator<Dependency>() {
			public int compare(Dependency dependency1, Dependency dependency2) {
				String text1 = DependencyUtil.toString(dependency1);
				String text2 = DependencyUtil.toString(dependency2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
