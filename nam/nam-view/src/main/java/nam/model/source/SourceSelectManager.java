package nam.model.source;

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

import nam.model.Source;
import nam.model.util.SourceUtil;


@SessionScoped
@Named("sourceSelectManager")
public class SourceSelectManager extends AbstractSelectManager<Source, SourceListObject> implements Serializable {
	
	@Inject
	private SourceDataManager sourceDataManager;
	
	@Inject
	private SourceHelper sourceHelper;
	
	
	@Override
	public String getClientId() {
		return "sourceSelect";
	}
	
	@Override
	public String getTitle() {
		return "Source Selection";
	}
	
	@Override
	protected Class<Source> getRecordClass() {
		return Source.class;
	}
	
	@Override
	public boolean isEmpty(Source source) {
		return sourceHelper.isEmpty(source);
	}
	
	@Override
	public String toString(Source source) {
		return sourceHelper.toString(source);
	}
	
	protected SourceHelper getSourceHelper() {
		return BeanContext.getFromSession("sourceHelper");
	}
	
	protected SourceListManager getSourceListManager() {
		return BeanContext.getFromSession("sourceListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshSourceList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Source> recordList) {
		SourceListManager sourceListManager = getSourceListManager();
		DataModel<SourceListObject> dataModel = sourceListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshSourceList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Source> refreshRecords() {
		try {
			Collection<Source> records = sourceDataManager.getSourceList();
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
	public void sortRecords(List<Source> sourceList) {
		Collections.sort(sourceList, new Comparator<Source>() {
			public int compare(Source source1, Source source2) {
				String text1 = SourceUtil.toString(source1);
				String text2 = SourceUtil.toString(source2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
