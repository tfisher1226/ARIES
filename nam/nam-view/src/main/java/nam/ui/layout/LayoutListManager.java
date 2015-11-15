package nam.ui.layout;

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

import nam.ui.Layout;
import nam.ui.design.SelectionContext;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutListManager")
public class LayoutListManager extends AbstractDomainListManager<Layout, LayoutListObject> implements Serializable {
	
	@Inject
	private LayoutDataManager layoutDataManager;
	
	@Inject
	private LayoutEventManager layoutEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "layoutList";
	}
	
	@Override
	public String getTitle() {
		return "Layout List";
	}
	
	@Override
	public Object getRecordKey(Layout layout) {
		return LayoutUtil.getKey(layout);
	}
	
	@Override
	public String getRecordName(Layout layout) {
		return LayoutUtil.toString(layout);
	}
	
	@Override
	protected Class<Layout> getRecordClass() {
		return Layout.class;
	}
	
	@Override
	protected Layout getRecord(LayoutListObject rowObject) {
		return rowObject.getLayout();
	}
	
	@Override
	public Layout getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? LayoutUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Layout layout) {
		super.setSelectedRecord(layout);
		fireSelectedEvent(layout);
	}
	
	protected void fireSelectedEvent(Layout layout) {
		layoutEventManager.fireSelectedEvent(layout);
	}
	
	public boolean isSelected(Layout layout) {
		Layout selection = selectionContext.getSelection("layout");
		boolean selected = selection != null && selection.equals(layout);
		return selected;
	}
	
	@Override
	protected LayoutListObject createRowObject(Layout layout) {
		LayoutListObject listObject = new LayoutListObject(layout);
		listObject.setSelected(isSelected(layout));
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Layout> createRecordList() {
		try {
			Collection<Layout> layoutList = layoutDataManager.getLayoutList();
			if (layoutList != null)
				return layoutList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewLayout() {
		return viewLayout(selectedRecordKey);
	}
	
	public String viewLayout(Object recordKey) {
		Layout layout = recordByKeyMap.get(recordKey);
		return viewLayout(layout);
	}
	
	public String viewLayout(Layout layout) {
		LayoutInfoManager layoutInfoManager = BeanContext.getFromSession("layoutInfoManager");
		String url = layoutInfoManager.viewLayout(layout);
		return url;
	}
	
	public String editLayout() {
		return editLayout(selectedRecordKey);
	}
	
	public String editLayout(Object recordKey) {
		Layout layout = recordByKeyMap.get(recordKey);
		return editLayout(layout);
	}
	
	public String editLayout(Layout layout) {
		LayoutInfoManager layoutInfoManager = BeanContext.getFromSession("layoutInfoManager");
		String url = layoutInfoManager.editLayout(layout);
		return url;
	}
	
	public void removeLayout() {
		removeLayout(selectedRecordKey);
	}
	
	public void removeLayout(Object recordKey) {
		Layout layout = recordByKeyMap.get(recordKey);
		removeLayout(layout);
	}
	
	public void removeLayout(Layout layout) {
		try {
			if (layoutDataManager.removeLayout(layout))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelLayout(@Observes @Cancelled Layout layout) {
		try {
			//Object key = LayoutUtil.getKey(layout);
			//recordByKeyMap.put(key, layout);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("layout");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateLayout(Collection<Layout> layoutList) {
		return LayoutUtil.validate(layoutList);
	}
	
	public void exportLayoutList(@Observes @Export String tableId) {
		//String tableId = "pageForm:layoutListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
