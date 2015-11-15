package nam.model.source;

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

import nam.model.Source;
import nam.model.util.SourceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("sourceListManager")
public class SourceListManager extends AbstractDomainListManager<Source, SourceListObject> implements Serializable {
	
	@Inject
	private SourceDataManager sourceDataManager;
	
	@Inject
	private SourceEventManager sourceEventManager;
	
	@Inject
	private SourceInfoManager sourceInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "sourceList";
	}
	
	@Override
	public String getTitle() {
		return "Source List";
	}
	
	@Override
	public Object getRecordKey(Source source) {
		return SourceUtil.getKey(source);
	}
	
	@Override
	public String getRecordName(Source source) {
		return SourceUtil.getLabel(source);
	}
	
	@Override
	protected Class<Source> getRecordClass() {
		return Source.class;
	}
	
	@Override
	protected Source getRecord(SourceListObject rowObject) {
		return rowObject.getSource();
	}
	
	@Override
	public Source getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? SourceUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Source source) {
		super.setSelectedRecord(source);
		fireSelectedEvent(source);
	}
	
	protected void fireSelectedEvent(Source source) {
		sourceEventManager.fireSelectedEvent(source);
	}
	
	public boolean isSelected(Source source) {
		Source selection = selectionContext.getSelection("source");
		boolean selected = selection != null && selection.equals(source);
		return selected;
	}
	
	@Override
	protected SourceListObject createRowObject(Source source) {
		SourceListObject listObject = new SourceListObject(source);
		listObject.setSelected(isSelected(source));
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
	protected Collection<Source> createRecordList() {
		try {
			Collection<Source> sourceList = sourceDataManager.getSourceList();
			if (sourceList != null)
				return sourceList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewSource() {
		return viewSource(selectedRecordKey);
	}
	
	public String viewSource(Object recordKey) {
		Source source = recordByKeyMap.get(recordKey);
		return viewSource(source);
	}
	
	public String viewSource(Source source) {
		String url = sourceInfoManager.viewSource(source);
		return url;
	}
	
	public String editSource() {
		return editSource(selectedRecordKey);
	}
	
	public String editSource(Object recordKey) {
		Source source = recordByKeyMap.get(recordKey);
		return editSource(source);
	}
	
	public String editSource(Source source) {
		String url = sourceInfoManager.editSource(source);
		return url;
	}
	
	public void removeSource() {
		removeSource(selectedRecordKey);
	}
	
	public void removeSource(Object recordKey) {
		Source source = recordByKeyMap.get(recordKey);
		removeSource(source);
	}
	
	public void removeSource(Source source) {
		try {
			if (sourceDataManager.removeSource(source))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelSource(@Observes @Cancelled Source source) {
		try {
			//Object key = SourceUtil.getKey(source);
			//recordByKeyMap.put(key, source);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("source");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateSource(Collection<Source> sourceList) {
		return SourceUtil.validate(sourceList);
	}
	
	public void exportSourceList(@Observes @Export String tableId) {
		//String tableId = "pageForm:sourceListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
