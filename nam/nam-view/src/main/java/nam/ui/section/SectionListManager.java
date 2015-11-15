package nam.ui.section;

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

import nam.ui.Section;
import nam.ui.design.SelectionContext;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionListManager")
public class SectionListManager extends AbstractDomainListManager<Section, SectionListObject> implements Serializable {
	
	@Inject
	private SectionDataManager sectionDataManager;
	
	@Inject
	private SectionEventManager sectionEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "sectionList";
	}
	
	@Override
	public String getTitle() {
		return "Section List";
	}
	
	@Override
	public Object getRecordKey(Section section) {
		return SectionUtil.getKey(section);
	}
	
	@Override
	public String getRecordName(Section section) {
		return SectionUtil.toString(section);
	}
	
	@Override
	protected Class<Section> getRecordClass() {
		return Section.class;
	}
	
	@Override
	protected Section getRecord(SectionListObject rowObject) {
		return rowObject.getSection();
	}
	
	@Override
	public Section getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? SectionUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Section section) {
		super.setSelectedRecord(section);
		fireSelectedEvent(section);
	}
	
	protected void fireSelectedEvent(Section section) {
		sectionEventManager.fireSelectedEvent(section);
	}
	
	public boolean isSelected(Section section) {
		Section selection = selectionContext.getSelection("section");
		boolean selected = selection != null && selection.equals(section);
		return selected;
	}
	
	@Override
	protected SectionListObject createRowObject(Section section) {
		SectionListObject listObject = new SectionListObject(section);
		listObject.setSelected(isSelected(section));
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
	protected Collection<Section> createRecordList() {
		try {
			Collection<Section> sectionList = sectionDataManager.getSectionList();
			if (sectionList != null)
				return sectionList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewSection() {
		return viewSection(selectedRecordKey);
	}
	
	public String viewSection(Object recordKey) {
		Section section = recordByKeyMap.get(recordKey);
		return viewSection(section);
	}
	
	public String viewSection(Section section) {
		SectionInfoManager sectionInfoManager = BeanContext.getFromSession("sectionInfoManager");
		String url = sectionInfoManager.viewSection(section);
		return url;
	}
	
	public String editSection() {
		return editSection(selectedRecordKey);
	}
	
	public String editSection(Object recordKey) {
		Section section = recordByKeyMap.get(recordKey);
		return editSection(section);
	}
	
	public String editSection(Section section) {
		SectionInfoManager sectionInfoManager = BeanContext.getFromSession("sectionInfoManager");
		String url = sectionInfoManager.editSection(section);
		return url;
	}
	
	public void removeSection() {
		removeSection(selectedRecordKey);
	}
	
	public void removeSection(Object recordKey) {
		Section section = recordByKeyMap.get(recordKey);
		removeSection(section);
	}
	
	public void removeSection(Section section) {
		try {
			if (sectionDataManager.removeSection(section))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelSection(@Observes @Cancelled Section section) {
		try {
			//Object key = SectionUtil.getKey(section);
			//recordByKeyMap.put(key, section);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("section");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateSection(Collection<Section> sectionList) {
		return SectionUtil.validate(sectionList);
	}
	
	public void exportSectionList(@Observes @Export String tableId) {
		//String tableId = "pageForm:sectionListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
