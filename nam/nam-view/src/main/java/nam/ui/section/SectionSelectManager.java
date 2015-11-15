package nam.ui.section;

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

import nam.ui.Section;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionSelectManager")
public class SectionSelectManager extends AbstractSelectManager<Section, SectionListObject> implements Serializable {
	
	@Inject
	private SectionDataManager sectionDataManager;
	
	
	@Override
	public String getClientId() {
		return "sectionSelect";
	}
	
	@Override
	public String getTitle() {
		return "Section Selection";
	}
	
	@Override
	protected Class<Section> getRecordClass() {
		return Section.class;
	}
	
	@Override
	public boolean isEmpty(Section section) {
		return getSectionHelper().isEmpty(section);
	}
	
	@Override
	public String toString(Section section) {
		return getSectionHelper().toString(section);
	}
	
	protected SectionHelper getSectionHelper() {
		return BeanContext.getFromSession("sectionHelper");
	}
	
	protected SectionListManager getSectionListManager() {
		return BeanContext.getFromSession("sectionListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshSectionList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Section> recordList) {
		SectionListManager sectionListManager = getSectionListManager();
		DataModel<SectionListObject> dataModel = sectionListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshSectionList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Section> refreshRecords() {
		try {
			Collection<Section> records = sectionDataManager.getSectionList();
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
	public void sortRecords(List<Section> sectionList) {
		Collections.sort(sectionList, new Comparator<Section>() {
			public int compare(Section section1, Section section2) {
				String text1 = SectionUtil.toString(section1);
				String text2 = SectionUtil.toString(section2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
