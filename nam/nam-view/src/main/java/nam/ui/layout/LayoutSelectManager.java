package nam.ui.layout;

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

import nam.ui.Layout;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutSelectManager")
public class LayoutSelectManager extends AbstractSelectManager<Layout, LayoutListObject> implements Serializable {
	
	@Inject
	private LayoutDataManager layoutDataManager;
	
	
	@Override
	public String getClientId() {
		return "layoutSelect";
	}
	
	@Override
	public String getTitle() {
		return "Layout Selection";
	}
	
	@Override
	protected Class<Layout> getRecordClass() {
		return Layout.class;
	}
	
	@Override
	public boolean isEmpty(Layout layout) {
		return getLayoutHelper().isEmpty(layout);
	}
	
	@Override
	public String toString(Layout layout) {
		return getLayoutHelper().toString(layout);
	}
	
	protected LayoutHelper getLayoutHelper() {
		return BeanContext.getFromSession("layoutHelper");
	}
	
	protected LayoutListManager getLayoutListManager() {
		return BeanContext.getFromSession("layoutListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshLayoutList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Layout> recordList) {
		LayoutListManager layoutListManager = getLayoutListManager();
		DataModel<LayoutListObject> dataModel = layoutListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshLayoutList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Layout> refreshRecords() {
		try {
			Collection<Layout> records = layoutDataManager.getLayoutList();
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
	public void sortRecords(List<Layout> layoutList) {
		Collections.sort(layoutList, new Comparator<Layout>() {
			public int compare(Layout layout1, Layout layout2) {
				String text1 = LayoutUtil.toString(layout1);
				String text2 = LayoutUtil.toString(layout2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
