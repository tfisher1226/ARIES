package nam.model.container;

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

import nam.model.Container;
import nam.model.util.ContainerUtil;


@SessionScoped
@Named("containerSelectManager")
public class ContainerSelectManager extends AbstractSelectManager<Container, ContainerListObject> implements Serializable {
	
	@Inject
	private ContainerDataManager containerDataManager;
	
	@Inject
	private ContainerHelper containerHelper;
	
	
	@Override
	public String getClientId() {
		return "containerSelect";
	}
	
	@Override
	public String getTitle() {
		return "Container Selection";
	}
	
	@Override
	protected Class<Container> getRecordClass() {
		return Container.class;
	}
	
	@Override
	public boolean isEmpty(Container container) {
		return containerHelper.isEmpty(container);
	}
	
	@Override
	public String toString(Container container) {
		return containerHelper.toString(container);
	}
	
	protected ContainerHelper getContainerHelper() {
		return BeanContext.getFromSession("containerHelper");
	}
	
	protected ContainerListManager getContainerListManager() {
		return BeanContext.getFromSession("containerListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshContainerList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Container> recordList) {
		ContainerListManager containerListManager = getContainerListManager();
		DataModel<ContainerListObject> dataModel = containerListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshContainerList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Container> refreshRecords() {
		try {
			Collection<Container> records = containerDataManager.getContainerList();
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
	public void sortRecords(List<Container> containerList) {
		Collections.sort(containerList, new Comparator<Container>() {
			public int compare(Container container1, Container container2) {
				String text1 = ContainerUtil.toString(container1);
				String text2 = ContainerUtil.toString(container2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
